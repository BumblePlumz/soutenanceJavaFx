package fr.cda.immobilier.model.entity;

import fr.cda.immobilier.model.repository.TypeRepository;
import fr.cda.immobilier.utils.Config;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AnnonceTest {
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
    @DisplayName("Test CRUD de la classe Annonce")
    void TEST_CRUD() {
        try {
            Address address = new Address();
            address.setCity("Lorient");
            address.setDepartment("Morbihan");
            address.setPostcode("56000");
            address.setCodeSeloger(null);
            address.setCodeOuestFrance(null);
            em.merge(address);

            TypeRepository tr = new TypeRepository();
            Type type = tr.findById((long)2);

            // Test de la création (CREATE)
            Annonce a = new Annonce();
            a.setHref("www.google.fr");
            a.setTitle("TEST");
            a.setDescription("blablablabla");
            a.setPrice(120000);
            a.setSize(75);
            a.setRoom("3");
            a.setBedroom("1");
            a.setImgUrl("liensVersMonImage");
            a.setAddress(address);
            a.setType(type);
            em.persist(a);
            Long AnnonceId = a.getId();

            // Test de la lecture (READ)
            Annonce isAn = em.find(Annonce.class, AnnonceId);
            assertNotNull(isAn);
            assertEquals("www.google.fr", isAn.getHref());
            assertEquals("TEST", isAn.getTitle());
            assertEquals("blablablabla", isAn.getDescription());
            assertEquals(120000, isAn.getPrice());
            assertEquals(75, isAn.getSize());
            assertEquals("3", isAn.getRoom());
            assertEquals("1", isAn.getBedroom());
            assertEquals("liensVersMonImage", isAn.getImgUrl());
            assertEquals(address.getId(), isAn.getAddress().getId());
            assertEquals(type.getId(), isAn.getType().getId());
            assertNotNull(isAn.getDateUpdate());
            assertNotNull(isAn.getDateCreation());

            // Test de la mise à jour (UPDATE)
            isAn.setHref("le nouveau href");
            isAn.setTitle("TEST UPDATED");
            isAn.setSize(20);
            isAn.setPrice(75000);
            isAn.setRoom("0");
            isAn.setBedroom("0");

            // Merge optionnel si on a déjà fait un find avant (l'objet est observé)
            // em.merge(isAnnonce);

            Annonce updatedAnnonce = em.find(Annonce.class, AnnonceId);
            assertEquals("le nouveau href", updatedAnnonce.getHref());
            assertEquals("TEST UPDATED", updatedAnnonce.getTitle());
            assertEquals(20, updatedAnnonce.getSize());
            assertEquals(75000, updatedAnnonce.getPrice());
            assertEquals("0", updatedAnnonce.getRoom());
            assertEquals("0", updatedAnnonce.getRoom());


            // Test de la suppression (DELETE)
            em.remove(updatedAnnonce);
            Annonce deletedAnnonce = em.find(Annonce.class, AnnonceId);
            assertNull(deletedAnnonce);
            isOk = true;
        }catch (Exception e){
            isOk = false;
            System.out.println("Une erreur est survenue");
        }
    }
}
