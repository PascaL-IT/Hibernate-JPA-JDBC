package be.pascalit.tennis.dto;

/**
 * EventPartialDto - Bean Event with partial event date (no tournament) for use by UI layer
 * 
 * @author PascaL
 */
public class EventPartialDto {

	long id;
	short year;
	char eventType;

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

	@Override
	public String toString() {
		return "EventPartialDto [id=" + id + ", year=" + year + ", eventType=" + eventType + "]";
	}

}
