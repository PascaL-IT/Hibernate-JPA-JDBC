package tennisui.controller;

import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import be.pascalit.tennis.entities.Player;
import be.pascalit.tennis.entities.Player.Sex;
import be.pascalit.tennis.services.PlayerService;


public class PlayerController {

	private PlayerService ps;

	private ScannerInputStream sis;

	public PlayerController() {
		this.ps = new PlayerService();
		this.sis = new ScannerInputStream(System.in);
	}

	/**
	 * Request the player id in input. Display the player info to output.
	 */
	public void requestExistingPlayer() {
		System.out.println("Retrieve an existing player : ");
		try (Scanner scan = new Scanner(this.sis)) {

			long playerId = 0L;
			do {
				System.out.println("\nWhat's the player id? ");
				playerId = scan.nextLong();
			} while (playerId == 0);

			Player player = ps.getPlayer(playerId);
			if (player != null) {
				System.out
						.println("Player with Id=" + playerId + " is " + player.getForename() + " " + player.getName());
			} else {
				System.out.println("Player with Id=" + playerId + " is not found !");
			}

		} // auto scan.close();

	}

	/**
	 * Request the player info in input (name, forename, sex). Create a new Player
	 * in tennis database.
	 */
	public void createNewPlayer() {
		System.out.println("Create a new player : ");
		Player player = null;
		String playerName = "";
		String playerForename = "";
		String playerSex = "";

		do {
			try (Scanner scan = new Scanner(this.sis)) {
				System.out.println("\nWhat's the player name?");
				playerName = scan.nextLine();
				System.out.println("What's the player forename?");
				playerForename = scan.nextLine();
				System.out.println("What's the player sex? [H|F]");
				playerSex = scan.nextLine();
			}
		} while (StringUtils.isBlank(playerName) || StringUtils.isBlank(playerForename)
				|| StringUtils.isBlank(playerSex));
		player = new Player(playerName, playerForename, Sex.valueOf(playerSex.charAt(0)));
		ps.createPlayer(player);
	}

	/**
	 * Request the player info in input (name, forename, sex). Delete the Player
	 * from tennis database.
	 */
	public void deleteExistingPlayer() {
		System.out.println("Delete an existing player : ");
		try (Scanner scan = new Scanner(this.sis)) {
			long playerId = 0L;
			do {
				System.out.println("What's the player id? ");
				playerId = scan.nextLong();
				scan.nextLine();
			} while (playerId == 0);

			ps.deletePlayer(playerId);
		}
	}

	/**
	 * Request the player info in input (id, name, forename). Update name and/or
	 * forename if not blank on an existing Player in tennis database.
	 */
	public void renamePlayer() {
		System.out.println("Rename an existing player : ");
		try (Scanner scan = new Scanner(this.sis)) {
			System.out.println("\nWhat's the player id?");
			Long playerId = scan.nextLong();
			scan.nextLine(); // TIP !!!
			System.out.println("What's the player surname?");
			String playerName = scan.nextLine();
			System.out.println("What's the player forname?");
			String playerForename = scan.nextLine();
			ps.updatePlayerName(playerId, playerName, playerForename);
		}
	}

	/**
	 * Request the player info in input (id, sex). Update sex on an existing Player
	 * in tennis database.
	 */
	public void changePlayerSex() {
		System.out.println("Change sex of an existing player : ");
		try (Scanner scan = new Scanner(this.sis)) {
			System.out.println("\nWhat's the player id?");
			Long playerId = scan.nextLong();
			scan.nextLine(); // TIP when not a String !!!
			System.out.println("What's the player sex? [H|F]");
			char playerSex = scan.nextLine().charAt(0);
			ps.updatePlayerSex(playerId, Sex.valueOf(playerSex));
		}
	}
	
	
	@FunctionalInterface
	/**
	 * ConsumerWithIndex
	 * Ref. https://www.baeldung.com/java-foreach-counter , 
	 *      https://stackoverflow.com/questions/477550/is-there-a-way-to-access-an-iteration-counter-in-javas-for-each-loop
	 * @param <T>
	 */
	public interface ConsumerWithIndex<T> {
		void accept(T t, int index, final int size);
	}

	/**
	 * forEachWithIndex
	 * @param <T>
	 * @param collection
	 * @param consumer
	 */
	public static <T> void forEachWithIndex(Collection<T> collection, ConsumerWithIndex<T> consumer) {
		int index = 0;
		for (T object : collection) {
			consumer.accept(object, index++, collection.size());
		}
	}
	
	/**
	 * withCounter
	 * @param <T>
	 * @param consumer
	 * @return consumer
	 */
	public static <T> Consumer<T> withCounter(BiConsumer<Integer, T> consumer) {
	    AtomicInteger counter = new AtomicInteger(0);
	    return item -> consumer.accept(counter.getAndIncrement(), item);
	}
	
	/**
	 * Display the full list of Players found in tennis database.
	 */
	public void displayListOfAllPlayers() {
		System.out.println("Full list of Players found in tennis database : ");
		// ps.getFullListPlayers().stream().forEach(System.out::println); // stream.forEach(s -> System.out.println(s));
		// forEachWithIndex( ps.getFullListPlayers() , (player, i, n) -> { System.out.printf("[%d/%d] %s\n", ++i, n, player); } ); // option n°1 : forEachWithIndex and ConsumerWithIndex
		ps.getFullListPlayers().stream().forEach(withCounter( (i, player) -> { System.out.printf("[%d] %s\n", ++i, player); } )); // option n°2 : withCounter
	}
	
	/**
	 * Display the list of Players found in tennis database per Sex.
	 */
	public void displayListOfPlayersPerSex() {
		System.out.println("List of Players found in tennis database (H|F): ");
		
		try (Scanner scan = new Scanner(this.sis)) {
			System.out.println("What's the player sex? [H|F]");
			char playerSex = scan.nextLine().charAt(0);
			ps.getListPlayers(playerSex).stream().forEach(withCounter( (i, player) -> { System.out.printf("[%d] %s\n", ++i, player); } )); // option n°2 : withCounter	
		}
		
	}
	
}
