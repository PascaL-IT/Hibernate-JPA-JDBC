package tennisui.controller;

import java.util.Scanner;

import be.pascalit.tennis.dto.TournamentDto;
import be.pascalit.tennis.services.TournamentService;

public class TournamentController {

	private TournamentService ts;
	
	private ScannerInputStream sis;

	public TournamentController() {
		this.ts = new TournamentService();
		this.sis = new ScannerInputStream(System.in);
	}

	/**
	 * Request the Tournament id in input. Display the Tournament info to output.
	 */
	public void requestExistingTournament() {
		System.out.println("Retrieve an existing tournament : ");
		try (Scanner scan = new Scanner(this.sis)) {

			long tournamentId = 0L;
			do {
				System.out.println("\nWhat's the tournament id? ");
				tournamentId = scan.nextLong();
			} while (tournamentId == 0);

			TournamentDto tournament = ts.getTournament(tournamentId);
			
			if (tournament != null) {
				System.out
						.println("Tournament with Id=" + tournamentId + " is " + tournament.getName() + " " + tournament.getCode());
			} else {
				System.out.println("Tournament with Id=" + tournamentId + " is not found !");
			}
		} // auto scan.close();
	}

	/**
	 * Request the Tournament info in input (name, code). Create a new Tournament in
	 * tennis database.
	 */
	public void createNewTournament() {
		System.out.println("Create a new tournament : ");
		try (Scanner scan = new Scanner(this.sis)) {
			System.out.println("\nWhat's the tournament name?");
			String tournamentName = scan.nextLine();
			System.out.println("What's the tournament code?");
			String tournamentCode = scan.nextLine();

			TournamentDto tournament = new TournamentDto(tournamentName, tournamentCode);
			ts.createTournament(tournament);
		}
	}

	/**
	 * Request the Tournament Id in input Delete the Tournament from tennis
	 * database.
	 */
	public void deleteExistingTournament() {
		System.out.println("Delete an existing tournament : ");
		try (Scanner scan = new Scanner(this.sis)) {
			long tournamentId = 0L;
			do {
				System.out.println("What's the tournament id? ");
				tournamentId = scan.nextLong();
			} while (tournamentId == 0);

			ts.deleteTournament(tournamentId);
			scan.close();
		}
	}
	
	/**
	 * Request the list of Tournaments.
	 */
	public void requestListOfTournaments() {
		System.out.println("List of tennis tournaments : ");
		ts.getFullListTournaments();
	}
}
