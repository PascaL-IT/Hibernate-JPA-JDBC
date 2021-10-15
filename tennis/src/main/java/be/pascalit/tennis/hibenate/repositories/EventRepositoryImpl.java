package be.pascalit.tennis.hibenate.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import be.pascalit.tennis.entities.Event;
import be.pascalit.tennis.interfaces.EventDao;
import be.pascalit.util.HibernateUtil;

public class EventRepositoryImpl implements EventDao {

	public Event getById(long eventId) {
		Session session = HibernateUtil.getCurrentSession();
		Event event = session.get(Event.class, eventId);
		// System.out.println("getById: " + event);
		System.out.println("EventRepositoryImpl.getById... done");
		return event;
	}

	@Override
	public List<Event> getFullList() {
		Session session = HibernateUtil.getCurrentSession();
		Query<Event> query = session.createQuery("from Event", Event.class);
		return query.list();
	}
	
	@Override
	public List<Event> getList(final String tournamentCode) {
		Session session = HibernateUtil.getCurrentSession();
		// Query<Event> query = session.createQuery("select e from Event e where e.tournament.code = ?0", Event.class);
		Query<Event> query = session.createQuery("select e from Event e join fetch e.tournament where e.tournament.code = ?0", Event.class);
		query.setParameter(0, tournamentCode);
		return query.list();
	}

}
