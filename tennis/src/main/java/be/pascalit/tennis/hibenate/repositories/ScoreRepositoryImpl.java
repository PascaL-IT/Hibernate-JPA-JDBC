package be.pascalit.tennis.hibenate.repositories;

import java.util.List;

import org.hibernate.Session;

import be.pascalit.tennis.entities.Score;
import be.pascalit.tennis.interfaces.ScoreDao;
import be.pascalit.util.HibernateUtil;


public class ScoreRepositoryImpl implements ScoreDao {

	@Override
	public Score getById(long scoreId) {
		Session session = HibernateUtil.getCurrentSession();
		Score score = session.get(Score.class, scoreId);
		System.out.println("getById: " + score);
		return score;
	}
	
	@Override
	public void create(Score s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Score s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(final long scoreId) {
		//Score score = new Score(); // not working, require a session.get
		//score.setId(scoreId); // not working, require a session.get
		Session session = HibernateUtil.getCurrentSession();
		Score score = session.get(Score.class, scoreId);
		session.delete(score);
		System.out.println("ScoreRepositoryImpl.deleteById... done");
	}
	
	@Override
	public List<Score> getFullList() {
		// TODO Auto-generated method stub
		return null;
	}

}
