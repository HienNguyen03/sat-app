package com.se.sat.app.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.se.sat.app.entity.Student;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;
	private static final ServiceRegistry serviceRegistry;

	static {
		Configuration conf = new Configuration();
		conf.addAnnotatedClass(Student.class);
		// conf.configure("hibernate.cfg.xml");
		// conf.configure();

		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
		try {
			sessionFactory = conf.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			System.err.println("Initial SessionFactory creation failed." + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}