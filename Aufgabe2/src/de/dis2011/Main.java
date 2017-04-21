package de.dis2011;

import de.dis2011.data.EstateAgent;

/**
 * Hauptklasse
 */
public class Main {
    /**
     * Startet die Anwendung
     */
    public static void main(String[] args) {
        showMainMenu();
    }

    /**
     * Zeigt das Hauptmenü
     */
    public static void showMainMenu() {
        //Menüoptionen
        final int MENU_MAKLER = 0;
        final int MENU_ESTATE = 1;
        final int MENU_CONTRACT = 2;
        final int QUIT = 3;

        //Erzeuge Menü
        Menu mainMenu = new Menu("Hauptmenü");
        mainMenu.addEntry("Makler-Verwaltung", MENU_MAKLER);
        mainMenu.addEntry("Wohnobjekt-Verwaltung", MENU_ESTATE);
        mainMenu.addEntry("Vertrags-Verwaltung", MENU_CONTRACT);
        mainMenu.addEntry("Beenden", QUIT);

        //Verarbeite Eingabe
        while (true) {
            int response = mainMenu.show();

            switch (response) {
                case MENU_MAKLER:
                    showMaklerMenu();
                    break;
                case MENU_ESTATE:
                    showEstateMenu();
                    break;
                case MENU_CONTRACT:
                    showContractMenu();
                    break;
                case QUIT:
                    return;
            }
        }
    }

    /**
     * Zeigt die Makler-Verwaltung
     */
    public static void showMaklerMenu() {
        //Menüoptionen
        final int NEW_MAKLER = 0;
        final int EDIT_MAKLER = 1;
        final int DELETE_MAKLER = 2;
        final int BACK = 3;

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
    public static void newMakler() {
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
    public static void editMakler() {
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
    public static void deleteMakler() {
        EstateAgent m = new EstateAgent(false);

        m.setLogin(FormUtil.readString("Login"));
        m.delete();

        System.out.println("Makler mit der Login " + m.getLogin() + " wurde gelöscht.");
    }

    /**
     * Zeigt die Wohnobjekt-Werwaltung
     */
    public static void showEstateMenu() {
        //Menüoptionen
        final int LOGIN = 0;
        final int NEW_ESTATE = 1;
        final int EDIT_ESTATE = 2;
        final int DELETE_ESTATE = 3;
        final int BACK = 4;

        //Maklerverwaltungsmenü
        Menu maklerMenu = new Menu("Makler-Verwaltung");
        maklerMenu.addEntry("Login", LOGIN);
        maklerMenu.addEntry("Neues Wohnobjekt", NEW_ESTATE);
        maklerMenu.addEntry("Bearbeite Wohnobjekt", EDIT_ESTATE);
        maklerMenu.addEntry("Entferne Wohnobjekt", DELETE_ESTATE);
        maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);

        //Verarbeite Eingabe
        while (true) {
            int response = maklerMenu.show();

            switch (response) {
                case LOGIN:
                    // noch nix
                    break;
                case NEW_ESTATE:
                    // noch nix
                    break;
                case EDIT_ESTATE:
                    // noch nix
                    break;
                case DELETE_ESTATE:
                    // noch nix
                    break;
                case BACK:
                    return;
            }
        }
    }

    /**
     * Zeigt die Vertrags-Werwaltung
     */
    public static void showContractMenu() {
        //Menüoptionen
        final int NEW_PERSON = 0;
        final int NEW_CONTRACT = 1;
        final int SHOW_CONTRACTS = 2;
        final int BACK = 4;

        //Maklerverwaltungsmenü
        Menu maklerMenu = new Menu("Makler-Verwaltung");
        maklerMenu.addEntry("Erstelle Person", NEW_PERSON);
        maklerMenu.addEntry("Erstelle Vertrag", NEW_CONTRACT);
        maklerMenu.addEntry("Zeige Verträge", SHOW_CONTRACTS);
        maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);

        //Verarbeite Eingabe
        while (true) {
            int response = maklerMenu.show();

            switch (response) {
                case NEW_PERSON:
                    // noch nix
                    break;
                case NEW_CONTRACT:
                    // noch nix
                    break;
                case SHOW_CONTRACTS:
                    // noch nix
                    break;
                case BACK:
                    return;
            }
        }
    }
}
