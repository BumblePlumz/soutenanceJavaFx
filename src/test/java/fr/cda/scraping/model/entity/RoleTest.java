package fr.cda.scraping.model.entity;


import static org.junit.jupiter.api.Assertions.*;

import fr.cda.scraping.utils.PasswordTools;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
public class RoleTest {
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
    @DisplayName("Test CRUD de la classe Role")
    void TEST_CRUD() {
        try {
        // Test de la création (CREATE)
        Role role = new Role();
        role.setType("TEST");
        role.setRead(true);
        role.setWrite(false);
        role.setExecute(false);
        em.persist(role);
        Long roleId = role.getId();

        // Test de la lecture (READ)
        Role isRole = em.find(Role.class, roleId);
        assertNotNull(isRole);
        assertEquals("TEST", isRole.getType());
        assertEquals(true, isRole.isRead());
        assertNotSame(true, isRole.isExecute());
        assertNotSame(true, isRole.isWrite());

        // Test de la mise à jour (UPDATE)
        isRole.setWrite(true);

        // Merge optionnel si on a déjà fait un find avant (l'objet est observé)
        // em.merge(isUser);

        Role updatedRole = em.find(Role.class, roleId);
        assertEquals(true, updatedRole.isWrite());

        // Test de la suppression (DELETE)
        em.remove(updatedRole);
        Role deletedRole = em.find(Role.class, roleId);
        assertNull(deletedRole);
        isOk = true;
        }catch (Exception e){
            isOk = false;
            System.out.println("Une erreur est survenue");
        }
    }
}
