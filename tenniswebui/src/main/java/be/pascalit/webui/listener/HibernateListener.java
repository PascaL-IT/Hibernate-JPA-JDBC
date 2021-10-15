package be.pascalit.webui.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.SessionFactory;

import be.pascalit.util.HibernateUtil;

@WebListener
public class HibernateListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
    	SessionFactory sessionFactory = HibernateUtil.getSessionFactory(); // Just call the static initializer of that class
    	if (sessionFactory.isOpen()) {
    		System.out.println("HibernateListener context initialized... OK");
    	} else {
    		System.err.println("HibernateListener context is NOT initialized... NOK");
    	}
    }

    public void contextDestroyed(ServletContextEvent event) {
        HibernateUtil.getSessionFactory().close(); // Free all resources
        System.out.println("HibernateListener context destroyed... BYE");
    }
}
