package be.pascalit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import be.pascalit.tennis.entities.Player;
import be.pascalit.tennis.entities.Player.Sex;
import be.pascalit.util.DbUtils;

public class TestDbSqlSimpleQueries {

	public static void main(String... args) {
		Connection conn = null;
		try {
			// MySQL driver MySQL Connector
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/tennis?" + "useSSL=false" + "&allowPublicKeyRetrieval=true"
									+ "&useLegacyDatetimeCode=false" + "&serverTimezone=Europe/Paris",
							"Pascal", "Pascal");

			// List of all tennis players
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from joueur");
			System.out.println("List of all tennis players:");
			while (rs.next()) {
				final long id = rs.getLong("ID");
				final String nom = rs.getString("NOM");
				final String prenom = rs.getString("PRENOM");
				final char sexe = rs.getString("SEXE").charAt(0);
				Player player = new Player(id, nom, prenom, Sex.valueOf(sexe));
				System.out.println(player);
			}

			DbUtils.printMetadatas(rs);

			// Search a tennis player with his ID
			PreparedStatement preparedStatement = conn.prepareStatement("select NOM,PRENOM,ID from JOUEUR where ID=?");
			long identifiant = 12L;
			preparedStatement.setLong(1, identifiant);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				final long id = rs.getLong("ID");
				final String nom = rs.getString("NOM");
				final String prenom = rs.getString("PRENOM");
				final char sexe = rs.getString("SEXE").charAt(0);
				Player player = new Player(id, nom, prenom, Sex.valueOf(sexe));
				System.out.println(player);

			} else {
				System.out.println("Il n'y a pas d'enregistrement avec un ID=128");
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
