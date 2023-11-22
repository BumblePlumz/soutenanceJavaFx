package fr.cda.scraping;

import fr.cda.scraping.model.entity.Role;
import fr.cda.scraping.model.entity.User;
import fr.cda.scraping.model.repository.RoleRepository;
import fr.cda.scraping.model.repository.UserRepository;
import fr.cda.scraping.utils.PasswordTools;

public class CreateUser {
    public static void main(String[] args) {
//        System.out.println("Début du main");
//        Connection c = Connexion.getConnexion();

        RoleRepository rr = new RoleRepository();
        UserRepository ur = new UserRepository();

//        Role role = new Role();
//        role.setType("USER");
//        role.setWrite(false);
//        role.setRead(true);
//        role.setExecute(true);
//
//        rr.save(role);

        // Test de la création (CREATE)
        User user = new User();
        user.setLastname("Toto");
        user.setFirstname("tutu");
        user.setEmail("tt@example.com");
        user.setPassword(PasswordTools.getPasswordHashed("test"));
        user.setRole(rr.findById((long)2));
        System.out.println("Sauvegarde en cours");
        ur.save(user);
        System.out.println("Fin de sauvegarde");

    }

}
