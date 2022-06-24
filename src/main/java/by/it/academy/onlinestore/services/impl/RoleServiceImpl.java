package by.it.academy.onlinestore.services.impl;

import by.it.academy.onlinestore.dao.RoleDao;
import by.it.academy.onlinestore.entities.Catalog;
import by.it.academy.onlinestore.entities.Role;
import by.it.academy.onlinestore.services.RoleService;
import by.it.academy.onlinestore.services.exeption.EntityAlreadyExistException;
import by.it.academy.onlinestore.services.exeption.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoleServiceImpl implements RoleService {
    private static final String ROLE_IS_NOT_FOUND = "Specified role is not found.";
    private static final String ROLE_ALREADY_EXISTS = "Specified role already exists.";
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void createNewRole(Role role) {
        log.info("Adding role {} started", role);
        if (roleDao.findByRoleName(role.getRoleName()).isPresent()) {
            log.error("Role {} already exists.", role);
            throw new EntityAlreadyExistException(ROLE_ALREADY_EXISTS);
        }
        roleDao.save(role);
        log.info("Role {} successfully added.", role);
    }

    @Override
    public Role findByRoleName(String name) {
        log.info("Find role by name = {} started", name);
        return roleDao.findByRoleName(name).orElseThrow(() -> {
            log.error("Role is not found.");
            return new EntityNotFoundException(ROLE_IS_NOT_FOUND);
        });
    }
}
