package be.pascalit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import be.pascalit.util.DbUtils;

public class TestDbSqlTransaction {
	
	private static PreparedStatement preparedStatement;

	public static void main(String... args) {
		
		Connection conn = null;
		
		try {
			// MySQL driver MySQL Connector
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/tennis?" + "useSSL=false" + "&allowPublicKeyRetrieval=true"
									+ "&useLegacyDatetimeCode=false" + "&serverTimezone=Europe/Paris",
							"Pascal", "Pascal");

			final String table = "tennis.joueur";
			final long initialNbrRows = DbUtils.getNumberOfRows(table, conn);
			
			// Enable transaction (SQL statements are grouped)
			conn.setAutoCommit(false);
			
			// Add additional tennis players that are missing
			String[][] newPlayers = { { "Capriati" , "Jennifer" , "F" } , 
					                  { "Johannson" , "Thomas" , "H" } };
			
			preparedStatement = conn.prepareStatement("INSERT INTO tennis.joueur (NOM, PRENOM, SEXE) VALUES (?, ?, ?)");
			
			int countAdditionalRows = 0;
			for (int r=0; r< newPlayers.length ; ++r) {
				countAdditionalRows += insertNewPlayer(newPlayers[r][0], newPlayers[r][1], newPlayers[r][2]); // r = row
			}

			// Commit transaction of two consecutive insert
			conn.commit();
			
			//Check results
			long finalNbrRows = DbUtils.getNumberOfRows("joueur", conn);
			System.out.printf("Nbr of rows in table %s is now %d (incremented by %d = %d) ", table, finalNbrRows, (finalNbrRows - initialNbrRows), countAdditionalRows);

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		} finally {
			try {
				if (preparedStatement != null) {
					conn.close();
				}
				
				if (conn != null) {
					conn.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static int insertNewPlayer(String nom, String prenom, String sexe) throws SQLException {
		preparedStatement.setString(1, nom); // Nom
		preparedStatement.setString(2, prenom); // Prénom
		preparedStatement.setString(3, sexe); // Sexe
		return preparedStatement.executeUpdate();
	}
	
}
