package br.com.bancodeideias.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAConnection {

    private static final EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("BancodeideiasPU");

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

}
