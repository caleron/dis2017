package de.dis2011.menus;

import de.dis2011.FormUtil;
import de.dis2011.Menu;
import de.dis2011.data.Contract;
import de.dis2011.data.Person;

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
                    createPerson();
                    break;
                case NEW_CONTRACT:
                	createContract();
                    break;
                case SHOW_CONTRACTS:
                    // noch nix
                    break;
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
        Person person = new Person(false);

        person.setFirst_name(FormUtil.readString("Vorname"));
        person.setName(FormUtil.readString("Name"));
        person.setAdress(FormUtil.readString("Adresse"));
        person.setId(FormUtil.readInt("Id"));
        person.save();

        System.out.println("Person mit der Id " + person.getId() + " wurde erzeugt.");
    }
    
    /**
     * Legt einen neuen Vertrag an, nachdem der Benutzer
     * die entprechenden Daten eingegeben hat.
     */
    private static void createContract() {
        Contract contract = new Contract(false);

        contract.setDate(FormUtil.readString("Datum"));
        contract.setPlace(FormUtil.readString("Ort"));
        contract.setEstateID(FormUtil.readInt("Immobilien ID"));
        contract.setPersonID(FormUtil.readInt("Makler ID"));
        contract.save();

        System.out.println("Vetrag mit der Vertragsnummer " + contract.getContract_no() + " wurde erzeugt.");
    }
}
