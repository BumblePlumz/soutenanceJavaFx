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

class UserTest {
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
    @DisplayName("Test CRUD de la classe User")
    void TEST_CRUD() {
        // Test de la création (CREATE)
        User user = new User();
        user.setLastname("Johnson");
        user.setFirstname("Dwayne");
        user.setEmail("the.rock@example.com");
        user.setPassword(PasswordTools.getPasswordHashed("password123"));
        user.setRole(em.find(Role.class, 1));
        em.persist(user);
        Long userId = user.getId();

        // Test de la lecture (READ)
        User isUser = em.find(User.class, userId);
        assertNotNull(isUser);
        assertEquals("Dwayne", isUser.getFirstname());
        assertEquals("Johnson", isUser.getLastname());
        assertTrue(PasswordTools.checkPassword("password123",isUser.getPassword()));
        assertNotSame("password123", isUser.getPassword());
        assertNotSame("test@example.com", isUser.getEmail());

        // Test de la mise à jour (UPDATE)
        isUser.setFirstname("Maria");

        // Merge optionnel si on a déjà fait un find avant (l'objet est observé)
        // em.merge(isUser);

        User updatedUser = em.find(User.class, userId);
        assertEquals("Maria", updatedUser.getFirstname());

        // Test de la suppression (DELETE)
        em.remove(updatedUser);
        User deletedUser = em.find(User.class, userId);
        assertNull(deletedUser);
        isOk = true;
    }
}
