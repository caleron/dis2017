package de.dis2011.menus;

import de.dis2011.Menu;

public class ContractMenu {


    /**
     * Zeigt die Vertrags-Werwaltung
     */
    public static void show() {
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
