package fr.cda.immobilier.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import fr.cda.immobilier.model.entity.Role;
import fr.cda.immobilier.model.entity.User;
import fr.cda.immobilier.utils.Config;
import fr.cda.immobilier.utils.tools.PasswordTools;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

class UserTest {
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
        isUser.setFirstname("John");
        isUser.setLastname("Cena");
        isUser.setEmail("JC@ex.com");
        isUser.setPassword(PasswordTools.getPasswordHashed("passwordTest"));

        isUser.setRole(em.find(Role.class, 2));


        // Merge optionnel si on a déjà fait un find avant (l'objet est observé)
        // em.merge(isUser);

        User updatedUser = em.find(User.class, userId);
        assertNotNull(isUser);
        assertEquals("John", isUser.getFirstname());
        assertEquals("Cena", isUser.getLastname());
        assertTrue(PasswordTools.checkPassword("passwordTest",isUser.getPassword()));
        assertEquals("JC@ex.com", isUser.getEmail());
        assertNotSame("password123", isUser.getPassword());
        assertNotSame("test@example.com", isUser.getEmail());

        // Test de la suppression (DELETE)
        em.remove(updatedUser);
        User deletedUser = em.find(User.class, userId);
        assertNull(deletedUser);
        isOk = true;
    }
}
