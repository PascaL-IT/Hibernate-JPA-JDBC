package be.pascalit.tennis.dto;

import java.util.Set;

/**
 * EventFullDto - Bean Event with event and tournament data for use by UI layer
 * 
 * @author PascaL
 */
public class EventFullDto implements Comparable<Object> {

	long id;
	short year;
	char eventType;
	TournamentDto tournament;
	Set<PlayerDto> participants;

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

	public TournamentDto getTournament() {
		return tournament;
	}

	public void setTournament(TournamentDto tournament) {
		this.tournament = tournament;
	}
	
	public Set<PlayerDto> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<PlayerDto> participants) {
		this.participants = participants;
	}

	@Override
	public String toString() {
		return "EventFullDto [id=" + id + ", year=" + year + ", eventType=" + eventType + ", tournament=" + tournament +
				", participants=" + participants + "]";
	}

	@Override
	public int compareTo(Object o) {
		EventFullDto e = null;
		if (o instanceof EventFullDto) {
			e = (EventFullDto) o;
		} else {
			throw new IllegalArgumentException("Invalid object instance - expecting an EventFullDto class");
		}
		if (this.getYear() < e.getYear()) { // sorting by year ascending
			return -1;
		} else if (this.getYear() > e.getYear()) {
			return 1;
		} 
		return 0;
	}

}
