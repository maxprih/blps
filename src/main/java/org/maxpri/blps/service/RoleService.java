package org.maxpri.blps.service;

import org.maxpri.blps.model.entity.Role;
import org.maxpri.blps.repsitory.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author max_pri
 */
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getUserRole() {
        return roleRepository.findByRoleName("ROLE_USER").orElseThrow(RuntimeException::new);
    }
}
