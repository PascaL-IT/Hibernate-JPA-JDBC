package be.pascalit.tennis.interfaces;

import java.util.List;

import be.pascalit.tennis.entities.Tournament;

public interface TournamentDao {

	/**
	 * Create a new Tournament (his technical Id is auto-generated)
	 * @param Tournament
	 */
	void create(Tournament t);
	
	/**
	 * Update Tournament data by his technical Id
	 * @param Tournament
	 */
	void update(Tournament t);
	
	/**
	 * Delete a Tournament with his technical Id
	 * @param id
	 */
	void deleteById(long id);
	
	
	/**
	 * Retrieve a Tournament with his technical Id 
	 * @param id
	 * @return Tournament
	 */
	Tournament getById(long id);
	
	
	/**
	 * Retrieve full list of Tournament
	 * @return List<Tournament>
	 */
	List<Tournament> getFullList();
	
	
}
