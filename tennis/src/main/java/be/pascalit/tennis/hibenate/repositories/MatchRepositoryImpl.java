package be.pascalit.tennis.hibenate.repositories;

import java.util.List;

import org.hibernate.Session;

import be.pascalit.tennis.entities.Match;
import be.pascalit.tennis.interfaces.MatchDao;
import be.pascalit.util.HibernateUtil;


public class MatchRepositoryImpl implements MatchDao {

	@Override
	public Match getById(long matchId) {
		Session session = HibernateUtil.getCurrentSession();
		Match match = session.get(Match.class, matchId);
		System.out.println("MatchRepositoryImpl.getById... done");
		return match;
	}

	@Override
	public void create(Match m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createMatchScore(Match match) {
		Session session = HibernateUtil.getCurrentSession();
		session.persist(match);
		session.persist(match.getScore()); // alternative CASCADEType.PERSIST 
		System.out.println("MatchRepositoryImpl.createMatchScore... done");
	}

	
	@Override
	public void update(Match m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMatchScoreById(final long matchId) {
		Match match = new Match(matchId, null, null, null, null);
		Session session = HibernateUtil.getCurrentSession();
		session.delete(match);
		System.out.println("MatchRepositoryImpl.deleteMatchScoreById... done");
	}

	@Override
	public List<Match> getFullList() {
		// TODO Auto-generated method stub
		return null;
	}

}
