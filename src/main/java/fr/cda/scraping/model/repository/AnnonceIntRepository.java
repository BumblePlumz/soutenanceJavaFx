package fr.cda.scraping.model.repository;

import fr.cda.scraping.model.entity.Annonce;

public interface AnnonceIntRepository {
    Annonce findById(Long id);
    void save(Annonce annonce);
    void update(Annonce annonce);
    void delete(Annonce annonce);
}
