package de.dis2011.menus;

import de.dis2011.FormUtil;
import de.dis2011.Menu;
import de.dis2011.data.EstateAgent;

import java.util.Objects;

public class EstateAgentMenu {

    /**
     * Zeigt die Makler-Verwaltung
     */
    public static void show() {
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
        EstateAgent m = new EstateAgent(false);

        m.setName(FormUtil.readString("Name"));
        m.setAddress(FormUtil.readString("Adresse"));
        m.setLogin(FormUtil.readString("Login"));
        m.setPassword(FormUtil.readString("Passwort"));
        m.save();

        System.out.println("Makler mit dem Login " + m.getLogin() + " wurde erzeugt.");
    }

    /**
     * Bearbeitet einen Makler anhand des Logins
     */
    private static void editMakler() {
        EstateAgent m = new EstateAgent(true);

        m.setLogin(FormUtil.readString("Login"));
        m.setName(FormUtil.readString("Name"));
        m.setAddress(FormUtil.readString("Adresse"));
        m.setPassword(FormUtil.readString("Passwort"));

        if (m.edit()) {
            System.out.println("Makler mit dem Login " + m.getLogin() + " wurde bearbeitet.");
        } else {
            //falls makler nicht in der Datenbank, wird ein neuer erstellt
            m.setInDb(false);
            m.save();
            System.out.println("Makler mit dem Login " + m.getLogin() + " nicht gefunden, stattdessen neuer erstellt.");
        }
    }

    /**
     * Löscht einen Makler anhand des Logins
     */
    private static void deleteMakler() {
        EstateAgent m = new EstateAgent(false);

        m.setLogin(FormUtil.readString("Login"));
        m.delete();

        System.out.println("Makler mit der Login " + m.getLogin() + " wurde gelöscht.");
    }
}
