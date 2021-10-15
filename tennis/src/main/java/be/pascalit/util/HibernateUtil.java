package be.pascalit.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {

		try {
			// Create the Hibernate SessionFactory from "hibernate.cfg.xml"
			return new Configuration().configure().buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Creation of Hibernate SessionFactory failed" + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}

	/**
	 * Get Hibernate Session Factory
	 * 
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Get the current Hibernate Session
	 * 
	 * @return Session
	 */
	public static Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * Get an new Hibernate Session
	 * 
	 * @return Session
	 */
	public static Session getSession() {
		return sessionFactory.openSession();
	}
	
}
