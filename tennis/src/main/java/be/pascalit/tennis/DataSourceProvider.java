package be.pascalit.tennis;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DataSourceProvider {
	
	static {
		buildDataSourceInstance();
	}

	private static BasicDataSource singleDataSource;
	
	private final static String MYSQL_URL = "jdbc:mysql://localhost:3306/tennis?" 
			+ "useSSL=false"
			+ "&allowPublicKeyRetrieval=true" 
			// + "&useLegacyDatetimeCode=false" 
			// + "&serverTimezone=Europe/Paris"
			;

	// Singleton (private constructor)
	private DataSourceProvider() {
	}; 

	// Static builder
	private static DataSource buildDataSourceInstance() {

		if (singleDataSource == null) {
			singleDataSource = new BasicDataSource();
			singleDataSource.setInitialSize(5); // 5 concurrent
			singleDataSource.setUrl(MYSQL_URL);
			singleDataSource.setUsername("Pascal");
			singleDataSource.setPassword("...");
		}
		
		return singleDataSource;
	}
	
	// Get a JDBC connection from DataSource pool
	public static Connection getConnection() throws SQLException {
		return singleDataSource.getConnection();
	}
	
	public static void printStatistics() {
		System.out.println("Max. active connections=" + singleDataSource.getMaxTotal());
		System.out.println("Active connections=" + singleDataSource.getNumActive());
	}
}
