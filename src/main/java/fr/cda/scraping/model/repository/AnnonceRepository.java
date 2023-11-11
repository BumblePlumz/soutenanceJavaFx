package fr.cda.scraping.model.repository;


import fr.cda.scraping.exceptions.JPAException;
import fr.cda.scraping.model.entity.Annonce;
import fr.cda.scraping.utils.JPATools;
import fr.cda.scraping.utils.LoggerTools;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class AnnonceRepository extends JPATools {
    public AnnonceRepository(String unitName) {
        super(unitName);
    }

    public Annonce findById(Long id) {
        Annonce annonce = null;
        start();
        try{
            annonce = entityManager.find(Annonce.class, id);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Annonce : erreur dans findById()", e.getCause());
        }
        close();
        return annonce;
    }

    public void save(Annonce annonce) {
        start();
        try{
            entityManager.persist(annonce);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Annonce : erreur dans save()", e.getCause());
        }
        close();
    }

    public void update(Annonce annonce) {
        start();
        try{
            entityManager.merge(annonce);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Annonce : erreur dans save()", e.getCause());
        }
        close();
    }

    public void delete(Annonce annonce) {
        start();
        try{
            entityManager.remove(annonce);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Annonce : erreur dans save()", e.getCause());
        }
        close();
    }

}


