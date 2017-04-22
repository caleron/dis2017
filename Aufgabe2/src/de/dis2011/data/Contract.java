package de.dis2011.data;

import java.sql.*;

/**
 * Created by Max on 22.04.2017.
 */
public class Contract {
    private int contract_no;
    private String date;
    private String place;
    private int estateID;
    private int personID;
    
    private boolean isInDb;

    public Contract(boolean isInDb) {
    	this.isInDb = isInDb;
    }
    
    public Contract(int contract_no) {
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
                Contract contract = new Contract(rs.getInt("CONTRACT_NO"));
                contract.setDate(rs.getString("DATE"));
                contract.setPlace(rs.getString("PLACE"));
                contract.setEstateID(rs.getInt("ESTATE"));
                contract.setPersonID(rs.getInt("PERSON"));

                rs.close();
                pstmt.close();
                return contract;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Speichert den Vertrag in der Datenbank. Ist noch keine Contract_no vergeben
     * worden, wird die generierte Contract_no von DB2 geholt und dem Model übergeben.
     */
    public void save() {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
            if (!isInDb) {
                String insertSQL = "INSERT INTO CONTRACT(CONTRACT_NO, DATE, PLACE, ESTATE, PERSON) VALUES (?,?,?,?,?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

                // Setze Anfrageparameter und fC<hre Anfrage ausp
                pstmt.setInt(1, contract_no);
                pstmt.setString(2, date);
                pstmt.setString(3, place);
                pstmt.setInt(4, estateID);
                pstmt.setInt(5, personID);
                pstmt.executeUpdate();

                // Hole die Id des engefC<gten Datensatzes
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    contract_no = rs.getInt(1);
                }
                rs.close();
                pstmt.close();
            } else {
                // Falls schon eine ID vorhanden ist, mache ein Update...
                String updateSQL = "UPDATE CONTRACT SET DATE = ?, PLACE = ?, ESTATE = ?, PERSON = ? WHERE CONTRACT_NO = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                // Setze Anfrage Parameter
                pstmt.setString(1, date);
                pstmt.setString(2, place);
                pstmt.setInt(3, estateID);
                pstmt.setInt(4, personID);
                pstmt.setInt(7, contract_no);
                pstmt.executeUpdate();

                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // SQL-Befehl zum entfernen des Vertrags
            String updateSQL = "DELETE FROM CONTRACT WHERE CONTRACT_NO = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSQL);

            // Setze Anfrage Parameter
            pstmt.setInt(1, getContract_no());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean edit() {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // SQL-Befehl zum entfernen des Vetrags
            String updateSQL = "UPDATE CONTRACT SET DATE = ?, PLACE = ?, ESTATE = ?, PERSON = ? WHERE CONTRACT_NO = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSQL);

            // Setze Anfrage Parameter
            pstmt.setString(1, getDate());
            pstmt.setString(2, getPlace());
            pstmt.setInt(3, getEstateID());
            pstmt.setInt(4, getPersonID());

            //false zurückgeben, falls keine zeile bearbeitet wurde --> falscher login
            return pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
