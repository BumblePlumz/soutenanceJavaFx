package fr.cda.scraping;

import fr.cda.scraping.model.entity.Role;
import fr.cda.scraping.model.entity.User;
import fr.cda.scraping.model.repository.RoleRepository;
import fr.cda.scraping.model.repository.UserRepository;
import fr.cda.scraping.utils.PasswordTools;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CreateUser {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("scraping");
        EntityManager em = emf.createEntityManager();

        RoleRepository rr = new RoleRepository("scraping");
        UserRepository ur = new UserRepository("scraping");

        // Test de la création (CREATE)
        User user = new User();
        user.setName("Nicolas");
        user.setFirstname("Nguyen");
        user.setEmail("n.n@example.com");
        user.setPassword(PasswordTools.getPasswordHashed("test"));
        user.setRole(em.find(Role.class, 1));

        ur.save(user);

    }
}
