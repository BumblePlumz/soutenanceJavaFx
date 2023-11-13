package fr.cda.scraping.model.repository;

import fr.cda.scraping.model.entity.User;

public interface UserIntRepository {
    User findById(Long id);
    User findByEmail(String email);
    void save(User user);
    void update(User user);
    void delete(User user);
}
