package fr.cda.scraping.model.repository;


import fr.cda.scraping.exceptions.JPAException;
import fr.cda.scraping.model.entity.User;
import fr.cda.scraping.utils.JPATools;
import fr.cda.scraping.utils.LoggerTools;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

public class UserRepository extends JPATools implements UserIntRepository{
    public UserRepository(String unitName) {
        super(unitName);
    }

    public User findById(Long id) {
        User user = null;
        start();
        try {
            user = entityManager.find(User.class, id);
            isOk = true;
        }catch (JPAException e){
            LoggerTools.logError("Classe User : erreur dans findById()", e.getCause());
        }
        close();
        return user;
    }

    public User findByEmail(String email) {
        User user = null;
        start();
        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            user = query.getSingleResult();
            isOk=true;
        } catch (NoResultException e) {
            LoggerTools.logError("Classe User : erreur dans findByEmail() -> NoResult", e.getCause());
        } catch (NonUniqueResultException e) {
            LoggerTools.logError("Classe User : erreur dans findByEmail() -> NonUniqueResult", e.getCause());
        } catch (JPAException e) {
            LoggerTools.logError("Classe User : erreur dans findByEmail() -> JPAError", e.getCause());
        }
        close();
        return user;
    }

    public void save(User User) {
        start();
        try {
            entityManager.persist(User);
            isOk = true;
        }catch (JPAException e){
            LoggerTools.logError("Classe User : erreur dans save()", e.getCause());
        }
        close();
    }

    public void update(User User) {
        start();
        try{
            entityManager.merge(User);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe User : erreur dans update()", e.getCause());
        }
        close();

    }

    public void delete(User User) {
        start();
        try{
            entityManager.remove(User);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe User : erreur dans delete()", e.getCause());
        }
        close();
    }

}

