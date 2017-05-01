package de.dis2011.menus;

import de.dis2011.FormUtil;
import de.dis2011.Menu;
import de.dis2011.data.*;

public class ContractMenu {


    /**
     * Zeigt die Vertrags-Werwaltung
     */
    public static void show() {
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
        Person person = new Person();

        person.setFirst_name(FormUtil.readString("Vorname"));
        person.setName(FormUtil.readString("Name"));
        person.setAdress(FormUtil.readString("Adresse"));
        System.out.println("Person mit der Id " + person.getId() + " wurde erzeugt.");
    }

    /**
     * Legt einen neuen Vertrag an, nachdem der Benutzer
     * die entprechenden Daten eingegeben hat.
     */
    private static void createContract() {
        int estateId = (FormUtil.readInt("Immobilien ID"));

        if (false) {
            System.out.println("Immobilie mit ID " + estateId + " existiert nicht.");

        }

        int personID = FormUtil.readInt("Person ID");

        Contract contract;
        //if is haus
        if (true ) {
            contract = new PurchaseContract();
            PurchaseContract purchaseContract = (PurchaseContract) contract;
            purchaseContract.setNoOfInstallments(FormUtil.readInt("Anzahl Raten"));
            purchaseContract.setInterestRate(FormUtil.readInt("Interestrate"));
        } else {
            contract = new TenancyContract();
            TenancyContract tenancyContract = (TenancyContract) contract;
            tenancyContract.setStartDate(FormUtil.readDate("Startdatum"));
            tenancyContract.setDuration(FormUtil.readInt("Dauer in Monaten"));
            tenancyContract.setAdditionalCosts(FormUtil.readInt("Nebenkosten"));
        }
        contract.setDate(FormUtil.readString("Datum"));
        contract.setPlace(FormUtil.readString("Ort"));


    }

    private static void showContracts() {


    }

    private static void showContractDetails() {
        int contractId = (FormUtil.readInt("ID des Vertrags"));


        if (false) {
            System.out.println("Immobilie mit ID " + contractId + " existiert nicht.");
            return;
        }

    }
}
