package fr.cda.immobilier.model.entity;

import fr.cda.immobilier.exceptions.JPAException;
import fr.cda.immobilier.model.entity.Address;
import fr.cda.immobilier.model.entity.Role;
import fr.cda.immobilier.model.entity.User;
import fr.cda.immobilier.utils.Config;
import fr.cda.immobilier.utils.tools.LoggerTools;
import fr.cda.immobilier.utils.tools.PasswordTools;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AddressTest {
    private String unit = "home";
    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;
    private Boolean isOk = false;

    @BeforeEach
    void setUp() {
        String url = Config.getProperty("database.driver")+":"+Config.getProperty("database.sgbdr")+"://"+Config.getProperty("database.server")+":"+Config.getProperty("database.port")+"/"+Config.getProperty("database.dbname");
        Properties dataBaseProperties = new Properties();
        dataBaseProperties.put("javax.persistence.jdbc.driver", Config.getProperty("database.driverUrl"));
        dataBaseProperties.put("javax.persistence.jdbc.url", url);
        dataBaseProperties.put("javax.persistence.jdbc.user", Config.getProperty("database.user"));
        dataBaseProperties.put("javax.persistence.jdbc.password", Config.getProperty("database.password"));
        dataBaseProperties.put("hibernate.hbm2ddl.auto", Config.getProperty("database.hbm2ddl"));
        dataBaseProperties.put("hibernate.show_sql", Config.getProperty("database.show_sql"));
        emf = Persistence.createEntityManagerFactory(unit, dataBaseProperties);
        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();
    }

    @AfterEach
    void tearDown() {
        if (isOk){
            et.commit();
        }else{
            et.rollback();
        }
        em.close();
        emf.close();
    }

    @Test
    @DisplayName("Test CRUD de la classe User")
    void TEST_CRUD() {
        // Test de la création (CREATE)
        Address address = new Address();
        address.setCity("Lorient");
        address.setDepartment("Morbihan");
        address.setPostcode("56000");
        address.setCodeSeloger(null);
        address.setCodeOuestFrance(null);
        em.persist(address);

        // Test de la lecture (READ)
        Address readAddress = em.find(Address.class, address.getId());
        assertNotNull(readAddress);
        assertEquals("Lorient", readAddress.getCity());
        assertEquals("Morbihan", readAddress.getDepartment());
        assertEquals("56000", readAddress.getPostcode());
        assertNull(address.getCodeSeloger());
        assertNull(address.getCodeOuestFrance());

        // Test de la mise à jour (UPDATE)
        readAddress.setCity("Quimper");
        readAddress.setDepartment("Finistère");
        readAddress.setPostcode("29000");
        readAddress.setCodeSeloger("29232");
        readAddress.setCodeOuestFrance("29");

        // Merge optionnel si on a déjà fait un find avant (l'objet est observé)
        // em.merge(isUser);

        Address updatedAddress = em.find(Address.class, readAddress.getId());
        assertEquals("Quimper", updatedAddress.getCity());
        assertEquals("Finistère", updatedAddress.getDepartment());
        assertEquals("29000", updatedAddress.getPostcode());
        assertEquals("29232", updatedAddress.getCodeSeloger());
        assertEquals("29", updatedAddress.getCodeOuestFrance());

        // Test de la suppression (DELETE)
        em.remove(updatedAddress);
        Address deletedAddress = em.find(Address.class, updatedAddress.getId());
        assertNull(deletedAddress);
        isOk = true;
    }
}
