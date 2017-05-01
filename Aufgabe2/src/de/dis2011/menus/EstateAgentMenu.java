package de.dis2011.menus;


import de.dis2011.FormUtil;
import de.dis2011.Menu;
import de.dis2011.data.EstateAgent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;


import java.util.Objects;

public class EstateAgentMenu {

    private static SessionFactory sessionFactory;

    /**
     * Zeigt die Makler-Verwaltung
     */
    public static void show() {
        sessionFactory = new Configuration().configure().buildSessionFactory();

        //Menüoptionen
        final int NEW_MAKLER = 0;
        final int EDIT_MAKLER = 1;
        final int DELETE_MAKLER = 2;
        final int BACK = 3;

        String passwort = FormUtil.readString("Masterpasswort");

        if (!Objects.equals(passwort, "123")) {
            System.out.println("Passwort falsch.");
            return;
        }

        //Maklerverwaltungsmenü
        Menu maklerMenu = new Menu("Makler-Verwaltung");
        maklerMenu.addEntry("Neuer Makler", NEW_MAKLER);
        maklerMenu.addEntry("Bearbeite Makler", EDIT_MAKLER);
        maklerMenu.addEntry("Entferne Makler", DELETE_MAKLER);
        maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);

        //Verarbeite Eingabe
        while (true) {
            int response = maklerMenu.show();

            switch (response) {
                case NEW_MAKLER:
                    newMakler();
                    break;
                case EDIT_MAKLER:
                    editMakler();
                    break;
                case DELETE_MAKLER:
                    deleteMakler();
                    break;
                case BACK:
                    return;
            }
        }
    }

    /**
     * Legt einen neuen Makler an, nachdem der Benutzer
     * die entprechenden Daten eingegeben hat.
     */
    private static void newMakler() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();

            EstateAgent agent = new EstateAgent();

            agent.setName(FormUtil.readString("Name"));
            agent.setAddress(FormUtil.readString("Adresse"));
            agent.setLogin(getLoginFromUser(session));
            agent.setPassword(FormUtil.readString("Passwort"));

            session.save(agent);
            tx.commit();

            System.out.println("Makler mit dem Login " + agent.getLogin() + " wurde erzeugt.");
        }
        catch(HibernateException e)
        {
            if(tx!= null)
            {
                tx.rollback();
            }
            System.out.println("Makler konnte nicht erzeugt werden: HibernateException");
        }
        finally {
            session.close();
        }

    }

    /**
     * Bearbeitet einen Makler anhand des Logins
     */
    private static void editMakler() {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            String login = FormUtil.readString("Login");
            EstateAgent agent = (EstateAgent) session.get(EstateAgent.class, login);

            if (agent != null) {
                agent.setName(FormUtil.readString("Name"));
                agent.setAddress(FormUtil.readString("Adresse"));
                agent.setPassword(FormUtil.readString("Passwort"));
                session.save(agent);
                tx.commit();
                System.out.println("Makler mit dem Login " + login + " wurde bearbeitet.");
            } else {
                //falls makler nicht in der Datenbank, wird ein neuer erstellt
                System.out.println("Makler mit dem Login " + login + " nicht gefunden.");
            }
        }
        catch(HibernateException e)
        {
            if(tx!= null)
            {
                tx.rollback();
            }
            System.out.println("Makler konnte nicht bearbeitet werden: HibernateException");
        }
        finally
        {
            session.close();
        }
    }

    /**
     * Löscht einen Makler anhand des Logins
     */
    private static void deleteMakler() {

        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try
        {
            tx = session.beginTransaction();

            String login = FormUtil.readString("Login");
            EstateAgent agent = (EstateAgent) session.get(EstateAgent.class, login);

            session.delete(agent);
            tx.commit();
            System.out.println("Makler mit der Login " + login + " wurde gelöscht.");
        }
        catch(HibernateException e)
        {
            if(tx!= null)
            {
                tx.rollback();
            }
            System.out.println("Makler konnte nicht gelöscht werden: HibernateException");
        }
        finally
        {
            session.close();
        }


    }

    private static String getLoginFromUser(Session session)
    {
        while(true) {
            String login = FormUtil.readString("Login");
            EstateAgent existingagent = (EstateAgent) session.get(EstateAgent.class, login);
            if (existingagent == null) {
                return login;
            }
            else {
                System.out.println("Login bereits vorhanden. Bitte wähle einen anderen aus.");
            }
        }
    }
}
