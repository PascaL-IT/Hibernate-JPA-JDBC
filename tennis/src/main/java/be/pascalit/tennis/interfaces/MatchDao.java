package be.pascalit.tennis.interfaces;

import java.util.List;

import be.pascalit.tennis.entities.Match;


public interface MatchDao {

	/**
	 * Create a new Match (his technical Id is auto-generated)
	 * @param m
	 */
	void create(Match m);
	
	/**
	 * Create a new Match along with his associated Score 
	 * @param m
	 */
	void createMatchScore(Match m);
	
	/**
	 * Update Match data by his technical Id
	 * @param m
	 */
	void update(Match m);
	
	/**
	 * Delete a Match with his technical Id
	 * @param id
	 */
	void deleteById(long id);
	
	/**
	 * Delete a Match along with his associated Score 
	 * @param matchId
	 */
	void deleteMatchScoreById(long matchId);
	
	/**
	 * Retrieve a Match with his technical Id 
	 * @param id
	 * @return Match
	 */
	Match getById(long id);
	
	/**
	 * Retrieve full list of Matches
	 * @return List<Match>
	 */
	List<Match> getFullList();
	

	
}
