package fr.cda.immobilier.model.repository;


import fr.cda.immobilier.exceptions.JPAException;
import fr.cda.immobilier.utils.tools.JPATools;
import fr.cda.immobilier.utils.tools.LoggerTools;
import fr.cda.immobilier.model.entity.Role;

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
        }finally {
            close();
        }
        return role;
    }

    public void save(Role role) {
        start();
        try{
            entityManager.persist(role);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Role : erreur dans save()", e.getCause());
        }finally {
            close();
        }
    }

    public void update(Role role) {
        start();
        try{
            entityManager.merge(role);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Role : erreur dans update()", e.getCause());
        }finally {
            close();
        }
    }

    public void delete(Role role) {
        start();
        try{
            entityManager.remove(role);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Role : erreur dans delete()", e.getCause());
        }finally {
            close();
        }
    }

}

