package be.pascalit.tennis.services;

import org.hibernate.Session;
import org.hibernate.Transaction;

import be.pascalit.tennis.dto.EventFullDto;
import be.pascalit.tennis.dto.MatchDto;
import be.pascalit.tennis.dto.PlayerDto;
import be.pascalit.tennis.dto.ScoreFullDto;
import be.pascalit.tennis.dto.TournamentDto;
import be.pascalit.tennis.entities.Event;
import be.pascalit.tennis.entities.Match;
import be.pascalit.tennis.entities.Player;
import be.pascalit.tennis.entities.Score;
import be.pascalit.tennis.hibenate.repositories.EventRepositoryImpl;
import be.pascalit.tennis.hibenate.repositories.MatchRepositoryImpl;
import be.pascalit.tennis.hibenate.repositories.PlayerRepositoryImpl;
import be.pascalit.tennis.interfaces.EventDao;
import be.pascalit.tennis.interfaces.MatchDao;
import be.pascalit.tennis.interfaces.PlayerDao;
import be.pascalit.util.HibernateUtil;

public class MatchService {

	private MatchDao mdi;
	// private ScoreDao sdi;
	private EventDao edi;
	private PlayerDao pdi;

	public MatchService() {
		// this.mdi = new MatchDaoImpl(); // JDBC
		// this.sdi = new ScoreDaoImpl(); // JDBC
		this.mdi = new MatchRepositoryImpl(); // HIBERNATE
		// this.sdi = new ScoreRepositoryImpl(); // HIBERNATE
		this.edi = new EventRepositoryImpl(); // HIBERNATE
		this.pdi = new PlayerRepositoryImpl(); // HIBERNATE
	}

	/**
	 * getMatch with event, score and players
	 * 
	 * @param matchId
	 * @return MatchDto
	 */
	public MatchDto getMatch(final long matchId) {
		Transaction tx = null;
		MatchDto md = null;
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			Match match = mdi.getById(matchId);
			if (match == null) {
				return md;
			};
			
			PlayerDto winner = new PlayerDto(match.getId(), match.getWinner().getName(),
					match.getWinner().getForename(), match.getWinner().getSex());
			PlayerDto finalist = new PlayerDto(match.getId(), match.getFinalist().getName(),
					match.getFinalist().getForename(), match.getFinalist().getSex());

			md = new MatchDto(match.getId(), winner, finalist);

			Event event = match.getEvent();
			EventFullDto efd = new EventFullDto();
			efd.setEventType(event.getEventType());
			efd.setId(event.getId());
			efd.setYear(event.getYear());
			
			TournamentDto td = new TournamentDto(event.getTournament().getId(), event.getTournament().getCode(),
					event.getTournament().getName());
			efd.setTournament(td);
			md.setEvent(efd);
			
			Score score = match.getScore();
			ScoreFullDto sd = new ScoreFullDto(score.getId(), score.getSet1(), score.getSet2(), score.getSet3(), score.getSet4(), score.getSet5());
			md.setScore(sd);
			sd.setMatch(md); // bidirectional !
			
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null)
				tx.rollback();
		}

		return md;
	}
	
	public void recordNewMatch(MatchDto dto) {
		
		Transaction tx = null;
		Match match = null;
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			
			long eventID = dto.getEvent().getId(); 
			if (eventID == 0) return;
			Event event = edi.getById(eventID);
			if (event == null) {
				System.err.print("Event not found!");
			}
			
			long finalistID = dto.getFinalist().getId();
			if (finalistID == 0) return;
			Player finalist = pdi.getById(finalistID);
			if (finalist == null) {
				System.err.print("Player finalist not found!");
			}
			
			long winnerID = dto.getWinner().getId();
			if (winnerID == 0) return;
			Player winner = pdi.getById(winnerID);
			if (winner == null) {
				System.err.print("Player winner not found!");
			}
			
			ScoreFullDto scoreDto = dto.getScore();
			Score score = new Score(scoreDto.getId(), scoreDto.getSet1(), scoreDto.getSet2(), scoreDto.getSet3(), scoreDto.getSet4(), scoreDto.getSet5());
						
			match = new Match(event, winner, finalist, score);
			score.setMatch(match);
						
			mdi.createMatchScore(match); // transactional approach (=> ok)
			tx.commit();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null)
				tx.rollback();
		}
	}

	/**
	 * Delete a match
	 * @param matchId
	 */
	public void deleteMatch(final long matchId) {
		Transaction tx = null;
		
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			mdi.deleteMatchScoreById(matchId);
			tx.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null)
				tx.rollback();
		}
	}

	/**
	 * Invert the Winner and the Finalist on a dedicated match
	 * @param matchId
	 */
	public void invertWinnerFinalist(final long matchId) {
		Transaction tx = null;
		Match match = null;
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			match = mdi.getById(matchId);
			if (match == null) {
				System.out.println("Match with Id=" + matchId + " is not found !");
			};
			
			Player winner = match.getWinner();
			Player finalist = match.getFinalist();
			Score score = match.getScore();
			System.out.println("BEFORE: winner=" + winner + " finalist=" + finalist + " scores=" + score);

			match.setWinner(finalist);
			match.setFinalist(winner);
			score.reset();
			
			tx.commit();
			System.out.println("AFTER: winner=" + match.getWinner() + " finalist=" + match.getFinalist() + " scores=" + score);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null)
				tx.rollback();
		}
	}
}
