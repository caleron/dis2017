package de.dis2011.data;

import java.sql.*;
import java.util.Objects;

/**
 * Created by Patrick on 21.04.2017.
 */
public abstract class Estate {
    protected int id = -1;
    protected String city;
    protected String street;
    protected int streetNumber;
    protected int postCode;
    protected int squareArea;
    protected String agent;
    protected String type;

    public String getType() {
        return type;
    }

    public Estate(String type) {
        this.type = type;
    }

    public Estate(String type, int id) {
        this(type);
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public void setSquareArea(int squareArea) {
        this.squareArea = squareArea;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public int getId() {
        return id;
    }

    public static Estate load(int id) {
        try {
            // Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM ESTATE WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String type = rs.getString("ESTATE_TYPE");
                Estate estate;
                if (Objects.equals(type, "house")) {
                    estate = new House(rs.getInt("ID"));
                } else {
                    estate = new Apartment(rs.getInt("ID"));
                }

                estate.setCity(rs.getString("CITY"));
                estate.setStreet(rs.getString("STREET"));
                estate.setStreetNumber(rs.getInt("STREETNUMBER"));
                estate.setPostCode(rs.getInt("POSTCODE"));
                estate.setSquareArea(rs.getInt("SQUAREAREA"));
                estate.setAgent(rs.getString("AGENT"));

                rs.close();
                pstmt.close();

                estate.loadSpecificFields();

                return estate;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract void loadSpecificFields();

    public void save() {
        boolean inserted = saveEstate();
        saveSpecificFields(inserted);
    }

    protected abstract void saveSpecificFields(boolean createNew);

    /**
     * Speichert das Wohnobjekt in der Datenbank. Ist noch keine ID vergeben
     * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
     */
    private boolean saveEstate() {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
            if (id == -1) {
                String insertSQL = "INSERT INTO ESTATE(CITY, STREET, STREETNUMBER, POSTCODE, SQUAREAREA, AGENT, ESTATE_TYPE) VALUES (?,?,?,?,?,?,?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

                // Setze Anfrageparameter und fC<hre Anfrage ausp
                pstmt.setString(1, city);
                pstmt.setString(2, street);
                pstmt.setInt(3, streetNumber);
                pstmt.setInt(4, postCode);
                pstmt.setInt(5, squareArea);
                pstmt.setString(6, agent);
                pstmt.executeUpdate();

                // Hole die Id des engefC<gten Datensatzes
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                rs.close();
                pstmt.close();
                return true;
            } else {
                // Falls schon eine ID vorhanden ist, mache ein Update...
                String updateSQL = "UPDATE ESTATE SET CITY = ?, STREET = ?, STREETNUMBER = ?, POSTCODE = ?, SQUAREAREA = ?, AGENT = ? WHERE ID = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                // Setze Anfrage Parameter
                pstmt.setString(1, city);
                pstmt.setString(2, street);
                pstmt.setInt(3, streetNumber);
                pstmt.setInt(4, postCode);
                pstmt.setInt(5, squareArea);
                pstmt.setString(6, agent);
                pstmt.setInt(7, id);
                pstmt.executeUpdate();

                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void delete() {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // SQL-Befehl zum entfernen des Wohn Objekts
            String updateSQL = "DELETE FROM ESTATE WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSQL);

            // Setze Anfrage Parameter
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            deleteSpecific();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract void deleteSpecific();
}
