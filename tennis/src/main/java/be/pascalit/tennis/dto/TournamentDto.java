package be.pascalit.tennis.dto;

/**
 * TournamentDto - Bean Tournament for use by UI layer
 * 
 * @author PascaL
 */
public class TournamentDto {

	private long id;
	private String name;
	private String code;

	/**
	 * TournamentDto constructor
	 * 
	 * @param name
	 * @param code
	 */
	public TournamentDto(long id, String name, String code) {
		this(name, code);
		this.id = id;
	}

	/**
	 * TournamentDto constructor
	 * 
	 * @param name
	 * @param code
	 */
	public TournamentDto(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "TournamentDto [id=" + id + ", name=" + name + ", code=" + code + "]";
	}

}
