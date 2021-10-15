package be.pascalit.tennis.services;

import org.hibernate.Session;
import org.hibernate.Transaction;

import be.pascalit.tennis.dto.MatchDto;
import be.pascalit.tennis.dto.PlayerDto;
import be.pascalit.tennis.dto.ScoreFullDto;
import be.pascalit.tennis.entities.Match;
import be.pascalit.tennis.entities.Score;
import be.pascalit.tennis.hibenate.repositories.ScoreRepositoryImpl;
import be.pascalit.tennis.interfaces.ScoreDao;
import be.pascalit.util.HibernateUtil;

public class ScoreService {

	private ScoreDao sdi;
	// private MatchDao mdi;

	public ScoreService() {
		// this.sdi = new ScoreDaoImpl(); // JDBC
		this.sdi = new ScoreRepositoryImpl(); // HIBERNATE
		// this.mdi = new MatchRepositoryImpl(); // HIBERNATE (alternative when using matchId instead of Match in Score class
	}

	public ScoreFullDto getScore(final long scoreId) {
		Transaction tx = null;
		ScoreFullDto sfd = null;
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			Score score = sdi.getById(scoreId); 
			
			if (score == null) {
				return sfd;
			}

			// Match match = mdi.getById(score.getMatchId());
			// match.setScore(score);

			// Hibernate.initialize(match.getFinalist());
			// Hibernate.initialize(match.getWinner());
			// Hibernate.initialize(match.getEvent());
			// Hibernate.initialize(match.getEvent().getTournament());
			
			sfd = new ScoreFullDto(score.getId(), score.getSet1(), score.getSet2(), score.getSet3(), score.getSet4(), score.getSet5());
			
			Match match = score.getMatch();
			PlayerDto winner = new PlayerDto(match.getWinner().getId(), match.getWinner().getName(), match.getWinner().getForename(), match.getWinner().getSex());
			PlayerDto finalist = new PlayerDto(match.getFinalist().getId(), match.getFinalist().getName(), match.getFinalist().getForename(), match.getFinalist().getSex());
			MatchDto md = new MatchDto(match.getId(), winner, finalist);
			
			sfd.setMatch(md);
			md.setScore(sfd); // bidirectional !
			
			tx.commit();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null) tx.rollback();
		}
		
		return sfd;
 	}
	
	/**
	 * Delete a score
	 * @param matchId
	 */
	public void deleteScore(final long scoreId) {
		Transaction tx = null;
		
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			sdi.deleteById(scoreId);
			tx.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null)
				tx.rollback();
		}
	}
}
