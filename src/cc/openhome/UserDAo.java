package cc.openhome;

import java.sql.*;

public class UserDAo {
	private static String driver = "com.mysql.jdbc.Driver"; 
	private static String url = "jdbc:mysql://localhost/web_db?useUnicode=true&amp;characterEncoding=utf-8";
	private static String user = "root"; 
	private static String password = "123456";
	
	public static Connection getconn() throws SQLException {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection conn = DriverManager.getConnection(url,user, password);
			return conn;
    }
}
