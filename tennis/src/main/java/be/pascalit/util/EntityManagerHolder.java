package be.pascalit.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Entity Manager Helper or Holder (given that EntityManger is not thread-safe ...)
 * For more details, see https://stackoverflow.com/questions/15071238/entitymanager-threadlocal-pattern-with-jpa-in-jse
 * @author PascaL
 */
public class EntityManagerHolder {
	
	private static final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();
	
	private static EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();

	private static EntityManagerFactory buildEntityManagerFactory() {
		return Persistence.createEntityManagerFactory("tennis-unit");
	}

	/**
	 * EntityManagerHolder constructor (private -> singleton)
	 */
	private EntityManagerHolder() {}

	/**
	 * @return The {@link EntityManager} linked to this thread
	 */
	public static EntityManager getCurrentEntityManager() {
		EntityManager entityManager = entityManagerThreadLocal.get();

		if (entityManager == null || ! entityManager.isOpen()) { // add a condition if EM is not open ...
			// Start the conversation by creating the EntityManager for this thread
			entityManager = entityManagerFactory.createEntityManager();
			entityManagerThreadLocal.set(entityManager);
		}
		
		return entityManager;
	}

}