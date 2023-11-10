//package fr.cda.scraping.service;
//
//import fr.cda.scraping.model.entities.User;
//import fr.cda.scraping.model.repository.AnnonceRepository;
//import fr.cda.scraping.model.repository.UserRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.persistence.*;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.metamodel.Metamodel;
//import java.util.Map;
//
//@Service
//public class UserService implements EntityManagerFactory {
//    @Autowired
//    private UserRepository userRepository;
//
//    public User getUserById(long id) {
//        return userRepository.findById(id);
//    }
//    public User createUser(User user) {
//        return userRepository.save(user);
//    }
//
//}
