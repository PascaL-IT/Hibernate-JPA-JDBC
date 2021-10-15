package be.pascalit.tennis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import be.pascalit.tennis.DataSourceProvider;
import be.pascalit.tennis.entities.Event;
import be.pascalit.tennis.entities.Match;
import be.pascalit.tennis.entities.Player;
import be.pascalit.tennis.entities.Score;
import be.pascalit.tennis.interfaces.MatchDao;

// Implement CRUD methods on Tennis for Match (MATCH_TENNIS table)
public class MatchDaoImpl implements MatchDao {

	@Override
	/**
	 * Add an additional Match in MATCH_TENNIS table<br>
	 * Remark. this function updates the ID of the input Match object.
	 */
	public void create(Match match) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement(
					"insert into MATCH_TENNIS (ID_EPREUVE, ID_VAINQUEUR, ID_FINALISTE) values (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, match.getEvent().getId());
			ps.setLong(2, match.getWinner().getId());
			ps.setLong(3, match.getFinalist().getId());

			int res = ps.executeUpdate();

			ResultSet rsKeys = ps.getGeneratedKeys();

			if (rsKeys.next() && res == 1) {
				match.setId(rsKeys.getInt(1)); // set generated Id
			}

			System.out.println("Add " + res + " match -> " + match);

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
		}

	}

	@Override
	/**
	 * Update match data by his Id
	 */
	public void update(Match match) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement(
					"update MATCH_TENNIS set ID_EPREUVE=?, ID_VAINQUEUR=?, ID_FINALISTE=? where ID=?");
			ps.setLong(1, match.getEvent().getId());
			ps.setLong(2, match.getWinner().getId());
			ps.setLong(3, match.getFinalist().getId());
			ps.setLong(4, match.getId());

			int res = ps.executeUpdate();
			System.out.println("Update " + res + " match -> " + match);

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
		}

	}

	@Override
	/**
	 * Delete a match by his technical ID
	 */
	public void deleteById(long id) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("delete from MATCH_TENNIS where ID=?");
			ps.setLong(1, id);

			int res = ps.executeUpdate();
			System.out.println("Delete " + res + " match with technical ID=" + id);

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
		}
	}

	@Override
	/**
	 * Retrieve a match by his technical ID
	 */
	public Match getById(long id) {

		Connection conn = null;
		PreparedStatement ps = null;
		Match match = null;

		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement(
					"select ID, ID_EPREUVE, ID_VAINQUEUR, ID_FINALISTE from MATCH_TENNIS where ID = ?");
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				match = getMatchInstance(rs);
				System.out.println("Get match with ID=" + id + " -> " + match);
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
		}

		return match;
	}

	@Override
	public List<Match> getFullList() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * getInstance (factory method)
	 * 
	 * @param rs of a player (JOUEUR)
	 * @return Player
	 * @throws SQLException
	 */
	private static Match getMatchInstance(ResultSet rs) throws SQLException {
		final long id = rs.getLong("ID");
		final long eventId = rs.getLong("ID_EPREUVE");
		Player winner = new Player();
		winner.setId(rs.getLong("ID_VAINQUEUR"));
		Player finalist = new Player();
		finalist.setId(rs.getLong("ID_FINALISTE"));
		Score score = new Score();
		Event event = new Event();
		event.setId(eventId);
		return new Match(id, event, winner, finalist, score);
	}

	@Override
	public void createMatchScore(Match match) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(
					"insert into MATCH_TENNIS (ID_EPREUVE, ID_VAINQUEUR, ID_FINALISTE) values (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, match.getEvent().getId());
			ps.setLong(2, match.getWinner().getId());
			ps.setLong(3, match.getFinalist().getId());

			int res = ps.executeUpdate();

			ResultSet rsKeys = ps.getGeneratedKeys();

			if (rsKeys.next() && res == 1) {
				long matchId = rsKeys.getInt(1);
				match.setId(matchId); // set generated Id
				match.getScore().setMatchId(matchId);
			}
			
			ps = conn.prepareStatement(
					"insert into SCORE_VAINQUEUR (ID_MATCH, SET_1, SET_2, SET_3, SET_4, SET_5) values (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			final Score score = match.getScore();
			ps.setLong(1, score.getMatchId());
			ps.setByte(2, score.getSet1());
			ps.setByte(3, score.getSet2());
			ps.setByte(4, score.getSet3());
			ps.setByte(5, score.getSet4());
			ps.setByte(6, score.getSet5());

			res += ps.executeUpdate();
			rsKeys = ps.getGeneratedKeys();

			if (rsKeys.next() && res == 2) {
				score.setId(rsKeys.getInt(1)); // set generated Id
				System.out.println("Add both a match and score -> " + match + " \n -> " + score);
			}
			
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void deleteMatchScoreById(long matchId) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			conn.setAutoCommit(false);

			ps = conn.prepareStatement("delete from SCORE_VAINQUEUR where ID_MATCH=?");
			ps.setLong(1, matchId);
			int res =ps.executeUpdate();
						
			ps = conn.prepareStatement("delete from MATCH_TENNIS where ID=?");
			ps.setLong(1, matchId);
			res += ps.executeUpdate();
			
			if (res == 2)
				System.out.println("Delete both match and his associated score with technical ID_MATCH=" + matchId);

			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) { 
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
