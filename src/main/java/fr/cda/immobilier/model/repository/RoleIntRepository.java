package fr.cda.immobilier.model.repository;

import fr.cda.immobilier.model.entity.Role;

public interface RoleIntRepository {
    Role findById(Long id);
    void save(Role role);
    void update(Role role);
    void delete(Role role);
}
