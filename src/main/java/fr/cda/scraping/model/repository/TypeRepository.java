package fr.cda.scraping.model.repository;

import fr.cda.scraping.exceptions.JPAException;
import fr.cda.scraping.model.entity.Type;
import fr.cda.scraping.utils.JPATools;
import fr.cda.scraping.utils.LoggerTools;

public class TypeRepository extends JPATools implements TypeIntRepository {
    public TypeRepository() {
        super();
    }

    public Type findById(Long id) {
        Type Type = null;
        start();
        try{
            Type = entityManager.find(Type.class, id);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Type : erreur dans findById()", e.getCause());
        }
        close();
        return Type;
    }

    public void save(Type Type) {
        start();
        try{
            entityManager.persist(Type);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Type : erreur dans save()", e.getCause());
        }
        close();
    }

    public void update(Type Type) {
        start();
        try{
            entityManager.merge(Type);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Type : erreur dans update()", e.getCause());
        }
        close();
    }

    public void delete(Type Type) {
        start();
        try{
            entityManager.remove(Type);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Type : erreur dans delete()", e.getCause());
        }
        close();
    }
}
