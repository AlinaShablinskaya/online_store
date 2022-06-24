package by.it.academy.onlinestore.dao;

import by.it.academy.onlinestore.dao.impl.jdbc.AbstractCrudDaoImpl;
import by.it.academy.onlinestore.entities.Role;

import java.util.Optional;

public interface RoleDao extends CrudDao<Role, Integer> {
    Optional<Role> findByRoleName(String name);
}
