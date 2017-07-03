package com.dis.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Einfaches Singleton zur Verwaltung von Datenbank-Verbindungen.
 *
 * @author Michael von Riegen
 * @version April 2009
 */
public class DB2ConnectionManager {

	// instance of Driver Manager
	private static DB2ConnectionManager _instance = null;

	// DB2 connection
	private Connection _con;

	/**
	 * Erzeugt eine Datenbank-Verbindung
	 */
	private DB2ConnectionManager() {
		try {
			// Holen der Einstellungen aus der db2.properties Datei

			String jdbcUser = "VSISP16";
			String jdbcPass = "qhK3CB3S";
			String jdbcUrl = "jdbc:db2://vsisls4.informatik.uni-hamburg.de:50001/VSISP";

			// Verbindung zur DB2 herstellen
			Class.forName("com.ibm.db2.jcc.DB2Driver");
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
	public static DB2ConnectionManager getInstance() {
		if (_instance == null) {
			_instance = new DB2ConnectionManager();
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