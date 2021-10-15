package be.pascalit;

import java.util.List;

import be.pascalit.tennis.dao.PlayerDaoImpl;
import be.pascalit.tennis.dao.TournamentDaoImpl;
import be.pascalit.tennis.entities.Player;
import be.pascalit.tennis.entities.Player.Sex;
import be.pascalit.tennis.entities.Tournament;

public class TestDbDao {

	public static void main(String... args) {
		testPlayDao();
 		testTournamentDao();
	}

	
	private static void testTournamentDao() {

		TournamentDaoImpl tdi = new TournamentDaoImpl();
		Tournament t = new Tournament("T1", "C1");
		tdi.create(t);
		tdi.deleteById(t.getId());
		// tdi.deleteById(17);
		
		List<Tournament> list = tdi.getFullList();
		list.stream().forEach(System.out::println);
		long countTournaments = list.stream().count();
		System.out.println("Nbr. of tournaments: " + countTournaments);
		
		

	}

	
	// @SuppressWarnings("unused")
	private static void testPlayDao() {
		PlayerDaoImpl pdi = new PlayerDaoImpl();

		Player yn = new Player("Noa", "Yannick", Sex.F);
		pdi.create(yn);

		yn = pdi.get("Noa", "Yannick");
		pdi.updateName(yn.getId(), "Noah");

		yn = pdi.get("Noah", "Yannick");

		// pdi.getListPlayers();
		// pdi.getListPlayers(Sex.Female);
		// pdi.getListPlayers(Sex.Male);

		// pdi.getPlayer(1);
		// pdi.getPlayer("Limage", "Pascal");

		pdi.deleteById(79);
		pdi.delete("Noah", "Yannick");

		List<Player> players = pdi.getList(44L, 100L);
		players.stream().forEach(System.out::println);
	}
}
