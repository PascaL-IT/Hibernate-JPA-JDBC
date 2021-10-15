package be.pascalit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import be.pascalit.util.DbUtils;

public class TestDbConnection {

	public static void main(String... args) {
		Connection conn = null;
		try {
			// Seulement avant Java 7/JDBC 4
			// Class.forName(DRIVER_CLASS_NAME);

			// MySQL driver MySQL Connector
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/tennis?"
					+ "useSSL=false"
					+ "&allowPublicKeyRetrieval=true"		
					+ "&useLegacyDatetimeCode=false"
					+ "&serverTimezone=Europe/Paris",
					"Pascal", "Pascal");
			// Oracle Driver officiel OJDBC Thin
			// conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:tennis","COURSDB","COURSDB");

			// Postgres Driver officiel
			// conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tennis","COURSDB","COURSDB");

			System.out.println("Successful connection with DriverManager!");
			Statement statement = conn.createStatement();
			boolean res = statement.execute("select version(), current_date, now();");
			if (res) {
				int type = statement.getResultSetType();
				System.out.println("ResultSet: type is " + type);
				ResultSet rs = statement.getResultSet();
				if (rs.next()) {
					ResultSetMetaData rsm = rs.getMetaData();
					for (int j=1; j <= rsm.getColumnCount(); ++j) {
						System.out.println("ResultSet: " + DbUtils.getMetadataLabel(rsm, j) + " is " + rs.getString(j));
					}
				}
				DbUtils.printMetadatas(rs);
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
}
