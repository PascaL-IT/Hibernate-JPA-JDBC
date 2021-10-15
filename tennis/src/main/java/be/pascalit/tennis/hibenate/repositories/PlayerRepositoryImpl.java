package be.pascalit.tennis.hibenate.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import be.pascalit.tennis.entities.Player;
import be.pascalit.tennis.entities.Player.Sex;
import be.pascalit.tennis.interfaces.PlayerDao;
import be.pascalit.util.HibernateUtil;

/**
 * PlayerRepositoryImpl (implementation with Hibernate)<br>
 * Remark. Session and Transaction are handled in the Service layer
 * @author Pascal
 *
 */
public class PlayerRepositoryImpl implements PlayerDao {

	@Override
	public Player getById(long playerId) {
		Session session = HibernateUtil.getCurrentSession();
		Player player = session.get(Player.class, playerId);
		System.out.println("getById: " + player);
		return player;
	}

	@Override
	public void create(Player player) {
		Session session = HibernateUtil.getCurrentSession();
		session.persist(player);
		session.flush();
		System.out.println("create: " + player);
	}

	@Override
	public void deleteById(long playerId) {
		Transaction tx = null;
		try(Session session = HibernateUtil.getSession()) { // request a new session (not a current)
			tx = session.beginTransaction();
			Player player = session.get(Player.class, playerId); // retrieve the player with his id
			session.delete(player); // delete it
			tx.commit();
			System.out.println("delete: " + player);
		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();
		} 
	}

	@Override
	public void delete(String name, String forename) {
		// TODO Auto-generated method stub
	}

	@Override
	public Player get(String name, String forename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Player> getFullList() {
		Session session = HibernateUtil.getCurrentSession();
		Query<Player> query = session.createQuery("select p from Player p", Player.class);
		List<Player> players = query.getResultList();
		System.out.println("PlayerRepositoryImpl.getFullList()... done (players size=" + players.size() +")");
		return players;
	}

	@Override
	public List<Player> getList(Sex sex) {
		Session session = HibernateUtil.getCurrentSession();
		// Query<Player> query = session.createQuery("select p from Player p where p.sex = ?0 ", Player.class);
		Query<Player> query = session.createNamedQuery("getBySex", Player.class); // with a named query defined in Player
		
		query.setParameter(0, sex);
		List<Player> players = query.getResultList();
		System.out.println("PlayerRepositoryImpl.getList() for "+ sex +"... done (players size=" + players.size() +")");
		return players;
	}

	@Override
	public List<Player> getList(long minId, long maxId) {
		Session session = HibernateUtil.getCurrentSession();
		Query<Player> query = session.createQuery("select p from Player p", Player.class);
		List<Player> players = query.getResultList();
		System.out.println("PlayerRepositoryImpl.getFullList()... done (players size=" + players.size() +")");
		return players;
	}
	

}
