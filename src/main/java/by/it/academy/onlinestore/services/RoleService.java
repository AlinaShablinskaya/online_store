package by.it.academy.onlinestore.services;

import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.entities.Role;

public interface RoleService {
    Role findByRoleName(String name);
    void createNewRole(Role role);
}
