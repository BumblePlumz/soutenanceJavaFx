package fr.cda.scraping.model.repository;

import fr.cda.scraping.exceptions.JPAException;
import fr.cda.scraping.model.entity.Address;
import fr.cda.scraping.utils.JPATools;
import fr.cda.scraping.utils.LoggerTools;

public class AddressRepository extends JPATools implements AddressIntRepository {
    public AddressRepository() {
        super();
    }

    public Address findById(Long id) {
        Address address = null;
        start();
        try{
            address = entityManager.find(Address.class, id);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Address : erreur dans findById()", e.getCause());
        }
        close();
        return address;
    }

    public void save(Address address) {
        start();
        try{
            entityManager.persist(address);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Address : erreur dans save()", e.getCause());
        }
        close();
    }

    public void update(Address address) {
        start();
        try{
            entityManager.merge(address);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Address : erreur dans save()", e.getCause());
        }
        close();
    }

    public void delete(Address address) {
        start();
        try{
            entityManager.remove(address);
            isOk=true;
        }catch (JPAException e){
            LoggerTools.logError("Classe Address : erreur dans save()", e.getCause());
        }
        close();
    }
}
