package fr.cda.immobilier.model.repository;


import fr.cda.immobilier.controller.SceneController;
import fr.cda.immobilier.exceptions.JPAException;
import fr.cda.immobilier.model.entity.Type;
import fr.cda.immobilier.utils.tools.JPATools;
import fr.cda.immobilier.utils.tools.LoggerTools;
import fr.cda.immobilier.model.entity.Address;
import fr.cda.immobilier.model.entity.Annonce;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


public class AnnonceRepository extends JPATools implements AnnonceIntRepository {
    public AnnonceRepository() {
        super();
    }
    public void dropData(){
        start();
        try{
            Query query = entityManager.createQuery("DELETE FROM Annonce a");
            query.executeUpdate();
            isOk = true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Annonce : erreur dans findById()", e.getCause());
        }finally {
            close();
        }
    }
    public List<Annonce> findAll() {
        List<Annonce> annonces = null;
        start();
        try {
            // Utiliser TypedQuery avec JOIN FETCH pour récupérer toutes les annonces avec leurs entités liées
            TypedQuery<Annonce> query = entityManager.createQuery(
                    "SELECT DISTINCT a FROM Annonce a " +
                            "LEFT JOIN FETCH a.type " +
                            "LEFT JOIN FETCH a.address", Annonce.class);
            annonces = query.getResultList();
            isOk = true;
        } catch (JPAException e) {
            LoggerTools.logError("Classe Annonce : erreur dans findAllWithAssociations()", e.getCause());
        } finally {
            close();
        }
        return annonces;
    }
    public Annonce findById(Long id) {
        Annonce annonce = null;
        start();
        try{
            annonce = entityManager.find(Annonce.class, id);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Annonce : erreur dans findById()", e.getCause());
        }finally {
            close();
        }
        return annonce;
    }
    public Annonce findByHref(String href){
        Annonce annonce = null;
        start();
        try {
            TypedQuery<Annonce> query = entityManager.createQuery(
                    "SELECT a FROM Annonce a WHERE a.href = :href", Annonce.class);
            query.setParameter("href", href);
            annonce = query.getSingleResult();
            isOk = true;
        } catch (NoResultException e) {
            LoggerTools.logError("Classe Annonce : aucune annonce trouvée pour l'URL : " + href, e.getCause());
        } catch (JPAException e) {
            LoggerTools.logError("Classe Annonce : erreur dans findById()", e.getCause());
        } finally {
            close();
        }
        return annonce;
    }
    public void save(Annonce annonce) {
        start();
        try{
            entityManager.persist(annonce);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Annonce : erreur dans save()", e.getCause());
        }finally {
            close();
        }
    }

    public void update(Annonce annonce) {
        start();
        try{
            annonce.setDateUpdate(LocalDateTime.now());
            entityManager.merge(annonce);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Annonce : erreur dans save()", e.getCause());
        }
    }
    public void updateCollection(Collection<Annonce> collection){
        start();
        try{
            for (Annonce a : collection) {
                try{
                    Address existingAddress = new AddressRepository().findByCity(a.getAddress().getCity());
                    Type existingType = new TypeRepository().findByLabel(a.getType().getLabel());
                    if (existingAddress != null) {
                        if (existingAddress.getCodeSeloger() == null && a.getAddress().getCodeSeloger() != null){
                            existingAddress.setCodeSeloger(a.getAddress().getCodeSeloger());
                        }
                        if (existingAddress.getCodeOuestFrance() == null && a.getAddress().getCodeOuestFrance() != null){
                            existingAddress.setCodeOuestFrance(a.getAddress().getCodeOuestFrance());
                        }
                        a.setAddress(existingAddress);
                    }
                    if (existingType != null) {
                        a.setType(existingType);
                    }
                    Annonce existingAnnonce = new AnnonceRepository().findByHref(a.getHref());
                    a.setDateUpdate(LocalDateTime.now());
                    if (existingAnnonce != null) {
                        a.setId(existingAnnonce.getId());
                        a.setDateCreation(existingAnnonce.getDateCreation());
                        entityManager.merge(a);
                    }else{
                        if (existingAddress == null){
                            if (existingType == null){
                                entityManager.persist(a);
                            }
                            entityManager.merge(a);
                        }else{
                            if (existingType == null){
                                entityManager.persist(a);
                            }
                            entityManager.merge(a);
                        }
                    }
                }catch (JPAException e){
                    SceneController.showErrorAlert("Erreur", "Sauvegarde échouée, veuillez contacter un administrateur");
                    LoggerTools.logError("Classe Annonce : erreur dans updateCollection()", e.getCause());
                }
            }
            SceneController.showValidAlert("Confirmation", "Sauvegarde effectuée avec réussite !");
            isOk=true;
        }finally {
            close();
        }
    }

    public void delete(Annonce annonce) {
        start();
        try{
            entityManager.remove(annonce);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Annonce : erreur dans save()", e.getCause());
        }finally {
            close();
        }
    }
}


