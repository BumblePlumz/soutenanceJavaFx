package fr.cda.immobilier.utils.tools;

import fr.cda.immobilier.exceptions.JPAException;
import fr.cda.immobilier.utils.Config;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.service.spi.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Gestion des managers JPA
 */
public class JPATools {
    protected String unit = "home";
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    protected EntityTransaction entityTransaction;
    protected Boolean isOk = false;
    protected Map<String, String> dataBaseProperties = new HashMap<>();

    public JPATools() {
        String url = Config.getProperty("database.driver")+":"+Config.getProperty("database.sgbdr")+"://"+Config.getProperty("database.server")+":"+Config.getProperty("database.port")+"/"+Config.getProperty("database.dbname");
        dataBaseProperties.put("javax.persistence.jdbc.driver", Config.getProperty("database.driverUrl"));
        dataBaseProperties.put("javax.persistence.jdbc.url", url);
        dataBaseProperties.put("javax.persistence.jdbc.user", Config.getProperty("database.user"));
        dataBaseProperties.put("javax.persistence.jdbc.password", Config.getProperty("database.password"));
        dataBaseProperties.put("hibernate.hbm2ddl.auto", Config.getProperty("database.hbm2ddl"));
        dataBaseProperties.put("hibernate.show_sql", Config.getProperty("database.show_sql"));
        try {
            this.entityManagerFactory = Persistence.createEntityManagerFactory(unit, dataBaseProperties);
            this.entityManager = entityManagerFactory.createEntityManager();
        }catch (JPAException e){
            LoggerTools.logFatal("JPAException e : La connexion à la base de donnée à échouée", e.getCause());
        }catch (ServiceException e){
            LoggerTools.logFatal("ServiceException : La connexion à la base de donnée à échouée", e.getCause());
        }
    }

    /**
     * Ouvrir une transaction
     */
    public void start(){
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
        }catch (JPAException e){
            LoggerTools.logError("Une erreur est survenue dans la transaction", e.getCause());
        }
    }

    /**
     * Fermer une transaction
     */
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
