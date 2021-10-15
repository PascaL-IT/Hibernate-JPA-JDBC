package be.pascalit.tennis.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import be.pascalit.tennis.dto.EventFullDto;
import be.pascalit.tennis.dto.EventPartialDto;
import be.pascalit.tennis.dto.PlayerDto;
import be.pascalit.tennis.dto.TournamentDto;
import be.pascalit.tennis.entities.Event;
import be.pascalit.tennis.entities.Player;
import be.pascalit.tennis.hibenate.repositories.EventRepositoryImpl;
import be.pascalit.tennis.interfaces.EventDao;
import be.pascalit.util.HibernateUtil;

public class EventService {

	private EventDao edi;

	public EventService() {
		// this.tdi = new EventDaoImpl(); // JDBC
		this.edi = new EventRepositoryImpl(); // HIBERNATE
	}

	/**
	 * Get an event with or without tournament data (approach without DTO !) 
	 * @param eventId
	 * @param withTournament
	 * @return Event
	 */
	public Event getEvent(final long eventId, final boolean withTournament) {
		Session session = null;
		Transaction tx = null;
		Event event = null;

		try {
			session = HibernateUtil.getCurrentSession();
			tx = session.beginTransaction();
			event = edi.getById(eventId);
			System.out.println("EventService.getEvent -> event.getTournament() is " + event.getTournament().getClass().getName());
//			if(event.getTournament() instanceof HibernateProxy) {
//				System.out.println("EventService.getEvent -> t is an instance of HibernateProxy");
//			}			
			if (withTournament) {
				Hibernate.initialize(event.getTournament()); // force proxy initialization => to get missing data
				if (Hibernate.isInitialized(event.getTournament())) {
					System.out.println("EventService.getEvent -> event.getTournament() is proxy initialized");
					System.out.println("EventService.getEvent -> event.getTournament() is " + event.getTournament().getClass().getName());
				}
			}
			System.out.println("EventService.getEvent -> Event id=" + event.getId());
			// System.out.println("EventService.getEvent -> Event tournament class name=" + event.getTournament().getClass().getName());
			// System.out.println("EventService.getEvent -> Event tournament=" + event.getTournament().toString());
			tx.commit();
			
		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();

		} finally {
			if (session != null)
				session.close();
		}

		return event;
	}

	/**
	 * getEventWithTournament (and with Participants)
	 * @param eventId
	 * @return EventFullDto
	 */
	public EventFullDto getEventWithTournament(final long eventId) {
		Session session = null;
		Transaction tx = null;
		EventFullDto efd = null;

		try {
			session = HibernateUtil.getCurrentSession();
			tx = session.beginTransaction();
			Event event = edi.getById(eventId);

			efd = new EventFullDto();
			efd.setEventType(event.getEventType());
			efd.setId(event.getId());
			efd.setYear(event.getYear());

			TournamentDto td = new TournamentDto(event.getTournament().getId(), event.getTournament().getCode(), event.getTournament().getName());
			System.out.println("EventService.getEventWithTournament -> " + td);
			efd.setTournament(td);
			
			efd.setParticipants(new HashSet<>()); // initialization !
			for (Player p : event.getParticipants()) {
				PlayerDto player = new PlayerDto(p.getId(), p.getForename(), p.getName(), p.getSex());
				efd.getParticipants().add(player);
			}
			tx.commit();
			
		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();

		} finally {
			if (session != null)
				session.close();
		}

		return efd;
	}
	
	/**
	 * getEventWithoutTournament (and without Participants)
	 * @param eventI
	 * @return EventPartialDto
	 */
	public EventPartialDto getEventWithoutTournament(final long eventId) {
		Session session = null;
		Transaction tx = null;
		EventPartialDto epd = null;

		try {
			session = HibernateUtil.getCurrentSession();
			tx = session.beginTransaction();
			Event event = edi.getById(eventId);

			epd = new EventPartialDto();
			epd.setEventType(event.getEventType());
			epd.setId(event.getId());
			epd.setYear(event.getYear());
			
			tx.commit();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}

		return epd;
	}
	
	/**
	 * Get full list of events filtered on a specific year (System.out)
	 * @param year
	 */
	public void getFullListEvents(final short year) {
		Transaction tx = null;
		List<Event> events = null;
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			events = edi.getFullList();
			events.stream().filter(e -> e.getYear() == year).forEach(System.out::println);
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null)
				tx.rollback();
		}
	}
	
	/**
	 * Get list of events on a tournament
	 * @param tournamentId
	 * @return List<EventFullDto>
	 */
	public List<EventFullDto> getListEvents(final String tournamentCode) {
		Transaction tx = null;
		List<EventFullDto> listDto = new ArrayList<EventFullDto>();
		
		try (Session session = HibernateUtil.getCurrentSession()) {
			tx = session.beginTransaction();
			List<Event> events = edi.getList(tournamentCode);
			events.stream().forEach(
					event -> { // used to convert Event into EventFullDto
						EventFullDto dto = new EventFullDto();
						dto.setId(event.getId());
						dto.setEventType(event.getEventType());
						dto.setYear(event.getYear());
						TournamentDto td = new TournamentDto(event.getTournament().getId() , event.getTournament().getCode() , event.getTournament().getName());
						dto.setTournament(td);
						listDto.add(dto); }
					);
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (tx != null)
				tx.rollback();
		}
		
		return listDto;
	}
	
	
}
