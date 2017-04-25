package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Contract {
    protected int contract_no = -1;
    protected String date;
    protected String place;
    protected int estateID;
    protected int personID;
    protected String contractType;

    public String getContractType() {
        return contractType;
    }


    public Contract(String type) {
        this.contractType = type;
    }

    public Contract(String type, int contract_no) {
        this(type);
        this.contract_no = contract_no;
    }

    public int getContract_no() {
        return contract_no;
    }

    public void setContract_no(int contract_no) {
        this.contract_no = contract_no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getEstateID() {
        return estateID;
    }

    public void setEstateID(int estateID) {
        this.estateID = estateID;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public abstract void loadSpecificFields();

    /**
     * Lädt einen Vertrag aus der Datenbank
     *
     * @param contract_no Vertragsnummer
     * @return Vertrags-Instanz
     */
    public static Contract load(int contract_no) {
        try {
            // Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM CONTRACT WHERE CONTRACT_NO = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, contract_no);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Contract contract;
                String type = rs.getString("CONTRACT_TYPE");
                if (Objects.equals(type, "house")) {
                    contract = new PurchaseContract(rs.getInt("CONTRACT_NO"));
                } else {
                    contract = new TenancyContract(rs.getInt("CONTRACT_NO"));
                }

                contract.setDate(rs.getString("DATE"));
                contract.setPlace(rs.getString("PLACE"));
                contract.setEstateID(rs.getInt("ESTATE"));
                contract.setPersonID(rs.getInt("PERSON"));

                rs.close();
                pstmt.close();

                contract.loadSpecificFields();
                return contract;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save() {
        boolean inserted = saveContract();
        saveSpecificFields(inserted);
    }

    protected abstract void saveSpecificFields(boolean createNew);


    /**
     * Speichert den Vertrag in der Datenbank. Ist noch keine Contract_no vergeben
     * worden, wird die generierte Contract_no von DB2 geholt und dem Model übergeben.
     * <p>
     * returns true if a new contract was inserted
     */
    private boolean saveContract() {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
            if (contract_no == -1) {
                String insertSQL = "INSERT INTO CONTRACT(DATE, PLACE, ESTATE, PERSON, CONTRACT_TYPE) VALUES (?,?,?,?,?)";
                String[] id_col = {"CONTRACT_NO"};
                PreparedStatement pstmt = con.prepareStatement(insertSQL, id_col);

                // Setze Anfrageparameter und fC<hre Anfrage ausp
                pstmt.setString(1, date);
                pstmt.setString(2, place);
                pstmt.setInt(3, estateID);
                pstmt.setInt(4, personID);
                pstmt.setString(5, contractType);
                pstmt.executeUpdate();

                // Hole die Id des engefC<gten Datensatzes
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    contract_no = rs.getInt(1);
                }
                rs.close();
                pstmt.close();
                return true;
            } else {
                // Falls schon eine ID vorhanden ist, mache ein Update...
                String updateSQL = "UPDATE CONTRACT SET DATE = ?, PLACE = ?, ESTATE = ?, PERSON = ? WHERE CONTRACT_NO = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                // Setze Anfrage Parameter
                pstmt.setString(1, date);
                pstmt.setString(2, place);
                pstmt.setInt(3, estateID);
                pstmt.setInt(4, personID);
                pstmt.setInt(5, contract_no);
                pstmt.executeUpdate();

                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Contract> getAllContracts() {
        ArrayList<Contract> list = new ArrayList<>();
        try {
            // Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM CONTRACT";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Contract contract;
                String type = rs.getString("CONTRACT_TYPE");
                if (Objects.equals(type, "house")) {
                    contract = new PurchaseContract(rs.getInt("CONTRACT_NO"));
                } else {
                    contract = new TenancyContract(rs.getInt("CONTRACT_NO"));
                }

                contract.setDate(rs.getString("DATE"));
                contract.setPlace(rs.getString("PLACE"));
                contract.setEstateID(rs.getInt("ESTATE"));
                contract.setPersonID(rs.getInt("PERSON"));

                contract.loadSpecificFields();
                list.add(contract);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
