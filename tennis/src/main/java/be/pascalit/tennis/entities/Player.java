package be.pascalit.tennis.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;


/**
 * Player bean (represent a JOUEUR)
 * 
 * @author PascaL
 */
@NamedQuery(name = "getBySex" , query = "select p from Player p where p.sex = ?0 ")
@Entity
@Table (name = "JOUEUR")
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Column(name="NOM")
	private String name;
	
	@Column(name="PRENOM")
	private String forename;
	
	@Column(name="SEXE", updatable = true, nullable = false, length=1, columnDefinition="char(1)")
	@Enumerated(EnumType.STRING)
	private Sex sex;

	/**
	 * Sex type - F=Female, H=Male<br>
	 * F or H is the flag saved in database
	 * 
	 * @author PascaL
	 */
	public enum Sex {

		F("Female",'F') {
		},
		H("Male", 'H') {
		};

		private String name;
		
		private char value;

		public String getDbValue() {
			return String.valueOf(this.value);
		}

		public char getValue() {
			return this.value;
		}
		
		public String getName() {
			return this.name;
		}

		
		public static Sex valueOf(char sexCode) {
			switch(sexCode) { 
				case 'H' : 
					return Sex.H;
				case 'F' :
					return Sex.F;
				default :
					throw new IllegalArgumentException("Error : invalid value for Sex -> " + sexCode + " ?!");
			}
		}

		private Sex(String name, char value) {
			this.name = name;
			this.value = value;
		}

	}
	
	/**
	 * Player constructor (JOUEUR)
	 */
	public Player() {
		super();
	};

	/**
	 * Player constructor (JOUEUR)
	 * 
	 * @param name
	 * @param forename
	 * @param sex
	 */
	public Player(String name, String forename, Sex sex) {
		this(0L, name, forename, sex);
	};

	/**
	 * Player constructor (JOUEUR)
	 * 
	 * @param id       (auto-generated)
	 * @param name
	 * @param forename
	 * @param sex
	 */
	public Player(long id, String name, String forename, Sex sex) {
		this.id = id; // technical Id (auto-generated)
		this.name = name;
		this.forename = forename;
		this.sex = sex;
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

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	};

	public char getSexValue() {
		return this.sex.getValue();
	}
	
	public Sex getSex() {
		return this.sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((forename == null) ? 0 : forename.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
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
		Player other = (Player) obj;
		if (forename == null) {
			if (other.forename != null)
				return false;
		} else if (!forename.equals(other.forename))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sex != other.sex)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", forename=" + forename + ", sex=" + sex + "] (JOUEUR)";
	};

}
