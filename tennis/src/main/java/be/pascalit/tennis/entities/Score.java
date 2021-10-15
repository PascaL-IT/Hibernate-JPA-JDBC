package be.pascalit.tennis.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table (name = "SCORE_VAINQUEUR")
public class Score {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	// @Column(name="ID_MATCH")
	@Transient // see Match below
	private long matchId; // reference to a match
	
	@Column(name="SET_1", nullable = true)
	private Byte set1;
	
	@Column(name="SET_2", nullable = true)
	private Byte set2;
	
	@Column(name="SET_3", nullable = true)
	private Byte set3;
	
	@Column(name="SET_4", nullable = true)
	private Byte set4;
	
	@Column(name="SET_5", nullable = true)
	private Byte set5;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "ID_MATCH")
	private Match match; // match's score (bidirectional relationship - Score is child of Match parent)
	
	
	/**
	 * Score constructor (SCORE_VAINQUEUR) when auto-generated id is unknown
	 * @param matchId
	 * @param set1
	 * @param set2
	 * @param set3
	 * @param set4
	 * @param set5
	 */
	public Score(long matchId, Byte set1, Byte set2, Byte set3, Byte set4, Byte set5) {
		this.matchId = matchId;
		this.set1 = set1;
		this.set2 = set2;
		this.set3 = set3;
		this.set4 = set4;
		this.set5 = set5;
	}
	
	/**
	 * Score constructor (SCORE_VAINQUEUR) when auto-generated id is known
	 */
	public Score(long id, long matchId, byte set1, byte set2, byte set3, byte set4, byte set5) {
		this.id = id;
		this.matchId = matchId;
		this.set1 = set1;
		this.set2 = set2;
		this.set3 = set3;
		this.set4 = set4;
		this.set5 = set5;
	}

	/**
	 * Score default constructor (SCORE_VAINQUEUR) required for Hibernate
	 */
	public Score() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getMatchId() {
		return matchId;
	}

	public void setMatchId(long matchId) {
		this.matchId = matchId;
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
	
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
	
	/**
	 * Reset the five sets to zero 
	 */
	public void reset() {
		this.set1 = 0;
		this.set2 = 0;
		this.set3 = 0;
		this.set4 = 0;
		this.set5 = 0;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (matchId ^ (matchId >>> 32));
		result = prime * result + set1;
		result = prime * result + set2;
		result = prime * result + set3;
		result = prime * result + set4;
		result = prime * result + set5;
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
		Score other = (Score) obj;
		if (id != other.id)
			return false;
		if (matchId != other.matchId)
			return false;
		if (set1 != other.set1)
			return false;
		if (set2 != other.set2)
			return false;
		if (set3 != other.set3)
			return false;
		if (set4 != other.set4)
			return false;
		if (set5 != other.set5)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Score [id=" + id + ", matchId=" + matchId + ", set1=" + set1 + ", set2=" + set2 + ", set3=" + set3
				+ ", set4=" + set4 + ", set5=" + set5 + "]";
	}
	
	/**
	 * toInfoDetails
	 * @return String
	 */
	public String toInfoDetails() {
		StringBuilder sb = new StringBuilder();
		sb.append(" matchId=").append(matchId);
		sb.append(", set1=").append(set1);
		sb.append(", set2=").append(set2);
		if (set3 != null)
			sb.append(", set3=").append(set3);
		if (set4 != null)
			sb.append(", set4=").append(set4);
		if (set5 != null)
			sb.append(", set5=").append(set5);
		
		return sb.toString();
	}

}
