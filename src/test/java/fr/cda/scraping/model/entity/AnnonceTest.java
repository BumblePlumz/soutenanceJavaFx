package fr.cda.scraping.model.entity;

import fr.cda.scraping.utils.PasswordTools;
import fr.cda.scraping.utils.SEARCH;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AnnonceTest {
    private String unit = "scraping";
    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;
    private Boolean isOk = false;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory(unit);
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
            // Test de la création (CREATE)
            Annonce a = new Annonce();
            a.setHref("www.google.fr");
            a.setType(SEARCH.HOUSE);
            a.setCity("Lorient");
            a.setDepartment("morbihan");
            a.setPostcode("56100");
            a.setDescription("blablablabla");
            em.persist(a);
            Long AnnonceId = a.getId();

            // Test de la lecture (READ)
            Annonce isAn = em.find(Annonce.class, AnnonceId);
            assertNotNull(isAn);
            assertEquals(SEARCH.HOUSE, isAn.getType());
            assertEquals("Lorient", isAn.getCity());
            assertNotSame("www.test.fr", isAn.getHref());
            assertNotSame("56000", isAn.getPostcode());
            assertNull(isAn.getImgUrl());
            assertNotNull(isAn.getDateCreation());

            // Test de la mise à jour (UPDATE)
            isAn.setCity("Vannes");
            isAn.setPostcode("56000");

            // Merge optionnel si on a déjà fait un find avant (l'objet est observé)
            // em.merge(isAnnonce);

            Annonce updatedAnnonce = em.find(Annonce.class, AnnonceId);
            assertEquals("Vannes", updatedAnnonce.getCity());

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
