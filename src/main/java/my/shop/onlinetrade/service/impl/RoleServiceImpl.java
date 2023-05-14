package my.shop.onlinetrade.service.impl;

import my.shop.onlinetrade.entity.Role;
import my.shop.onlinetrade.repository.RoleRepository;
import my.shop.onlinetrade.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String name) {
        Role role;
        Optional<Role> roleOptional = roleRepository.findByName(name);
        if (roleOptional.isEmpty()) {
            role = new Role(name);
            roleRepository.save(role);
        } else {
            role = roleOptional.get();
        }
        return role;
    }
}
