package fr.cda.scraping.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class HibernateTools {

    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;

    public HibernateTools(String unitName) {
        this.emf = Persistence.createEntityManagerFactory(unitName);
        this.em = emf.createEntityManager();
    }



}
