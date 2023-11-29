package fr.cda.immobilier.model.repository;

import fr.cda.immobilier.model.entity.User;

public interface UserIntRepository {
    User findById(Long id);
    User findByEmail(String email);
    void save(User user);
    void update(User user);
    void delete(User user);
}
