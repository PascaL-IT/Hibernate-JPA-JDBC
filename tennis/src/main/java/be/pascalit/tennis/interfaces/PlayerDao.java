package be.pascalit.tennis.interfaces;

import java.util.List;

import be.pascalit.tennis.entities.Player;
import be.pascalit.tennis.entities.Player.Sex;

public interface PlayerDao {

	/**
	 * Create a new Player (his technical Id is auto-generated)
	 * @param p
	 */
	void create(Player p);
	
	/**
	 * Delete a Player with his technical Id
	 * @param id
	 */
	void deleteById(long id);
	
	/**
	 * Delete a Player with name and forename
	 * @param name
	 * @param forename
	 */
	void delete(String name, String forename);
	
	/**
	 * Retrieve a Player with his technical Id 
	 * @param id
	 * @return Player
	 */
	Player getById(long id);
	
	/**
	 * Retrieve a Player with name and forename
	 * @param name
	 * @param forename
	 * @return
	 */
	Player get(String name, String forename);
	
	/**
	 * Retrieve full list of Players
	 * @return List<Player>
	 */
	List<Player> getFullList();
	
	/**
	 * Retrieve list of Players for type of Sex
	 * @return List<Player>
	 */
	List<Player> getList(Sex sex);
	
	/**
	 * Retrieve list of Players in the range [minId, maxId]
	 * @param minID
	 * @param maxId
	 * @return List<Player> 
	 */
	List<Player> getList(long minId, long maxId);
	
	
}
