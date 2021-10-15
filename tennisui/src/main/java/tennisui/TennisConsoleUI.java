package tennisui;

import tennisui.controller.EventController;
import tennisui.controller.MatchController;
import tennisui.controller.PlayerController;
import tennisui.controller.ScoreController;
import tennisui.controller.TournamentController;

public class TennisConsoleUI {

	// MAIN
	public static void main(String[] args) {

		PlayerController pc = new PlayerController();
		// pc.renamePlayer();
		// pc.createNewPlayer();
		// pc.changePlayerSex();
		// pc.requestExistingPlayer();
		// pc.deleteExistingPlayer();
		// pc.displayListOfAllPlayers();
		// pc.displayListOfPlayersPerSex();
				
		TournamentController tc = new TournamentController();
//		tc.createNewTournament();
//		tc.deleteExistingTournament();
//		tc.requestExistingTournament();
		tc.requestListOfTournaments();
		
		EventController ec = new EventController();
		// ec.requestExistingEvent(false);
		// ec.requestExistingEvent(true);
		// ec.requestFullEvent();
		// ec.requestPartialEvent();
		// ec.requestListOfEventsPerYear();
		// ec.requestListOfEventsOnTournament();
		
		MatchController mc = new MatchController();
		// mc.invertWinnerFinalist();
		// mc.createMatchWithScore();
		// mc.suppressMatch();
		
		ScoreController sc = new ScoreController();
		// sc.suppressScore();
		// sc.requestExistingScore();
		
	}

}
