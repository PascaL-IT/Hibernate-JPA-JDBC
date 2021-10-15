package tennisui.controller;

import java.util.Scanner;

import be.pascalit.tennis.dto.ScoreFullDto;
import be.pascalit.tennis.services.ScoreService;


public class ScoreController {

	private ScoreService ss;

	private ScannerInputStream sis;

	public ScoreController() {
		this.sis = new ScannerInputStream(System.in);
		this.ss = new ScoreService();
	}

	/**
	 * Request the score id in input. Display the score info to output along with Tournament and Event information.
	 */
	public void requestExistingScore() {
		System.out.println("Retrieve an existing score : ");
		try (Scanner scan = new Scanner(this.sis)) {

			long scoreId = 0L;
			do {
				System.out.println("\nWhat's the score id? ");
				scoreId = scan.nextLong();
			} while (scoreId == 0);

			ScoreFullDto scoreFD = ss.getScore(scoreId);
			
			if (scoreFD != null) {
				System.out.println("ScoreController.requestExistingScore... done");
				
				if (scoreFD.getMatch() != null) {
					System.out.println("ScoreController.requestExistingMatch -> " + scoreFD.toFullString());
				} else {
					System.out.println("ScoreController.requestExistingMatch -> " + scoreFD.toString());
				}
				
			} else {
				System.out.println("Score with Id=" + scoreId + " is not found !");
			}

		} // auto scan.close();

	}
	
	
	/**
	 * Suppress a Score
	 */
	public void suppressScore() {
		System.out.println("Supress an existing score : ");
		try (Scanner scan = new Scanner(this.sis)) {

			long scoreId = 0L;
			do {
				System.out.println("\nWhat's the score id? ");
				scoreId = scan.nextLong();
			} while (scoreId == 0);
			
			ss.deleteScore(scoreId);
			System.out.println("ScoreController.suppressScore... done");
			
			ScoreFullDto sfd = ss.getScore(scoreId);		
			if (sfd != null) {
				System.err.println("ScoreController.suppressScore... error, score still exists in database (found " + sfd + ")");
			} else {
				System.out.println("ScoreController.suppressScore... ok, score doesn't exist in database (not found)");
			}
			
		} // auto scan.close();
	}

}
