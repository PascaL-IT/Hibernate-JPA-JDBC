package be.pascalit.tennis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import be.pascalit.tennis.DataSourceProvider;
import be.pascalit.tennis.entities.Player;
import be.pascalit.tennis.entities.Player.Sex;
import be.pascalit.tennis.interfaces.PlayerDao;
import be.pascalit.util.DbUtils;

/**
 * PlayerDaoImpl (implement CRUD methods on Tennis for Player (JOUEUR table) with JDBC)
 * @author Pascal
 *
 */
public class PlayerDaoImpl implements PlayerDao {
	
	final static boolean VERBOSE = false; // debugging

	@Override
	/**
	 * Add an additional tennis player in JOUEUR table<br>
	 * Remark. this function updates the ID of the input player object. 
	 */
	public void create(Player player) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			// ps = conn.prepareStatement("insert into JOUEUR (NOM, PRENOM, SEXE) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS); // option 1
			ps = conn.prepareStatement("insert into JOUEUR (NOM, PRENOM, SEXE) values (?, ?, ?)", new String[] { "ID" } ); // option 2
			ps.setString(1, player.getName());
			ps.setString(2, player.getForename());
			ps.setString(3, String.valueOf(player.getSexValue()));

			int res = ps.executeUpdate();
			
			ResultSet rsKeys = ps.getGeneratedKeys();
			if (VERBOSE) DbUtils.printDebugKeys(rsKeys);

			if (rsKeys.next() && res == 1) {
				// System.out.println("DEBUG - key value: " + rsKeys.getInt(1));
				player.setId(rsKeys.getInt(1)); // set generated Id
			}
			
			System.out.println("Add " + res + " tennis player -> " + player);

		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (VERBOSE) DataSourceProvider.printStatistics();
		}

	}


	/**
	 * Update tennis player name by his Id
	 */
	public void updateName(final long playerId, final String playerName) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("update JOUEUR set NOM=? where ID=?");
			ps.setString(1, playerName);
			ps.setLong(2, playerId);

			int res = ps.executeUpdate();
			System.out.println("Update " + res + " tennis player name -> " + playerName + "(" + playerId + ")");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (VERBOSE) DataSourceProvider.printStatistics();
		}

	}

	@Override
	/**
	 * Delete a tennis player by his technical ID
	 */
	public void deleteById(long id) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("delete from JOUEUR where ID=?");
			ps.setLong(1, id);

			int res = ps.executeUpdate();
			System.out.println("Delete " + res + " tennis player with technical ID=" + id);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (VERBOSE) DataSourceProvider.printStatistics();
		}

	}

	@Override
	/**
	 * Delete a tennis player by his name and forename
	 */
	public void delete(String name, String forename) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("delete from JOUEUR where NOM=? and PRENOM=?");
			ps.setString(1, name);
			ps.setString(2, forename);

			int res = ps.executeUpdate();
			System.out.println("Delete " + res + " tennis player named " + name + " " + forename);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (VERBOSE) DataSourceProvider.printStatistics();
		}

	}

	@Override
	/**
	 * Retrieve a tennis player by his technical ID
	 */
	public Player getById(long id) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		Player player = null;

		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("select ID, NOM, PRENOM, SEXE from JOUEUR where ID=?");
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				player = getPlayerInstance(rs);
				System.out.println("Get tennis player with ID=" + id + " -> " + player);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (VERBOSE) DataSourceProvider.printStatistics();
		}

		return player;
	}

	@Override
	/**
	 * Retrieve a tennis player by his name and forename
	 */
	public Player get(String name, String forename) {
		Connection conn = null;
		PreparedStatement ps = null;
		Player player = null;

		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("select ID, NOM, PRENOM, SEXE from JOUEUR where NOM=? and PRENOM=?");
			ps.setString(1, name);
			ps.setString(2, forename);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				player = getPlayerInstance(rs);
				System.out.println("Get tennis player named " + name + " " + forename + " -> " + player);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (VERBOSE) DataSourceProvider.printStatistics();
		}

		return player;
	}

	@Override
	/**
	 * Retrieve list of all tennis players
	 */
	public List<Player> getFullList() {
		
		List<Player> list = new ArrayList<>();
		Connection conn = null;
		Statement st = null;
		try {
			conn = DataSourceProvider.getConnection();
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from joueur order by id");
			
			while (rs.next()) {
				list.add(getPlayerInstance(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (VERBOSE) DataSourceProvider.printStatistics();
		}
		return list;
	}

	@Override
	/**
	 * Retrieve list of all tennis players for a type of sex (F or M)
	 */
	public List<Player> getList(Sex sex) {
		List<Player> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("select * from JOUEUR where SEXE = ?");
			ps.setString(1, sex.getDbValue());

			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				list.add(getPlayerInstance(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (VERBOSE) DataSourceProvider.printStatistics();
		}
		return list;
	}

	@Override
	/**
	 * Retrieve list of all tennis players for a type of sex (F or M)
	 */
	public List<Player> getList(long minId, long maxId) {
		
		if (maxId < minId) {
			throw new IllegalArgumentException("maxId must be greater or equal to minId");
		}
		
		List<Player> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("select * from JOUEUR where ID >= ? and ID <= ?");
			ps.setLong(1, minId);
			ps.setLong(2, maxId);

			ResultSet rs = ps.executeQuery();
			System.out.println("List of tennis players in the range [" + minId + " , " + maxId + "] :");
			while (rs.next()) {
				list.add(getPlayerInstance(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (VERBOSE) DataSourceProvider.printStatistics();
		}
		return list;
	}

	
	/**
	 * getPlayerInstance
	 * 
	 * @param ResultSet of a player (JOUEUR : ID, NOM, PRENOM, SEXE)
	 * @return Player
	 * @throws SQLException
	 */
	private static Player getPlayerInstance(ResultSet rs) throws SQLException {
		final long id = rs.getLong("ID");
		final String nom = rs.getString("NOM");
		final String prenom = rs.getString("PRENOM");
		final char sexe = rs.getString("SEXE").charAt(0);
		return new Player(id, nom, prenom, Sex.valueOf(sexe));
	}


}
