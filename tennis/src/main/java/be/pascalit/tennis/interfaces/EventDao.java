package be.pascalit.tennis.interfaces;

import java.util.List;

import be.pascalit.tennis.entities.Event;

public interface EventDao {

	/**
	 * Retrieve an Event with his technical Id
	 * 
	 * @param id
	 * @return Event
	 */
	Event getById(long id);

	/**
	 * Retrieve full list of Events
	 * 
	 * @return List<Event>
	 */
	List<Event> getFullList();
	
	/**
	 * Retrieve list of Events on Tournament
	 * @param tournamentCode
	 * @return List<Event>
	 */
	List<Event> getList(String tournamentCode); 
}
