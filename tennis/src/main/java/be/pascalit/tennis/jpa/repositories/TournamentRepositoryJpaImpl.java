package be.pascalit.tennis.jpa.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import be.pascalit.tennis.entities.Tournament;
import be.pascalit.tennis.interfaces.TournamentDao;
import be.pascalit.util.EntityManagerHolder;

/**
 * TournamentRepositoryImpl with JPA
 * @author PascaL
 */
public class TournamentRepositoryJpaImpl implements TournamentDao {
	
	public TournamentRepositoryJpaImpl() {
		System.out.println("(JPA) TournamentRepositoryJpaImpl instance created...");
	}

	public Tournament getById(long tournamentId) {
		EntityTransaction tx = null;
		EntityManager em = EntityManagerHolder.getCurrentEntityManager();
		Tournament tournament = null;
		
		try {
			tx = em.getTransaction();
			tx.begin();
			tournament = em.find(Tournament.class, tournamentId);
			tx.commit();
			System.out.println("(JPA - find) getById: " + tournament);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			if(tx != null)
				tx.rollback();

		} finally {
			if (em != null)
				em.close();
		}
		
		return tournament;
	}

	@Override
	public void create(Tournament tournament) {
		EntityTransaction tx = null;
		EntityManager em = EntityManagerHolder.getCurrentEntityManager();
		try {
			tx = em.getTransaction();
			tx.begin();
			em.persist(tournament);
			em.flush();
			tx.commit();
			System.out.println("(JPA - persist) create: " + tournament);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			if(tx != null)
				tx.rollback();

		} finally {
			if (em != null)
				em.close();
		}
		
	}

	@Override
	public void update(Tournament t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(long tournamentId) {
		EntityTransaction tx = null;
		EntityManager em = EntityManagerHolder.getCurrentEntityManager();
		
		try {
			tx = em.getTransaction();
			tx.begin();
			Tournament tournament = em.find(Tournament.class, tournamentId);
			if (tournament == null) {
				System.out.println("Tournament with ID=" + tournamentId + " is not found in DB.");
				return;
			} 
			System.out.println("Tournament with ID=" + tournamentId + " is going to be deleted << " + tournament);
			em.remove(tournament);
			tx.commit();
			System.out.println("(JPA - remove) deleteById: " + tournament);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			if(tx != null)
				tx.rollback();

		} finally {
			if (em != null)
				em.close();
		}
	}

	@Override
	public List<Tournament> getFullList() {
		EntityTransaction tx = null;
		EntityManager em = EntityManagerHolder.getCurrentEntityManager();
		List<Tournament> list = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			TypedQuery<Tournament> query = em.createQuery("from Tournament", Tournament.class);
			list = query.getResultList();
			tx.commit();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			if(tx != null)
				tx.rollback();

		} finally {
			if (em != null)
				em.close();
		}

		return list; 
	}


}

