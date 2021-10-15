package be.pascalit.tennis.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * Event bean (represent an EPREUVE)
 * 
 * @author PascaL
 */
@Entity
@Table(name = "EPREUVE")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;
	
	@Column(name = "ANNEE")
	@Type(type = "short")
	private short year;
	
	@Column(name = "TYPE_EPREUVE")
	private char eventType;
	
	@JoinColumn(name = "ID_TOURNOI")
	@ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY or EAGER
	private Tournament tournament;
	
	@ManyToMany // (fetch = FetchType.LAZY) per default on Many To Many association
	@JoinTable( name="PARTICIPANTS",
			    joinColumns={ @JoinColumn(name="ID_EPREUVE") },
	            inverseJoinColumns={ @JoinColumn(name="ID_JOUEUR")} )
	private Set<Player> participants;


	/**
	 * Event default constructor (EPREUVE) required for Hibernate
	 */
	public Event() {
		super();
	};

	/**
	 * Event constructor (EPREUVE)
	 * 
	 * @param id
	 * @param year
	 * @param eventType
	 * @param idTournament
	 */
	public Event(long id, short year, char eventType, Tournament tournament) {
		this.id = id;
		this.year = year;
		this.eventType = eventType;
		this.tournament = tournament;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public short getYear() {
		return year;
	}

	public void setYear(short year) {
		this.year = year;
	}

	public char getEventType() {
		return eventType;
	}

	public void setEventType(char eventType) {
		this.eventType = eventType;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}
	
	public Set<Player> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<Player> participants) {
		this.participants = participants;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eventType;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((participants == null) ? 0 : participants.hashCode());
		result = prime * result + ((tournament == null) ? 0 : tournament.hashCode());
		result = prime * result + year;
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
		Event other = (Event) obj;
		if (eventType != other.eventType)
			return false;
		if (id != other.id)
			return false;
		if (participants == null) {
			if (other.participants != null)
				return false;
		} else if (!participants.equals(other.participants))
			return false;
		if (tournament == null) {
			if (other.tournament != null)
				return false;
		} else if (!tournament.equals(other.tournament))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", year=" + year + ", eventType=" + eventType + ", tournament=" + tournament
				// + ", participants=" + participants + "] (EPREUVE)";
				+ "] (EPREUVE)";
	}

	

}
