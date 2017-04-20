package de.dis2011.data;

import java.sql.*;

/**
 * Makler-Bean
 * <p>
 * Beispiel-Tabelle: CREATE TABLE makler(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1,
 * NO CACHE) PRIMARY KEY, name varchar(255), address varchar(255), login varchar(40) UNIQUE, password varchar(40));
 */
public class EstateAgent {
    private String name;
    private String address;
    private String login;
    private String password;

    private boolean isInDb;
    private String originallyLogin;

    public EstateAgent(boolean isInDb) {
        this.isInDb = isInDb;
    }

    public EstateAgent(String name, String address, String login, String password, boolean isInDb) {
        this.name = name;
        this.address = address;
        this.login = login;
        this.password = password;
        this.originallyLogin = login;
        this.isInDb = isInDb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Lädt einen Makler aus der Datenbank
     *
     * @param login login des zu ladenden Maklers
     * @return Makler-Instanz
     */
    public static EstateAgent load(String login) {
        try {
            // Hole Verbindung
            Connection con = DB2ConnectionManager.getInstance().getConnection();

            // Erzeuge Anfrage
            String selectSQL = "SELECT * FROM ESTATE_AGENT WHERE LOGIN = ?";
            PreparedStatement pstmt = con.prepareStatement(selectSQL);
            pstmt.setString(1, login);

            // Führe Anfrage aus
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                EstateAgent ts = new EstateAgent(true);
                ts.setName(rs.getString("name"));
                ts.setAddress(rs.getString("address"));
                ts.setLogin(rs.getString("login"));
                ts.setPassword(rs.getString("password"));

                rs.close();
                pstmt.close();
                return ts;
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
            if (!isInDb) {
                // Achtung, hier wird noch ein Parameter mitgegeben,
                // damit spC$ter generierte IDs zurC<ckgeliefert werden!
                String insertSQL = "INSERT INTO ESTATE_AGENT(name, address, login, password) VALUES (?, ?, ?, ?)";

                PreparedStatement pstmt = con.prepareStatement(insertSQL,
                        Statement.RETURN_GENERATED_KEYS);

                // Setze Anfrageparameter und fC<hre Anfrage aus
                pstmt.setString(1, getName());
                pstmt.setString(2, getAddress());
                pstmt.setString(3, getLogin());
                pstmt.setString(4, getPassword());
                pstmt.executeUpdate();
            } else {
                // Falls schon eine ID vorhanden ist, mache ein Update...
                String updateSQL = "UPDATE ESTATE_AGENT SET name = ?, address = ?, login = ?, password = ? WHERE LOGIN = ?";
                PreparedStatement pstmt = con.prepareStatement(updateSQL);

                // Setze Anfrage Parameter
                pstmt.setString(1, getName());
                pstmt.setString(2, getAddress());
                pstmt.setString(3, getLogin());
                pstmt.setString(4, getPassword());
                pstmt.setString(5, originallyLogin);
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
            String updateSQL = "DELETE FROM ESTATE_AGENT WHERE LOGIN = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSQL);

            // Setze Anfrage Parameter
            pstmt.setString(1, getLogin());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void edit() {
        // Hole Verbindung
        Connection con = DB2ConnectionManager.getInstance().getConnection();

        try {
            // SQL-Befehl zum entfernen des Maklers
            String updateSQL = "UPDATE ESTATE_AGENT SET name = ?, address = ?, password = ? WHERE LOGIN = ?";
            PreparedStatement pstmt = con.prepareStatement(updateSQL);

            // Setze Anfrage Parameter
            pstmt.setString(1, getName());
            pstmt.setString(2, getAddress());
            pstmt.setString(3, getPassword());
            pstmt.setString(4, getLogin());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
