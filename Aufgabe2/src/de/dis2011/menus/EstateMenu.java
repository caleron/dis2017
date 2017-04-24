package de.dis2011.menus;

import de.dis2011.FormUtil;
import de.dis2011.Menu;
import de.dis2011.data.Apartment;
import de.dis2011.data.Estate;
import de.dis2011.data.EstateAgent;
import de.dis2011.data.House;

import java.util.Objects;

public class EstateMenu {

    private static EstateAgent currentAgent;

    /**
     * Zeigt die Wohnobjekt-Werwaltung
     */
    public static void show() {
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
        String login = FormUtil.readString("Login");
        String password = FormUtil.readString("Passwort");

        EstateAgent agent = EstateAgent.load(login);

        if (agent == null) {
            System.out.println("Makler mit dem angegebenen Login nicht gefunden.");
            return false;
        }
        if (!agent.getPassword().equals(password)) {
            System.out.println("Passwort falsch.");
            return false;
        }
        currentAgent = agent;
        return true;
    }

    private static void createEstate() {
        String type = "";
        while (!Objects.equals(type, "haus") && !Objects.equals(type, "wohnung")) {
            type = FormUtil.readString("Typ der Immobilie (Haus oder Wohnung):").toLowerCase();
        }

        Estate estate;
        if (type.equals("haus")) {
            estate = new House();
        } else {
            estate = new Apartment();
        }

        readEstateFields(estate, type);

        estate.save();
        System.out.println("Immobilie wurde mit ID " + estate.getId() + " erzeugt");
    }

    private static void readEstateFields(Estate estate, String type) {
        if (type.equals("haus") || type.equals("house")) {
            House house = (House) estate;
            house.setFloors(FormUtil.readInt("Anzahl Stockwerke"));
            house.setGarden(FormUtil.readBool("Garten"));
            house.setPrice(FormUtil.readInt("Preis"));
        } else if (type.equals("wohnung") || type.equals("apartment")){
            Apartment apartment = (Apartment) estate;
            apartment.setFloor(FormUtil.readInt("Etage"));
            apartment.setRent(FormUtil.readInt("Miete"));
            apartment.setRooms(FormUtil.readInt("Anzahl Räume"));
            apartment.setBalcony(FormUtil.readBool("Hat Balkon"));
            apartment.setBuiltInKitchen(FormUtil.readBool("Hat Einbauküche"));
        }

        estate.setCity(FormUtil.readString("Stadt"));
        estate.setStreet(FormUtil.readString("Straße"));
        estate.setStreetNumber(FormUtil.readInt("Hausnummer"));
        estate.setPostCode(FormUtil.readInt("PLZ"));
        estate.setSquareArea(FormUtil.readInt("Fläche in qm"));

        estate.setAgent(currentAgent.getLogin());
    }

    private static void editEstate() {
        int id = FormUtil.readInt("ID der Immobilie");
        Estate estate = Estate.load(id);

        if (estate == null) {
            System.out.println("Immobilie mit ID " + id + " existiert nicht.");
            return;
        }

        readEstateFields(estate, estate.getType());

        estate.save();
        System.out.println("Immobilie mit ID " + estate.getId() + " wurde bearbeitet");
    }

    private static void deleteEstate() {
        int id = FormUtil.readInt("ID der Immobilie");
        Estate estate = Estate.load(id);
        if (estate == null) {
            System.out.println("Immobilie mit ID " + id + " existiert nicht.");
            return;
        }
        estate.delete(estate.getType());
        System.out.println("Immobilie mit ID " + id + " wurde gelöscht.");
    }
}
