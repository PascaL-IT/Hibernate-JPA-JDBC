package be.pascalit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

import be.pascalit.util.DbUtils;

public class TestDbBasicDataSource {
	
	private final static String MYSQL_URL = "jdbc:mysql://localhost:3306/tennis?" + "useSSL=false"
			+ "&allowPublicKeyRetrieval=true" + "&useLegacyDatetimeCode=false" + "&serverTimezone=Europe/Paris";

	public static void main(String... args) {

		Connection conn = null;
		BasicDataSource bds = null;
		
		try {
			// Basic Apache Common DBCP 
			bds = new BasicDataSource();
			bds.setInitialSize(5);
			bds.setUrl(MYSQL_URL);
			bds.setUsername("Pascal");
			bds.setPassword("...");

			System.out.println("Successful connection with BasicDataSource !");

			conn = bds.getConnection();
			// conn = bds.getConnection("Pascal", "Pascal"); // BasicDataSource does NOT support this method.

			Statement statement = conn.createStatement();
			boolean res = statement.execute("select version(), current_date, now();");
			if (res) {
				int type = statement.getResultSetType();
				System.out.println("ResultSet: type is " + type);
				ResultSet rs = statement.getResultSet();
				if (rs.next()) {
					ResultSetMetaData rsm = rs.getMetaData();
					for (int j = 1; j <= rsm.getColumnCount(); ++j) {
						System.out.println("ResultSet: " + DbUtils.getMetadataLabel(rsm, j) + " is " + rs.getString(j));
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				
				if (bds != null) {
					bds.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
