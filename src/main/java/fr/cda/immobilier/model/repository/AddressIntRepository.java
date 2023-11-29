package fr.cda.immobilier.model.repository;

import fr.cda.immobilier.model.entity.Address;

public interface AddressIntRepository {
    Address findById(Long id);
    void save(Address address);
    void update(Address address);
    void delete(Address address);
}
