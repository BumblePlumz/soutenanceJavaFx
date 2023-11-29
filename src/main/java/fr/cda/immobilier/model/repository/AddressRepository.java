package fr.cda.immobilier.model.repository;

import fr.cda.immobilier.exceptions.JPAException;
import fr.cda.immobilier.utils.tools.JPATools;
import fr.cda.immobilier.utils.tools.LoggerTools;
import fr.cda.immobilier.model.entity.Address;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.NonUniqueResultException;

public class AddressRepository extends JPATools implements AddressIntRepository {
    public AddressRepository() {
        super();
    }
    public Address findByCity(String city){
        Address address = null;
        start();
        try {
            TypedQuery<Address> query = entityManager.createQuery( "SELECT a FROM Address a WHERE a.city = :city", Address.class);
            query.setParameter("city", city);
            address = query.getSingleResult();
            isOk = true;
        } catch (NoResultException e) {
            LoggerTools.logError("Classe Address : aucune adresse trouvée pour la ville : " + city, e);
        } catch (NonUniqueResultException e) {
            LoggerTools.logError("Classe Address : plusieurs adresses trouvées pour la ville : " + city, e);
        } catch (JPAException e){
            LoggerTools.logError("Classe Address : erreur dans findByCity()", e.getCause());
        }finally {
            close();
        }
        return address;
    }
    @Override
    public Address findById(Long id) {
        Address address = null;
        start();
        try{
            address = entityManager.find(Address.class, id);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Address : erreur dans findById()", e.getCause());
        }finally {
            close();
        }
        return address;
    }
    @Override
    public void save(Address address) {
        start();
        try{
            entityManager.persist(address);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Address : erreur dans save()", e.getCause());
        }finally {
            close();
        }
    }
    @Override
    public void update(Address address) {
        start();
        try{
            entityManager.merge(address);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Address : erreur dans save()", e.getCause());
        }finally {
            close();
        }
    }
    @Override
    public void delete(Address address) {
        start();
        try{
            entityManager.remove(address);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Address : erreur dans save()", e.getCause());
        }finally {
            close();
        }
    }
}
