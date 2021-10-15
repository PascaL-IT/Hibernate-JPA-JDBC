package be.pascalit.tennis.hibenate.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import be.pascalit.tennis.entities.Tournament;
import be.pascalit.tennis.interfaces.TournamentDao;
import be.pascalit.util.HibernateUtil;

/**
 * TournamentRepositoryImpl with HIBERNATE
 * @author PascaL
 */
public class TournamentRepositoryImpl implements TournamentDao {

	public Tournament getById(long tournamentId) {
		Tournament tournament = null;
		try(Session session = HibernateUtil.getSession()) {
			tournament = session.get(Tournament.class, tournamentId);
			System.out.println("getById: " + tournament);
		}
		return tournament;
	}

	@Override
	public void create(Tournament tournament) {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSession()) {
			tx = session.beginTransaction();
			session.persist(tournament);
			session.flush();
			tx.commit();
			System.out.println("create: " + tournament);
			
		} catch (Exception ex) {
			tx.rollback();
			ex.printStackTrace();
		}
		
	}

	@Override
	public void update(Tournament t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(long tournamentId) {
		Transaction tx = null;
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			Tournament tournament = session.get(Tournament.class, tournamentId);
			if (tournament == null) {
				System.out.println("Tournament with Id=" + tournamentId + " is not found in DB (no delete)");
				return;
			}
			session.delete(tournament);
			System.out.println("delete: " + tournament);
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null)
				tx.rollback();
		}
	}

	@Override
	public List<Tournament> getFullList() {
		Transaction tx = null;
		List<Tournament> list = null;
		try (Session session = HibernateUtil.getSession()) {
			tx = session.beginTransaction();
			Query<Tournament> query = session.createQuery("from Tournament", Tournament.class);
			list = query.list();
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return list; 
	}


}
