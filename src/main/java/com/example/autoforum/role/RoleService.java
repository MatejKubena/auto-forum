package com.example.autoforum.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRole(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public void updateRole(int id, Role role) {
        roleRepository.save(role);
    }

    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
