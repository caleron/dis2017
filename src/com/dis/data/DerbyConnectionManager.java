package com.dis.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyConnectionManager {

    // instance of Driver Manager
    private static DerbyConnectionManager _instance = null;

    // DB2 connection
    private Connection _con;

    /**
     * Erzeugt eine Datenbank-Verbindung
     */
    private DerbyConnectionManager() {
        try {
            // Holen der Einstellungen aus der db2.properties Datei

            String jdbcUser = "root";
            String jdbcPass = "";
            String jdbcUrl = "jdbc:derby:data;create=true";

            // Verbindung zu Derby herstellen
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            _con = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Liefert Instanz des Managers
     *
     * @return DB2ConnectionManager
     */
    public static DerbyConnectionManager getInstance() {
        if (_instance == null) {
            _instance = new DerbyConnectionManager();
        }
        return _instance;
    }

    /**
     * Liefert eine Verbindung zur DB2 zurück
     *
     * @return Connection
     */
    public Connection getConnection() {
        return _con;
    }
}
