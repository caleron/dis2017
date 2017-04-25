package de.dis2011.menus;

import de.dis2011.FormUtil;
import de.dis2011.Menu;
import de.dis2011.data.*;

import java.util.List;

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
        person.save();

        System.out.println("Person mit der Id " + person.getId() + " wurde erzeugt.");
    }

    /**
     * Legt einen neuen Vertrag an, nachdem der Benutzer
     * die entprechenden Daten eingegeben hat.
     */
    private static void createContract() {
        int estateId = (FormUtil.readInt("Immobilien ID"));
        Estate estate = Estate.load(estateId);

        if (estate == null) {
            System.out.println("Immobilie mit ID " + estateId + " existiert nicht.");
            return;
        }

        int personID = FormUtil.readInt("Person ID");

        Person person = Person.load(personID);

        if (person == null) {
            System.out.println("Person mit ID " + personID + " existiert nicht.");
            return;
        }

        Contract contract;
        if (estate.getType().equals("house")) {
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

        contract.setPersonID(personID);
        contract.setDate(FormUtil.readString("Datum"));
        contract.setPlace(FormUtil.readString("Ort"));
        contract.setEstateID(estateId);
        contract.save();

        System.out.println("Vetrag mit der Vertragsnummer " + contract.getContract_no() + " wurde erzeugt.");
    }

    private static void showContracts() {
        List<Contract> contracts = Contract.getAllContracts();

        for (Contract contract : contracts) {
            if (contract instanceof PurchaseContract) {
                System.out.println("Kaufvertrag mit ID " + contract.getContract_no());
            } else {
                System.out.println("Mietvertrag mit ID " + contract.getContract_no());
            }
        }
    }

    private static void showContractDetails() {
        int contractId = (FormUtil.readInt("ID des Vertrags"));
        Contract contract = Contract.load(contractId);

        if (contract == null) {
            System.out.println("Immobilie mit ID " + contractId + " existiert nicht.");
            return;
        }

        if (contract instanceof PurchaseContract) {
            System.out.println("Kaufvertrag mit ID " + contract.getContract_no());
            PurchaseContract purchaseContract = (PurchaseContract) contract;
            System.out.println("Interestrate: " + purchaseContract.getInterestRate());
            System.out.println("Anzahl Raten: " + purchaseContract.getNoOfInstallments());
        } else {
            System.out.println("Mietvertrag mit ID " + contract.getContract_no());
            TenancyContract tenancyContract = (TenancyContract) contract;
            System.out.println("Startdatum: " + tenancyContract.getStartDate());
            System.out.println("Mietdauer: " + tenancyContract.getDuration());
            System.out.println("Nebenkosten: " + tenancyContract.getAdditionalCosts());

        }
        System.out.println("Kunde hat ID " + contract.getPersonID());
        System.out.println("Datum: " + contract.getDate());
        System.out.println("Ort: " + contract.getPlace());
        System.out.println("ID der Immobilie: " + contract.getEstateID());
    }
}
