package fr.cda.immobilier.model.repository;

import fr.cda.immobilier.exceptions.JPAException;
import fr.cda.immobilier.utils.tools.JPATools;
import fr.cda.immobilier.utils.tools.LoggerTools;
import fr.cda.immobilier.model.entity.Type;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TypeRepository extends JPATools implements TypeIntRepository {
    public TypeRepository() {
        super();
    }

    public Type findByLabel(String label){
        Type rs = null;
        start();
        try{
            TypedQuery<Type> query = entityManager.createQuery("SELECT t FROM Type t WHERE t.label=:label", Type.class);
            query.setParameter("label", label);
            rs = query.getSingleResult();
        }catch (JPAException e){
            LoggerTools.logError("Classe Type : erreur dans findAll()", e.getCause());
        }finally {
            close();
        }
        return rs;
    }
    public Type findById(Long id) {
        Type type = null;
        start();
        try{
            type = entityManager.find(Type.class, id);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Type : erreur dans findById()", e.getCause());
        }finally {
            close();
        }
        return type;
    }
    public List<Type> findAll(){
        List<Type> rs = null;
        start();
        try{
            TypedQuery<Type> query = entityManager.createQuery("SELECT t FROM Type t", Type.class);
            rs = query.getResultList();
        }catch (JPAException e){
            LoggerTools.logError("Classe Type : erreur dans findAll()", e.getCause());
        }finally {
            close();
        }
        return rs;
    }
    public void save(Type Type) {
        start();
        try{
            entityManager.merge(Type);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Type : erreur dans save()", e.getCause());
        }finally {
            close();
        }
    }

    public void update(Type Type) {
        start();
        try{
            entityManager.merge(Type);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Type : erreur dans update()", e.getCause());
        }finally {
            close();
        }
    }

    public void delete(Type Type) {
        start();
        try{
            entityManager.remove(Type);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Type : erreur dans delete()", e.getCause());
        }finally {
            close();
        }
    }
}
