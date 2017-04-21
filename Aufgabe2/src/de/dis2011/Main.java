package de.dis2011;

import de.dis2011.menus.ContractMenu;
import de.dis2011.menus.EstateAgentMenu;
import de.dis2011.menus.EstateMenu;


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
    private static void showMainMenu() {
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
                    EstateAgentMenu.show();
                    break;
                case MENU_ESTATE:
                    EstateMenu.show();
                    break;
                case MENU_CONTRACT:
                    ContractMenu.show();
                    break;
                case QUIT:
                    return;
            }
        }
    }


}
