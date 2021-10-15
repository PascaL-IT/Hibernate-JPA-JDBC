package be.pascalit;

import be.pascalit.tennis.entities.Event;
import be.pascalit.tennis.entities.Match;
import be.pascalit.tennis.entities.Player;
import be.pascalit.tennis.entities.Score;
import be.pascalit.tennis.services.MatchService;
import be.pascalit.tennis.services.PlayerService;
import be.pascalit.tennis.services.TournamentService;

public class TestServiceLayers {
	
	public static void main(String... args) {
		
		PlayerService ps = new PlayerService();
		// ps.createPlayer(new Player("JN1", "JP1", Sex.Female));
        // ps.createPlayer(new Player("JN2", "JP2", Sex.Female));
		ps.getFullListPlayers();
		
		TournamentService ts = new TournamentService();
        // ts.createTournament(new Tournament("TN1", "T1"));
        // ts.createTournament(new Tournament("TN2", "T2"));
		ts.getFullListTournaments();
		
		MatchService ms = new MatchService();
		long eventId = 10; // event n° 10 (épreuve)
		Player winner = ps.getPlayer("Federer", "Roger"); // 6-3 6-4 7-6
		Player finalist = ps.getPlayer("Murray", "Andy");
		Score score = new Score();
		score.setSet1((byte) 3);
		score.setSet2((byte) 4);
		score.setSet3((byte) 6);
		Event event = new Event();
		event.setId(eventId);
		Match match = new Match(event, winner, finalist, score);
		
		
		//ms.recordNewMatch(match); TODO
		//ms.deleteMatch(match);
		
	}
	
}
