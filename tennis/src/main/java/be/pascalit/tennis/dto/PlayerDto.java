package be.pascalit.tennis.dto;

import be.pascalit.tennis.entities.Player.Sex;

/**
 * PlayerDto - Bean Player for use by UI layer
 * 
 * @author PascaL
 */
public final class PlayerDto {

	private long id;

	private String name;

	private String forename;

	private Sex sex;

	/**
	 * Player constructor (JOUEUR)
	 * 
	 * @param id       (auto-generated)
	 * @param name
	 * @param forename
	 * @param sex
	 */
	public PlayerDto(long id, String name, String forename, Sex sex) {
		this.id = id; // technical Id (auto-generated)
		this.name = name;
		this.forename = forename;
		this.sex = sex;
	};
	
	/**
	 * layer constructor (JOUEUR)
	 * 
	 * @param id
	 */
	public PlayerDto(long id) {
		this.id = id; 
	};
	
	
	public long getId() {
		return id;
	}

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

	public char getSex() {
		return this.sex.getValue();
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "PlayerDto [id=" + id + ", name=" + name + ", forename=" + forename + ", sex=" + sex + "]";
	};

}
