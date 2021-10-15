package be.pascalit.tennis.dto;

/**
 * ScoreFullDto - Bean Score with event and tournament data for use by UI layer
 * 
 * @author PascaL
 */
public final class ScoreFullDto {

	private long id;

	private Byte set1; // Byte instead of byte in order to accept null value

	private Byte set2;

	private Byte set3;

	private Byte set4;
	
	private Byte set5;
	
	private MatchDto match;
	
	
	/**
	 * Score constructor (SCORE_VAINQUEUR) when auto-generated id is known
	 */
	public ScoreFullDto(long id, Byte set1, Byte set2, Byte set3, Byte set4, Byte set5) {
		super();
		this.id = id;
		this.set1 = set1;
		this.set2 = set2;
		this.set3 = set3;
		this.set4 = set4;
		this.set5 = set5;
	}
	
	/**
	 * Score constructor (SCORE_VAINQUEUR) when auto-generated id is unknown
	 */
	public ScoreFullDto(Byte set1, Byte set2, Byte set3, Byte set4, Byte set5) {
		this.set1 = set1;
		this.set2 = set2;
		this.set3 = set3;
		this.set4 = set4;
		this.set5 = set5;
	}

	/**
	 * Score default constructor (SCORE_VAINQUEUR) required for Hibernate
	 */
	public ScoreFullDto() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Byte getSet1() {
		return set1;
	}

	public void setSet1(Byte set1) {
		if (set1 != null)
			this.set1 = set1;
	}

	public Byte getSet2() {
		return set2;
	}

	public void setSet2(Byte set2) {
		if (set2 != null)
			this.set2 = set2;
	}

	public Byte getSet3() {
		return set3;
	}

	public void setSet3(Byte set3) {
		if (set3 != null)
			this.set3 = set3;
	}

	public Byte getSet4() {
		return set4;
	}

	public void setSet4(Byte set4) {
		if (set4 != null)
			this.set4 = set4;
	}

	public Byte getSet5() {
		return set5;
	}

	public void setSet5(Byte set5) {
		if (set5 != null)
			this.set5 = set5;
	}

	public MatchDto getMatch() {
		return match;
	}

	public void setMatch(MatchDto match) {
		this.match = match;
	}
	
	@Override
	public String toString() {
		return "ScoreFullDto [id=" + id + ", set1=" + set1 + ", set2=" + set2 + ", set3=" + set3
				+ ", set4=" + set4 + ", set5=" + set5 + "]"; // without 'match' to avoid infinite loop in some cases !
	}

	public String toFullString() {
		return "ScoreFullDto [id=" + id + ", set1=" + set1 + ", set2=" + set2 + ", set3=" + set3
				+ ", set4=" + set4 + ", set5=" + set5 + ", match=" + match + "]";
	}



}
