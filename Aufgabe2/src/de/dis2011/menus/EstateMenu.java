package de.dis2011.menus;

import de.dis2011.FormUtil;
import de.dis2011.Menu;
import de.dis2011.data.Apartment;
import de.dis2011.data.Estate;
import de.dis2011.data.EstateAgent;
import de.dis2011.data.House;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Objects;

public class EstateMenu {

    private static EstateAgent currentAgent;
    private static SessionFactory sessionFactory;

    /**
     * Zeigt die Wohnobjekt-Werwaltung
     */
    public static void show() {
        sessionFactory = new Configuration().configure().buildSessionFactory();

        //Menüoptionen
        final int NEW_ESTATE = 0;
        final int EDIT_ESTATE = 1;
        final int DELETE_ESTATE = 2;
        final int BACK = 3;

        if (!login()) {
            return;
        }

        //Maklerverwaltungsmenü
        Menu maklerMenu = new Menu("Makler-Verwaltung");
        maklerMenu.addEntry("Neues Wohnobjekt", NEW_ESTATE);
        maklerMenu.addEntry("Bearbeite Wohnobjekt", EDIT_ESTATE);
        maklerMenu.addEntry("Entferne Wohnobjekt", DELETE_ESTATE);
        maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);

        //Verarbeite Eingabe
        while (true) {
            int response = maklerMenu.show();

            switch (response) {
                case NEW_ESTATE:
                    createEstate();
                    break;
                case EDIT_ESTATE:
                    editEstate();
                    break;
                case DELETE_ESTATE:
                    deleteEstate();
                    break;
                case BACK:
                    //logout the agent
                    currentAgent = null;
                    return;
            }
        }
    }

    private static boolean login() {
        Session session = sessionFactory.openSession();
        String login = FormUtil.readString("Login");
        EstateAgent agent = (EstateAgent) session.get(EstateAgent.class, login);
        if (agent == null) {
            System.out.println("Makler mit dem angegebenen Login nicht gefunden.");
            return false;
        }
        String password = FormUtil.readString("Passwort");
        if (!password.equals(agent.getPassword())) {
            System.out.println("Passwort falsch.");
            return false;
        }
        session.close();
        currentAgent = agent;
        return true;
    }

    /**
     *
     *
     */
    private static void createEstate() {
        String type = getEstateType();
        createSpecialEstate(type);
    }

    private static String getEstateType()
    {
        String type = "";
        while (!Objects.equals(type, "haus") && !Objects.equals(type, "wohnung")) {
            type = FormUtil.readString("Typ der Immobilie (Haus oder Wohnung)").toLowerCase();
        }
        return type;
    }

    private static void createSpecialEstate(String type) {

        Estate estate;
        if (type.equals("haus") || type.equals("house")) {
            House house = new House();
            house.setFloor(FormUtil.readInt("Anzahl Stockwerke"));
            house.setGarden(FormUtil.readBool("Garten"));
            house.setPrice(FormUtil.readInt("Preis"));
            estate = house;
        } else if (type.equals("wohnung") || type.equals("apartment")){
            Apartment apartment = new Apartment();
            apartment.setFloor(FormUtil.readInt("Etage"));
            apartment.setRent(FormUtil.readInt("Miete"));
            apartment.setRooms(FormUtil.readInt("Anzahl Räume"));
            apartment.setBuiltInKitchen(FormUtil.readInt("Küche"));
            apartment.setBalcony(FormUtil.readInt("Balkon"));
            estate = apartment;
        }
        else
        {
            System.out.println("Dieser Typ existiert nicht");
            return;
        }

        estate.setCity(FormUtil.readString("Stadt"));
        estate.setStreet(FormUtil.readString("Straße"));
        estate.setStreetNumber(FormUtil.readInt("Hausnummer"));
        estate.setPostCode(FormUtil.readInt("PLZ"));
        estate.setSquareArea(FormUtil.readInt("Fläche in qm"));
        estate.setAgent(currentAgent);

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(estate);
            tx.commit();
            System.out.println("Immobilie wurde erzeugt. Neue ID: " + estate.getId());
        }
        catch(HibernateException e)
        {
            if(tx!= null)
            {
                tx.rollback();
            }
            System.out.println("Immobilie konnte nicht erzeugt werden: HibernateException");
        }
        finally {
            session.close();
        }
    }

    private static void editEstate() {


        int id = FormUtil.readInt("ID der Immobilie");

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            Estate estate = (Estate) session.get(Estate.class, id);

            if (estate == null) {
                System.out.println("Immobilie " + id + "existiert nicht.");
            } else if (estate instanceof House){
                System.out.println("Haus gefunden. Neue Werte eintragen:");
                House house = (House) estate;
                house.setFloor(FormUtil.readInt("Anzahl Stockwerke"));
                house.setGarden(FormUtil.readBool("Garten"));
                house.setPrice(FormUtil.readInt("Preis"));
                estate.setCity(FormUtil.readString("Stadt"));
                estate.setStreet(FormUtil.readString("Straße"));
                estate.setStreetNumber(FormUtil.readInt("Hausnummer"));
                estate.setPostCode(FormUtil.readInt("PLZ"));
                estate.setSquareArea(FormUtil.readInt("Fläche in qm"));
                session.save(house);
                tx.commit();
                System.out.println("Immobilie " + " wurde bearbeitet");
            } else if (estate instanceof Apartment) {
                System.out.println("Wohnung gefunden. Neue Werte eintragen:");
                Apartment apartment = (Apartment) estate;
                apartment.setFloor(FormUtil.readInt("Etage"));
                apartment.setRent(FormUtil.readInt("Miete"));
                apartment.setRooms(FormUtil.readInt("Anzahl Räume"));
                estate.setCity(FormUtil.readString("Stadt"));
                estate.setStreet(FormUtil.readString("Straße"));
                estate.setStreetNumber(FormUtil.readInt("Hausnummer"));
                estate.setPostCode(FormUtil.readInt("PLZ"));
                estate.setSquareArea(FormUtil.readInt("Fläche in qm"));
                ((Apartment) estate).setBuiltInKitchen(FormUtil.readInt("Küche"));
                ((Apartment) estate).setBalcony(FormUtil.readInt("Balkon"));
                session.save(apartment);
                tx.commit();
                System.out.println("Immobilie " + " wurde bearbeitet");
            }

        }
        catch(HibernateException e)
        {
            if(tx!= null)
            {
                tx.rollback();
            }
            System.out.println("Immobilie konnte nicht bearbeitet werden: HibernateException");
        }
        finally
        {
            session.close();
        }
    }

    private static void deleteEstate() {

        int id = FormUtil.readInt("ID der Immobilie");

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            Estate estate = (Estate) session.get(Estate.class, id);

            if (estate == null) {
                System.out.println("Immobilie " + id + " existiert nicht.");
            }
            else{
                session.delete(estate);
                tx.commit();
                System.out.println("Immobilie " + id + " wurde gelöscht.");
            }
        }
        catch(HibernateException e)
        {
            if(tx!= null)
            {
                tx.rollback();
            }
            System.out.println("Immobilie konnte nicht gelöscht werden: HibernateException");
        }
        finally
        {
            session.close();
        }
    }
}
