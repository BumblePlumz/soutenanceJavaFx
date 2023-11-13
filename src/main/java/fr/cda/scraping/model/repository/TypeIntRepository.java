package fr.cda.scraping.model.repository;

import fr.cda.scraping.model.entity.Type;

public interface TypeIntRepository {
    Type findById(Long id);
    void save(Type role);
    void update(Type role);
    void delete(Type role);
}
