package tennisui.controller;

import java.util.List;
import java.util.Scanner;

import be.pascalit.tennis.dto.EventFullDto;
import be.pascalit.tennis.dto.EventPartialDto;
import be.pascalit.tennis.entities.Event;
import be.pascalit.tennis.services.EventService;



public class EventController {

	private EventService es;
	
	private ScannerInputStream sis;

	public EventController() {
		this.es = new EventService();
		this.sis = new ScannerInputStream(System.in);
	}

	/**
	 * Request the Event (epreuve) id in input. Display the Event info to output.
	 */
	public void requestExistingEvent(final boolean withTournament) {
		System.out.println("Retrieve an existing event : ");
		try (Scanner scan = new Scanner(this.sis)) {

			long eventId = 0L;
			do {
				System.out.println("\nWhat's the event id? ");
				eventId = scan.nextLong();
			} while (eventId == 0);

			Event event = es.getEvent(eventId, withTournament);
			
			if (event != null) {
				System.out.println("EventController.requestExistingEvent... done - withTournament=" + withTournament);
				System.out.println("EventController.requestExistingEvent -> with Id=" + eventId + " is " + event.getId() + " - " + event.getYear() + " - " + event.getEventType());
				if (withTournament) {
					System.out.println("EventController.requestExistingEvent -> Event tournament=" + event.getTournament()); // condition to avoid error of type : Unable to perform requested lazy initialization [be.pascalit.tennis.entities.Tournament.name] - session is closed and settings disallow loading outside the Session
				}
			} else {
				System.out.println("Event with Id=" + eventId + " is not found !");
			}
		} // auto scan.close();
	}
	
	/**
	 * Request the full Event (epreuve) id in input. Display the full Event info to output.
	 */
	public void requestFullEvent() {
		System.out.println("Retrieve an existing event with tournament : ");
		try (Scanner scan = new Scanner(this.sis)) {

			long eventId = 0L;
			do {
				System.out.println("\nWhat's the event id? ");
				eventId = scan.nextLong();
			} while (eventId == 0);

			EventFullDto eventFD = es.getEventWithTournament(eventId);
			
			if (eventFD != null) {
				System.out.println("EventController.requestFullEvent... done");
				System.out.println("EventController.requestFullEvent -> " + eventFD);
				System.out.println("EventController.requestFullEvent -> nbr. of participants=" + eventFD.getParticipants().stream().count());
				// eventFD.getParticipants().stream().forEach(System.out::println);
				
			} else {
				System.out.println("EventFullDto with Id=" + eventId + " is not found !");
			}
		} // auto scan.close();
	}
	
	/**
	 * Request the partial Event (epreuve) id in input. Display the partial Event info to output (i.e. without Tournament).
	 */
	public void requestPartialEvent() {
		System.out.println("Retrieve an existing event without tournament: ");
		try (Scanner scan = new Scanner(this.sis)) {

			long eventId = 0L;
			do {
				System.out.println("\nWhat's the event id? ");
				eventId = scan.nextLong();
			} while (eventId == 0);

			EventPartialDto eventPD = es.getEventWithoutTournament(eventId);
			
			if (eventPD != null) {
				System.out.println("EventController.requestPartialEvent... done");
				System.out.println("EventController.requestPartialEvent -> " + eventPD);
			} else {
				System.out.println("EventPartialDto with Id=" + eventId + " is not found !");
			}
		} // auto scan.close();
	}
	
	/**
	 * Request list of full Events on a year 
	 */
	public void requestListOfEventsPerYear() {
		
		System.out.println("Retrieve list of events on a year: ");
		try (Scanner scan = new Scanner(this.sis)) {

			short year = 0;
			do {
				System.out.println("\nWhat's the year for the events you are looking for? [YYYY]");
				year = scan.nextShort();
				
			} while (year == 0);

			es.getFullListEvents(year);

		} // auto scan.close();
	}
	
	/**
	 * Request list of Events on a tournament by his code 
	 */
	public void requestListOfEventsOnTournament() {
		
		System.out.println("Retrieve list of events on a tournament: ");
		try (Scanner scan = new Scanner(this.sis)) {

			String tournamentCode = "";
			do {
				System.out.println("\nWhat's the tournament code? ");
				tournamentCode = scan.nextLine();
			} while (tournamentCode.isBlank());

			List<EventFullDto> listEvents = es.getListEvents(tournamentCode);
			
			if (listEvents.isEmpty()) {
				System.out.println("No event found with tournament code = " + tournamentCode);
			} else {
				listEvents.stream().sorted().forEach(System.out::println); // print out and sorted by year
			}

		} // auto scan.close();
	}

}
