package be.pascalit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestDbSqlUpdateQueries {

	public static void main(String... args) {
		Connection conn = null;
		try {
			// MySQL driver MySQL Connector
			conn = DriverManager
					.getConnection(
					  "jdbc:mysql://localhost:3306/tennis?" 
					+ "useSSL=false" 
					+ "&allowPublicKeyRetrieval=true"
					+ "&useLegacyDatetimeCode=false"
					+ "&serverTimezone=Europe/Paris",
					"Pascal", "Pascal");

			// Add a new tennis player
			PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO tennis.joueur (NOM, PRENOM, SEXE) VALUES (?, ?, ?)");
			preparedStatement.setString(1, "Limage"); // Nom
			preparedStatement.setString(2, "Pascal"); // Prénom
			preparedStatement.setString(3, "H"); // Sexe
			
			int rowCount = preparedStatement.executeUpdate();
			if (rowCount > 0) {
				System.out.printf("OK - Row count=%d\n", rowCount);	// +1 row => total of 44 rows			
			} else {
				System.err.printf("ERROR - Row count=%d\n", rowCount);
			}
			
			// Update a tennis player info
			final int identifiant = 24;
			final String newPrenom = "Sara";
			preparedStatement = conn.prepareStatement("UPDATE TENNIS.JOUEUR SET PRENOM=? WHERE ID=?");
			preparedStatement.setString(1, newPrenom); // Prénom Sara instead of Sarah for ID=24
			preparedStatement.setLong(2, identifiant); // ID=24 for Errani Sarah
			
			rowCount = preparedStatement.executeUpdate();
			if (rowCount > 0) {
				System.out.printf("OK - Row count=%d", rowCount);				
			} else {
				System.err.printf("ERROR - Row count=%d for ID=%d", rowCount, identifiant);
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
