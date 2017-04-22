package de.dis2011.data;

import java.sql.*;

/**
 * Created by Max on 22.04.2017.
 */
public class Person {
    private int id;
    private String first_name;
    private String name;
    private String adress;
    
    private boolean isInDb;

    public Person(boolean isInDb) {
    	this.isInDb = isInDb;
    }
    
    public void setInDb(boolean inDb) {
        isInDb = inDb;
    }
    
    public Person(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    /**
     * Lädt eine Person aus der Datenbank
     *
     * @param id Personen ID
     * @return Personen-Instanz
     */
    public static Person load(int id) {
        try {
            // Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM PERSON WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setInt(1, id);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Person person = new Person(rs.getInt("ID"));
                person.setFirst_name(rs.getString("FIRST_NAME"));
                person.setName(rs.getString("NAME"));
                person.setAdress(rs.getString("ADRESS"));

                rs.close();
                pstmt.close();
                return person;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Speichert die Person in der Datenbank. Ist noch keine ID vergeben
     * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
     */
    public void save() {
    	   // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
            if (!isInDb) {
                String insertSQL = "INSERT INTO PERSON(ID, FIRST_NAME, NAME, ADRESS) VALUES (?,?,?,?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

                // Setze Anfrageparameter und fC<hre Anfrage ausp
                pstmt.setInt(1, id);
                pstmt.setString(2, first_name);
                pstmt.setString(3, name);
                pstmt.setString(4, adress);
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
                String updateSQL = "UPDATE PERSON SET FIRST_NAME = ?, NAME = ?, ADRESS = ? WHERE ID = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                // Setze Anfrage Parameter
                pstmt.setString(1, first_name);
                pstmt.setString(2, name);
                pstmt.setString(3, adress);
                pstmt.setInt(4, id);
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
            String updateSQL = "DELETE FROM PERSON WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSQL);

            // Setze Anfrage Parameter
            pstmt.setInt(1, getId());
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
            String updateSQL = "UPDATE PERSON SET FIRST_NAME = ?, NAME = ?, ADRESS = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSQL);

            // Setze Anfrage Parameter
            pstmt.setString(1, getFirst_name());
            pstmt.setString(2, getName());
            pstmt.setString(3, getAdress());
            pstmt.setInt(4, getId());

            //false zurückgeben, falls keine zeile bearbeitet wurde --> falscher login
            return pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
