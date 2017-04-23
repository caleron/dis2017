package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Patrick on 23.04.2017.
 */
public class Apartment extends Estate {
    private int floor;
    private double rent;
    private int rooms;
    private boolean balcony;
    private boolean builtInKitchen;

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public boolean isBalcony() {
        return balcony;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public boolean isBuiltInKitchen() {
        return builtInKitchen;
    }

    public void setBuiltInKitchen(boolean builtInKitchen) {
        this.builtInKitchen = builtInKitchen;
    }

    public Apartment(int id) {
        super("apartment", id);
    }

    @Override
    public void loadSpecificFields() {
        try {
            // Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM APARTMENT WHERE ESTATE = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                floor = rs.getInt("FLOOR");
                rent = rs.getDouble("RENT");
                rooms = rs.getInt("ROOMS");
                balcony = rs.getBoolean("BALCONY");
                builtInKitchen = rs.getBoolean("BUILT_IN_KITCHEN");

                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void saveSpecificFields(boolean createNew) {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
            if (createNew) {
                String insertSQL = "INSERT INTO APARTMENT(FLOOR, RENT, ROOMS, BALCONY, BUILT_IN_KITCHEN, ESTATE) VALUES (?,?,?,?,?,?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL);

                // Setze Anfrageparameter und fC<hre Anfrage ausp
                pstmt.setInt(1, floor);
                pstmt.setDouble(2, rent);
                pstmt.setInt(3, rooms);
                pstmt.setBoolean(4, balcony);
                pstmt.setBoolean(5, builtInKitchen);
                pstmt.setInt(6, id);
                pstmt.executeUpdate();

                pstmt.close();
            } else {
                // Falls schon eine ID vorhanden ist, mache ein Update...
                String updateSQL = "UPDATE APARTMENT SET FLOOR = ?, RENT = ?, ROOMS = ?, BALCONY =?, BUILT_IN_KITCHEN = ? WHERE ESTATE = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                // Setze Anfrage Parameter
                pstmt.setInt(1, floor);
                pstmt.setDouble(2, rent);
                pstmt.setInt(3, rooms);
                pstmt.setBoolean(4, balcony);
                pstmt.setBoolean(5, builtInKitchen);
                pstmt.setInt(6, id);
                pstmt.executeUpdate();

                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void deleteSpecific() {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // SQL-Befehl zum entfernen des Wohn Objekts
            String updateSQL = "DELETE FROM APARTMENT WHERE ESTATE = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSQL);

            // Setze Anfrage Parameter
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
