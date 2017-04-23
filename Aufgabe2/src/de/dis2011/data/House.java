package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class House extends Estate {
    private int floors;
    private double price;
    private boolean garden;

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isGarden() {
        return garden;
    }

    public void setGarden(boolean garden) {
        this.garden = garden;
    }

    public House(int id) {
        super("house", id);
    }

    @Override
    public void loadSpecificFields() {
        try {
            // Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM HOUSE WHERE ESTATE = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                floors = rs.getInt("FLOOR");
                price = rs.getDouble("PRICE");
                garden = rs.getBoolean("GARDEN");

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
                String insertSQL = "INSERT INTO HOUSE(FLOOR, PRICE, GARDEN, ESTATE) VALUES (?,?,?,?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL);

                // Setze Anfrageparameter und fC<hre Anfrage ausp
                pstmt.setInt(1, floors);
                pstmt.setDouble(2, price);
                pstmt.setBoolean(3, garden);
                pstmt.setInt(4, id);
                pstmt.executeUpdate();

                pstmt.close();
            } else {
                // Falls schon eine ID vorhanden ist, mache ein Update...
                String updateSQL = "UPDATE HOUSE SET FLOOR = ?, PRICE = ?, GARDEN = ? WHERE ESTATE = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                // Setze Anfrage Parameter
                pstmt.setInt(1, floors);
                pstmt.setDouble(2, price);
                pstmt.setBoolean(3, garden);
                pstmt.setInt(4, id);
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
            String updateSQL = "DELETE FROM HOUSE WHERE ESTATE = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSQL);

            // Setze Anfrage Parameter
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
