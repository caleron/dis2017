package de.dis2011.menus;

import de.dis2011.Menu;

public class EstateMenu {

    /**
     * Zeigt die Wohnobjekt-Werwaltung
     */
    public static void show() {
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
}
