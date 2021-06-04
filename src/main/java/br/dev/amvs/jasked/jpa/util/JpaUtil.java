package br.dev.amvs.jasked.jpa.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
	private static EntityManagerFactory factory;
	static {
		factory = Persistence.createEntityManagerFactory("my_persistence_unit");
	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

}
