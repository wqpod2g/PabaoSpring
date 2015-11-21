package nju.iip.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	private static Connection conn = null;

	public static Connection getConn() {
		try {
			Class.forName(Config.getValue("DBDRIVER")).newInstance();
			conn = DriverManager.getConnection(Config.getValue("DBURL"),Config.getValue("DBUSER"),Config.getValue("DBPASSWORD"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
