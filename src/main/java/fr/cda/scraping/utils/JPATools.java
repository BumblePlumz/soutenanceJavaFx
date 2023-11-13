package fr.cda.scraping.utils;

import fr.cda.scraping.exceptions.JPAException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.service.spi.ServiceException;

import java.net.ConnectException;
import java.util.logging.Logger;

public class JPATools {
    protected String unit = "scraping";
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    protected EntityTransaction entityTransaction;
    protected Boolean isOk = false;

    public JPATools() {;
        try {
            this.entityManagerFactory = Persistence.createEntityManagerFactory(unit);
            this.entityManager = entityManagerFactory.createEntityManager();
        }catch (JPAException e){
            LoggerTools.logFatal("JPAException e : La connexion à la base de donnée à échouée", e.getCause());
        }catch (ServiceException e){
            LoggerTools.logFatal("ServiceException : La connexion à la base de donnée à échouée", e.getCause());
        }
    }

    public void start(){
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
        }catch (JPAException e){
            LoggerTools.logError("Une erreur est survenue dans la transaction", e.getCause());
        }
    }
    public void close(){
        try {
            if (isOk){
                entityTransaction.commit();
            }else{
                entityTransaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }catch (JPAException e){
            LoggerTools.logError("Une erreur est survenue dans la fermeture de la transaction", e.getCause());
        }
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    public EntityManager getEntityManager() {
        return entityManager;
    }
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public EntityTransaction getEntityTransaction() {
        return entityTransaction;
    }
    public void setEntityTransaction(EntityTransaction entityTransaction) {
        this.entityTransaction = entityTransaction;
    }
}
