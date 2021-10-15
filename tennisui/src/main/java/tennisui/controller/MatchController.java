package tennisui.controller;

import java.util.Scanner;

import be.pascalit.tennis.dto.EventFullDto;
import be.pascalit.tennis.dto.MatchDto;
import be.pascalit.tennis.dto.PlayerDto;
import be.pascalit.tennis.dto.ScoreFullDto;
import be.pascalit.tennis.services.MatchService;


public class MatchController {
	
	private MatchService ms;
	
	private ScannerInputStream sis;

	public MatchController() {
		this.ms = new MatchService();
		this.sis = new ScannerInputStream(System.in);
	}

	/**
	 * Request the Match (match_tennis) id in input. Display the Match info to output.
	 */
	public void requestExistingMatch() {
		System.out.println("Retrieve an existing match : ");
		try (Scanner scan = new Scanner(this.sis)) {

			long matchId = 0L;
			do {
				System.out.println("\nWhat's the match id? ");
				matchId = scan.nextLong();
			} while (matchId == 0);

			MatchDto md = ms.getMatch(matchId);
			
			if (md != null) {
				System.out.println("MatchController.requestExistingMatch... done");
				System.out.println("MatchController.requestExistingMatch -> " + md);
				
			} else {
				System.out.println("MatchDto with Id=" + matchId + " is not found !");
			}
			
		} // auto scan.close();
	}
	
	/**
	 * Process the invertion of the Winner and the Finalist on a Match (match_tennis) by requesting the math's id.
	 */
	public void invertWinnerFinalist() {
		System.out.println("Invert winner and finalist on an existing match : ");
		try (Scanner scan = new Scanner(this.sis)) {

			long matchId = 0L;
			do {
				System.out.println("\nWhat's the match id? ");
				matchId = scan.nextLong();
			} while (matchId == 0);
			
			ms.invertWinnerFinalist(matchId);

			MatchDto md = ms.getMatch(matchId);		
			
			if (md != null) {
				System.out.println("MatchController.invertWinnerFinalist... done");
				System.out.println("MatchController.invertWinnerFinalist -> " + md);
			} 
			
		} // auto scan.close();
	}
	
	/**
	 * Create a Match (match_tennis) and his associated scores.
	 */
	public void createMatchWithScore() {
		System.out.println("Create a new match and his scores: ");
		try (Scanner scan = new Scanner(this.sis)) {

			long eventId = 0L;
			do {
				System.out.println("\nWhat's the event id? ");
				eventId = scan.nextLong();
				scan.nextLine();
			} while (eventId == 0);
			
			EventFullDto eventDto = new EventFullDto();
			eventDto.setId(eventId);
			
			
			long finalistId = 0L;
			do {
				System.out.println("\nWhat's the finalist id? ");
				finalistId = scan.nextLong();
				scan.nextLine();
			} while (finalistId == 0);
			
			PlayerDto finalistDto = new PlayerDto(finalistId);
			
			
			long winnerId = 0L;
			do {
				System.out.println("\nWhat's the winner id? ");
				winnerId = scan.nextLong();
				scan.nextLine();
			} while (winnerId == 0);
			
			PlayerDto winnerDto = new PlayerDto(winnerId);

			Byte set1 = null;
			System.out.println("\nWhat's the score of set 1? ");
			set1 = scan.nextByte();
			scan.nextLine();

			Byte set2 = null;
			System.out.println("\nWhat's the score of set 2? ");
			set2 = scan.nextByte();
			scan.nextLine();
			
			Byte set3 = null; // How to handle correctly the additional set could be improved ... 
			System.out.println("\nWhat's the score of set 3? ");
			set3 = scan.nextByte();
			scan.nextLine();
			
			Byte set4 = null;
			System.out.println("\nWhat's the score of set 4? ");
			set4 = scan.nextByte();
			scan.nextLine();
			
			Byte set5 = null;
			System.out.println("\nWhat's the score of set 5? ");
			set5 = scan.nextByte();
			scan.nextLine();
			
			ScoreFullDto scoreDto = new ScoreFullDto(set1, set2, set3, set4, set5);
			
			
			MatchDto matchDto = new MatchDto(eventDto, winnerDto, finalistDto, scoreDto);
			scoreDto.setMatch(matchDto);
			
			ms.recordNewMatch(matchDto);
			System.out.println("MatchController.createMatchWithScore... done");
			
		} // auto scan.close();
	}
	
	/**
	 * Suppress a Match
	 */
	public void suppressMatch() {
		System.out.println("Supress an existing match : ");
		try (Scanner scan = new Scanner(this.sis)) {

			long matchId = 0L;
			do {
				System.out.println("\nWhat's the match id? ");
				matchId = scan.nextLong();
			} while (matchId == 0);
			
			ms.deleteMatch(matchId);
			System.out.println("MatchController.suppressMatch... done");
			
			MatchDto md = ms.getMatch(matchId);		
			if (md != null) {
				System.err.println("MatchController.suppressMatch... error, match still exists in database (found " + md + ")");
			} else {
				System.out.println("MatchController.suppressMatch... ok, match doesn't exist in database (not found)");
			}
			
		} // auto scan.close();
	}
	
}

	
