package de.dis2011.data;

import java.sql.*;

/**
 * Created by Patrick on 21.04.2017.
 */
public class Estate {
    private int id = -1;
    private String city;
    private String street;
    private int streetNumber;
    private int postCode;
    private int squareArea;
    private String agent;

    public Estate() {
    }

    public Estate(int id) {
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
                Estate estate = new Estate(rs.getInt("ID"));

                estate.setCity(rs.getString("CITY"));
                estate.setStreet(rs.getString("STREET"));
                estate.setStreetNumber(rs.getInt("STREETNUMBER"));
                estate.setPostCode(rs.getInt("POSTCODE"));
                estate.setSquareArea(rs.getInt("SQUAREAREA"));
                estate.setAgent(rs.getString("AGENT"));

                rs.close();
                pstmt.close();
                return estate;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Speichert den Makler in der Datenbank. Ist noch keine ID vergeben
     * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
     */
    public void save() {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
            if (id == -1) {
                String insertSQL = "INSERT INTO ESTATE(CITY, STREET, STREETNUMBER, POSTCODE, SQUAREAREA, AGENT) VALUES (?,?,?,?,?,?)";

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
    }

    public void delete() {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // SQL-Befehl zum entfernen des Maklers
            String updateSQL = "DELETE FROM ESTATE WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSQL);

            // Setze Anfrage Parameter
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
