package fr.cda.scraping.model.repository;


import fr.cda.scraping.exceptions.JPAException;
import fr.cda.scraping.model.entity.Role;
import fr.cda.scraping.utils.JPATools;
import fr.cda.scraping.utils.LoggerTools;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class RoleRepository extends JPATools implements RoleIntRepository {
    public RoleRepository() {
        super();
    }

    public Role findById(Long id) {
        Role role = null;
        start();
        try{
            role = entityManager.find(Role.class, id);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Role : erreur dans findById()", e.getCause());
        }
        close();
        return role;
    }

    public void save(Role role) {
        start();
        try{
            entityManager.persist(role);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Role : erreur dans save()", e.getCause());
        }
        close();
    }

    public void update(Role role) {
        start();
        try{
            entityManager.merge(role);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Role : erreur dans update()", e.getCause());
        }
        close();
    }

    public void delete(Role role) {
        start();
        try{
            entityManager.remove(role);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Role : erreur dans delete()", e.getCause());
        }
        close();
    }

}

