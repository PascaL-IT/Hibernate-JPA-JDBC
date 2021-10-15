package be.pascalit.tennis.interfaces;

import java.util.List;

import be.pascalit.tennis.entities.Score;


public interface ScoreDao {

	/**
	 * Create a new Score (his technical Id is auto-generated)
	 * @param m
	 */
	void create(Score s);
	
	/**
	 * Update Score data by his technical Id
	 * @param s
	 */
	void update(Score s);
	
	/**
	 * Delete a Score with his technical Id
	 * @param id
	 */
	void deleteById(long id);
	
	/**
	 * Retrieve a Score with his technical Id 
	 * @param id
	 * @return Score
	 */
	Score getById(long id);
	
	/**
	 * Retrieve full list of Scores
	 * @return List<Score>
	 */
	List<Score> getFullList();
	

	
}
