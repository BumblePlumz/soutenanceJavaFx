package fr.cda.immobilier.model.entity;


import static org.junit.jupiter.api.Assertions.*;

import fr.cda.immobilier.model.entity.Role;
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

public class RoleTest {
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
        assertTrue(isRole.isRead());
        assertFalse(isRole.isExecute());
        assertFalse(isRole.isWrite());

        // Test de la mise à jour (UPDATE)
        isRole.setWrite(true);
        isRole.setExecute(true);

        // Merge optionnel si on a déjà fait un find avant (l'objet est observé)
        // em.merge(isUser);

        Role updatedRole = em.find(Role.class, roleId);
        assertTrue(updatedRole.isRead());
        assertTrue(updatedRole.isWrite());
        assertTrue(updatedRole.isExecute());

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
