package de.dis2011.menus;

import de.dis2011.FormUtil;
import de.dis2011.Menu;
import de.dis2011.data.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ContractMenu {

    private static SessionFactory sessionFactory;
    /**
     * Zeigt die Vertrags-Werwaltung
     */
    public static void show() {
        sessionFactory = new Configuration().configure().buildSessionFactory();

        //Menüoptionen
        final int NEW_PERSON = 0;
        final int NEW_CONTRACT = 1;
        final int SHOW_CONTRACTS = 2;
        final int SHOW_DETAILS = 3;
        final int BACK = 4;

        //Maklerverwaltungsmenü
        Menu maklerMenu = new Menu("Makler-Verwaltung");
        maklerMenu.addEntry("Erstelle Person", NEW_PERSON);
        maklerMenu.addEntry("Erstelle Vertrag", NEW_CONTRACT);
        maklerMenu.addEntry("Zeige Verträge", SHOW_CONTRACTS);
        maklerMenu.addEntry("Zeige Vertragsdetails", SHOW_DETAILS);
        maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);

        //Verarbeite Eingabe
        while (true) {
            int response = maklerMenu.show();

            switch (response) {
                case NEW_PERSON:
                    createPerson();
                    break;
                case NEW_CONTRACT:
                    createContract();
                    break;
                case SHOW_CONTRACTS:
                    showContracts();
                    break;
                case SHOW_DETAILS:
                    showContractDetails();
                case BACK:
                    return;
            }
        }
    }

    /**
     * Legt eine neue Person an, nachdem der Benutzer
     * die entprechenden Daten eingegeben hat.
     */
    private static void createPerson() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();

            Person person = new Person();
            person.setFirst_name(FormUtil.readString("Vorname"));
            person.setName(FormUtil.readString("Name"));
            person.setAdress(FormUtil.readString("Adresse"));

            session.save(person);
            tx.commit();

            System.out.println("Person mit der Id " + person.getId() + " wurde erzeugt.");
        }
        catch(HibernateException e)
        {
            if(tx!= null)
            {
                tx.rollback();
            }
            e.printStackTrace();
            System.out.println("Person konnte nicht erzeugt werden: HibernateException");
        }
        finally {
            session.close();
        }

    }

    /**
     * Legt einen neuen Vertrag an, nachdem der Benutzer
     * die entprechenden Daten eingegeben hat.
     */
    private static void createContract() {

        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try
        {
            tx = session.beginTransaction();
            int estateId = (FormUtil.readInt("Immobilien ID"));
            Estate estate = (Estate) session.get(Estate.class, estateId);

            if (estate == null) {
                System.out.println("Immobilie mit ID " + estateId + " existiert nicht.");
            }
            else
            {
                int personId = (FormUtil.readInt("Person ID"));
                Person person = (Person) session.get(Person.class, personId);
                if(person == null)
                {
                    System.out.println("Person mit ID " + personId + " existiert nicht.");
                }
                else
                {
                    Contract contract = new Contract();
                    //if is haus
                    if (estate instanceof House)
                    {
                        contract = new PurchaseContract();
                        PurchaseContract purchaseContract = (PurchaseContract) contract;
                        purchaseContract.setNoOfInstallments(FormUtil.readInt("Anzahl Raten"));
                        purchaseContract.setInterestRate(FormUtil.readInt("Interestrate"));
                    }
                    else if(estate instanceof Apartment)
                    {
                        contract = new TenancyContract();
                        TenancyContract tenancyContract = (TenancyContract) contract;
                        tenancyContract.setStartDate(FormUtil.readDate("Startdatum"));
                        tenancyContract.setDuration(FormUtil.readInt("Dauer in Monaten"));
                        tenancyContract.setAdditionalCosts(FormUtil.readInt("Nebenkosten"));
                    }
                    contract.setDate(FormUtil.readString("Datum"));
                    contract.setPlace(FormUtil.readString("Ort"));
                    contract.setPerson(person);
                    contract.setEstate(estate);

                    session.save(contract);
                    tx.commit();
                    System.out.println("Vertrag " + contract.getContract_no() + " wurde erstellt.");
                }
            }
        }
        catch (HibernateException e)
        {
            if(tx!= null)
            {
                tx.rollback();
            }
            e.printStackTrace();
            System.out.println("Contract konnte nicht erzeugt werden: HibernateException");

        }
        finally
        {
            session.close();
        }
    }

    private static void showContracts() {
        System.out.println("Alle Verträge:");
        Session session = sessionFactory.openSession();
        List<Contract> contractList  = (List<Contract>) session.createQuery("from Contract").list();
        for (Contract contract: contractList)
        {
            System.out.println(contract.toShortString());
        }
    }

    private static void showContractDetails() {


        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try
        {
            tx = session.beginTransaction();
            int id = (FormUtil.readInt("ID des Vertrags"));
            Contract contract = (Contract) session.get(PurchaseContract.class,id);
            if(contract == null)
            {
                contract = (Contract) session.get(TenancyContract.class,id);
            }
            if(contract == null)
            {
                System.out.println("Kein Vertrag mit der ID " + id + " auffindbar.");
            }
            else
            {
                System.out.println(contract.toString());
            }


        }
        catch (HibernateException e)
        {
            if(tx!= null)
            {
                tx.rollback();
            }
            e.printStackTrace();
            System.out.println("Contract konnte analysiert werden: HibernateException");

        }
        finally
        {
            session.close();
        }
    }
}
