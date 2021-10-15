package be.pascalit.tennis.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "MATCH_TENNIS")
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@JoinColumn(name = "ID_VAINQUEUR")
	@ManyToOne(fetch = FetchType.LAZY)
	private Player winner;

	@JoinColumn(name = "ID_FINALISTE")
	@ManyToOne(fetch = FetchType.LAZY)
	private Player finalist;

	@JoinColumn(name = "ID_EPREUVE")
	@OneToOne(fetch = FetchType.LAZY)
	private Event event; // match's event (1 to 1 , bidirectional relationship ???)

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "match", orphanRemoval = true) // mapped by 'match' from Score (child), orphanRemoval = true
	private Score score; // match's score (bidirectional relationship - Match is parent)

	
	/**
	 * Match constructor (MATCH_TENNIS) if auto-generated id is unknown
	 * 
	 * @param event
	 * @param winner
	 * @param finalist
	 * @param score
	 */
	public Match(Event event, Player winner, Player finalist, Score score) {
		this.id = 0L; // auto-generated
		this.event = event;
		this.winner = winner;
		this.finalist = finalist;
		this.score = score;
	}

	/**
	 * Match constructor (MATCH_TENNIS) when auto-generated id is known
	 * 
	 * @param id
	 * @param eventId
	 * @param winner
	 * @param finalist
	 * @param score
	 */
	public Match(long id, Event event, Player winner, Player finalist, Score score) {
		this.id = id;
		this.event = event;
		this.winner = winner;
		this.finalist = finalist;
		this.score = score;
	}

	/**
	 * Match constructor (default required by Hibernate)
	 */
	public Match() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public Player getFinalist() {
		return finalist;
	}

	public void setFinalist(Player finalist) {
		this.finalist = finalist;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((finalist == null) ? 0 : finalist.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result + ((winner == null) ? 0 : winner.hashCode());
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
		Match other = (Match) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (finalist == null) {
			if (other.finalist != null)
				return false;
		} else if (!finalist.equals(other.finalist))
			return false;
		if (id != other.id)
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (winner == null) {
			if (other.winner != null)
				return false;
		} else if (!winner.equals(other.winner))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Match [id=" + id + ", winner=" + winner + ", finalist=" + finalist + ", event=" + event + ", score="
				+ score + "] (MATCH_TENNIS)";
	}

}
