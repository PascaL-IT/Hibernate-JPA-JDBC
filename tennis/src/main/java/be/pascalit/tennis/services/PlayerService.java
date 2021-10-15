package be.pascalit.tennis.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import be.pascalit.tennis.dto.PlayerDto;
// import be.pascalit.tennis.dao.PlayerDaoImpl;
import be.pascalit.tennis.entities.Player;
import be.pascalit.tennis.entities.Player.Sex;
import be.pascalit.tennis.hibenate.repositories.PlayerRepositoryImpl;
import be.pascalit.tennis.interfaces.PlayerDao;
import be.pascalit.util.HibernateUtil;

public class PlayerService {

	private PlayerDao pdi;

	public PlayerService() {
		// this.pdi = new PlayerDaoImpl(); // JDBC
		this.pdi = new PlayerRepositoryImpl(); // HIBERNATE
	}

	/**
	 * createPlayer
	 * 
	 * @param player
	 */
	public void createPlayer(Player player) {
		Transaction tx = null;
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			pdi.create(player);
			tx.commit();
		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();
		}
	}

	/**
	 * deletePlayer<br>
	 * Remark. here I let the Repository layer to handle the Transaction and Session stuffs
	 * 
	 * @param playerId
	 */
	public void deletePlayer(long playerId) {
		pdi.deleteById(playerId);
	}

	/**
	 * getFullListPlayers
	 * @return List<PlayerDto>
	 */
	public List<PlayerDto> getFullListPlayers() {
		List<Player> players = null;
		Transaction tx = null;
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			players = pdi.getFullList();
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null) tx.rollback();
		}
		
		List<PlayerDto> listDto = new ArrayList<PlayerDto>();
		
		Consumer<Player> toDto = player -> { // used to convert Player into PlayerDto
			PlayerDto p = new PlayerDto(player.getId(), player.getName(), player.getForename(), player.getSex());
			listDto.add(p);
		};
		
		players.stream().filter(p -> p.getId() > 0).forEach(toDto);
		return listDto;
	}
	

	/**
	 * getListPlayers
	 * @param sexe
	 * @return List<PlayerDto> 
	 */
	public List<PlayerDto> getListPlayers(final char sexe) {
		List<Player> players = null;
		Transaction tx = null;
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			players = pdi.getList(Sex.valueOf(sexe));
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null) tx.rollback();
		}
		
		List<PlayerDto> listDto = new ArrayList<PlayerDto>();
		
		Consumer<Player> toDto = player -> { // used to convert Player into PlayerDto
			PlayerDto p = new PlayerDto(player.getId(), player.getName(), player.getForename(), player.getSex());
			listDto.add(p);
		};
		
		players.stream().forEach(toDto);
		return listDto;
	}

	/**
	 * getPlayer
	 * 
	 * @param name
	 * @param forename
	 * @return
	 */
	public Player getPlayer(final String name, final String forename) {
		return pdi.get(name, forename);
	}

	/**
	 * getPlayer
	 * 
	 * @param playerId
	 * @return Player
	 */
	public Player getPlayer(final long playerId) {
		Transaction tx = null;
		Player player = null;
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			player = pdi.getById(playerId);
			tx.commit();
		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();
		}
		return player;
	}

	/**
	 * updatePlayerName - update surname and/or forename if not blank<br>
	 * 
	 * @param playerId
	 * @param playerName
	 * @param playerForename
	 */
	public void updatePlayerName(final long playerId, final String playerName, final String playerForename) {
		Transaction tx = null;
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			Player player = pdi.getById(playerId); // get first the player object, store in session
			if (StringUtils.isNotBlank(playerName))
				player.setName(playerName); // update his surname
			if (StringUtils.isNotBlank(playerForename))
				player.setForename(playerForename); // update his forename
			tx.commit(); // update persistent object in db
			System.out.println("update: " + player);
		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();
		}
	}

	/**
	 * updatePlayerSex - update surname and/or forename if not blank<br>
	 * 
	 * @param playerId
	 * @param playerSex
	 */
	public void updatePlayerSex(final long playerId, final Sex playerSex) {
		Player player = getPlayer(playerId);
		player.setSex(playerSex); // update sex

		Transaction tx = null;
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			session.merge(player);
			tx.commit(); // update persistent object in db
			System.out.println("update: " + player);
		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();
		}
	}
}
