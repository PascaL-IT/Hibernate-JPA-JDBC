package be.pascalit.tennis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import be.pascalit.tennis.DataSourceProvider;
import be.pascalit.tennis.entities.Tournament;
import be.pascalit.tennis.interfaces.TournamentDao;

//Implement CRUD methods on Tennis for Tournament (TOURNOI table)
public class TournamentDaoImpl implements TournamentDao {

	@Override
	/**
	 * Add an additional tournament in TOURNOI table<br>
	 * Remark. this function updates the ID of the input tournament object.
	 */
	public void create(Tournament tournament) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("insert into TOURNOI (NOM, CODE) values (?, ?)",
					Statement.RETURN_GENERATED_KEYS); // option 1
			ps.setString(1, tournament.getName());
			ps.setString(2, tournament.getCode());

			int res = ps.executeUpdate();

			ResultSet rsKeys = ps.getGeneratedKeys();
			if (rsKeys.next() && res == 1) {
				tournament.setId(rsKeys.getInt(1)); // set generated Id
			}

			System.out.println("Add " + res + " tournament -> " + tournament);

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
	public void update(Tournament tournament) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("update TOURNOI set NOM = ? , CODE = ? where ID = ?");
			ps.setString(1, tournament.getName());
			ps.setString(2, tournament.getCode());
			ps.setLong(3, tournament.getId());

			int res = ps.executeUpdate();
			System.out.println("Update " + res + " tournament -> " + tournament);

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
	public void deleteById(long id) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("delete from TOURNOI where ID = ?");
			ps.setLong(1, id);

			int res = ps.executeUpdate();
			System.out.println("Delete " + res + " tournament with technical ID=" + id);

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
	public Tournament getById(long id) {
		Connection conn = null;
		PreparedStatement ps = null;
		Tournament tournament = null;

		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("select ID, NOM, CODE from TOURNOI where ID = ?");
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				tournament = Tournament.getInstance(rs);
				System.out.println("Get tournament with ID=" + id + " -> " + tournament);
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

		return tournament;
	}

	@Override
	public List<Tournament> getFullList() {
		List<Tournament> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DataSourceProvider.getConnection();
			ps = conn.prepareStatement("select * from TOURNOI order by id");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(Tournament.getInstance(rs));
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

}
