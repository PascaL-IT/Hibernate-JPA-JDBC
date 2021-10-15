package be.pascalit.tennis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import be.pascalit.tennis.DataSourceProvider;
import be.pascalit.tennis.entities.Score;
import be.pascalit.tennis.interfaces.ScoreDao;

// Implement CRUD methods on Tennis for Score (SCORE_VAINQUEUR table)
public class ScoreDaoImpl implements ScoreDao {

	@Override
	/**
	 * Add an additional Score in SCORE_VAINQUEUR table<br>
	 * Remark. this function updates the ID of the input Score object.
	 */
	public void create(Score score) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement(
					"insert into SCORE_VAINQUEUR (ID_MATCH, SET_1, SET_2, SET_3, SET_4, SET_5) values (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, score.getMatchId());
			
			ps.setByte(2, score.getSet1());
			ps.setByte(3, score.getSet2());
			ps.setByte(4, score.getSet3());
			ps.setByte(5, score.getSet4());
			ps.setByte(6, score.getSet5());

			int res = ps.executeUpdate();

			ResultSet rsKeys = ps.getGeneratedKeys();

			if (rsKeys.next() && res == 1) {
				score.setId(rsKeys.getInt(1)); // set generated Id
			}

			System.out.println("Add " + res + " score -> " + score);

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
	 * Update score data by his Id
	 */
	public void update(Score score) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement(
					"update SCORE_VAINQUEUR set ID_MATCH=?, SET_1=?, SET_2=?, SET_3=?, SET_4=?, SET_5=? where ID=?");
			ps.setLong(1, score.getMatchId());
			ps.setByte(2, score.getSet1());
			ps.setByte(3, score.getSet2());
			ps.setByte(4, score.getSet3());
			ps.setByte(5, score.getSet4());
			ps.setByte(6, score.getSet5());
			ps.setLong(7, score.getId());

			int res = ps.executeUpdate();
			System.out.println("Update " + res + " score -> " + score);

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
	 * Delete a score by his technical ID
	 */
	public void deleteById(long id) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("delete from SCORE_VAINQUEUR where ID=?");
			ps.setLong(1, id);

			int res = ps.executeUpdate();
			System.out.println("Delete " + res + " score with technical ID=" + id);

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
	public Score getById(long id) {

		Connection conn = null;
		PreparedStatement ps = null;
		Score score = null;

		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("select ID, ID_MATCH, SET_1, SET_2, SET_3, SET_4, SET_5 from SCORE_VAINQUEUR where ID=?");
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				score = getScoreInstance(rs);
				System.out.println("Get score with ID=" + id + " -> " + score);
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

		return score;
	}

	@Override
	public List<Score> getFullList() {
		List<Score> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("select * from SCORE_VAINQUEUR order by id");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(getScoreInstance(rs));
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
		return list;
	}

	
	/**
	 * getScoreInstance
	 * 
	 * @param rs of a Score (SCORE_VAINQUEUR : ID, ID_MATCH, SET_1, ... SET_5)
	 * @return Score
	 * @throws SQLException
	 */
	private static Score getScoreInstance(ResultSet rs) throws SQLException {
		final long id = rs.getLong("ID");
		final long matchId = rs.getLong("ID_MATCH");
		final byte set1 = rs.getByte("SET_1");
		final byte set2 = rs.getByte("SET_2");
		final byte set3 = rs.getByte("SET_3");
		final byte set4 = rs.getByte("SET_4");
		final byte set5 = rs.getByte("SET_5");
		return new Score(id, matchId, set1, set2, set3, set4, set5);
	}

}
