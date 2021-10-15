package be.pascalit.tennis.services;

import java.util.List;

import be.pascalit.tennis.dto.TournamentDto;
import be.pascalit.tennis.entities.Tournament;
import be.pascalit.tennis.interfaces.TournamentDao;
import be.pascalit.tennis.jpa.repositories.TournamentRepositoryJpaImpl;

public class TournamentService {

	private TournamentDao tdi;

	public TournamentService() {
		// this.tdi = new TournamentDaoImpl(); // JDBC
		// this.tdi = new TournamentRepositoryImpl(); // HIBERNATE
		this.tdi = new TournamentRepositoryJpaImpl(); // JPA
	}

	public void createTournament(TournamentDto dto) {
		Tournament tournament = new Tournament();
		tournament.setId(dto.getId());
		tournament.setCode(dto.getCode());
		tournament.setName(dto.getName());
		tdi.create(tournament);
	}
	
	public void deleteTournament(long id) {
		tdi.deleteById(id);
	}
		
	/**
	 * Get print out of full list of tournaments
	 */
	public void getFullListTournaments() {
		List<Tournament> list = tdi.getFullList();
		list.stream().forEach(System.out::println);
	}

	/**
	 * Get Tournament data by his Id
	 * @param tournamentId
	 * @return TournamentDto
	 */
	public TournamentDto getTournament(final long tournamentId) {
		Tournament t = tdi.getById(tournamentId);
		if (t != null) {
			return new TournamentDto(t.getId(), t.getName(), t.getCode());
		} 
		return null;
	}
}
