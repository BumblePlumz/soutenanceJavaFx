package fr.cda.immobilier.model.repository;

import fr.cda.immobilier.model.entity.Annonce;

public interface AnnonceIntRepository {
    Annonce findById(Long id);
    void save(Annonce annonce);
    void update(Annonce annonce);
    void delete(Annonce annonce);
}
