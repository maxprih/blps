package org.maxpri.blps.service;

import org.maxpri.blps.exception.UsernameNotFoundException;
import org.maxpri.blps.model.entity.articleEntity.Role;
import org.maxpri.blps.model.entity.articleEntity.User;
import org.maxpri.blps.repository.articleRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author max_pri
 */
@Service
public class AdminService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public AdminService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public void setAdmin(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        Set<Role> newRoles = user.getRoles();
        newRoles.add(roleService.getAdminRole());

        user.setRoles(newRoles);
        userRepository.save(user);
    }
}
