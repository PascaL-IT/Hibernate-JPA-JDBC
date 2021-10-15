package be.pascalit.tennis.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tournament bean (represent a TOURNOI)
 * 
 * @author PascaL
 */
@Entity
@Table (name = "TOURNOI")
public class Tournament {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Column(name="NOM")
	private String name;
	
	@Column(name="CODE")
	private String code;

	
	/**
	 *  Tournament constructor (TOURNOI)<br>
	 *  Required for Hibernate
	 */
	public Tournament() {
		super();
	};
	
	/**
	 * Tournament constructor (TOURNOI)
	 * @param name
	 * @param code
	 */
	public Tournament(String name, String code) {
		this(0L, name, code);
	};

	/**
	 * Tournament constructor (TOURNOI)
	 * @param id
	 * @param name
	 * @param code
	 */
	private Tournament(long id, String name, String code) {
		this.id = id; // technical Id (auto-generated)
		this.name = name;
		setCode(code);
	};

	public long getId() {
		return id;
	}

	/**
	 * Set technical Id on player creation (it's an auto-generated key)
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		if (code.length() != 2) {
			throw new IllegalArgumentException("Code of a tournament must be on exactly 2 chars !");
		}
		this.code = code;
	};

	/**
	 * getInstance (factory method)
	 * 
	 * @param rs of a tournament (TOURNOI)
	 * @return Tournament
	 * @throws SQLException
	 */
	public static Tournament getInstance(ResultSet rs) throws SQLException {
		final String nom = rs.getString("NOM");
		final String code = rs.getString("CODE");
		final long id = rs.getLong("ID");
		return new Tournament(id, nom, code);
	}

	/**
	 * printTournament
	 * 
	 * @param rs of a tournament (TOURNOI)
	 * @throws SQLException
	 */
	public static void printTournament(ResultSet rs) throws SQLException {
		getInstance(rs).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tournament other = (Tournament) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tournament [id=" + id + ", name=" + name + ", code=" + code + "] (TOURNOI)";
	}




}
