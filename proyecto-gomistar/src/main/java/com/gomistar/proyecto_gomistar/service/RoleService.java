package com.gomistar.proyecto_gomistar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.role.ERole;
import com.gomistar.proyecto_gomistar.model.role.RoleEntity;
import com.gomistar.proyecto_gomistar.repository.RoleRepository;

@Service
public class RoleService {
    
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository pRoleRepository) {
        this.roleRepository = pRoleRepository;
    }

    public RoleEntity findByName(ERole pRole) {
        return this.roleRepository.findByName(pRole);
    }

    public List<RoleEntity> listAll() {
        return (List<RoleEntity>) this.roleRepository.findAll();
    }

    public RoleEntity save(String pName) {

        Optional<ERole> myRoleOptional = Optional.of(this.obtainRole(pName));

        if(!myRoleOptional.isPresent()) {
            throw new RequestException("P-288", "El rol " + pName + " no existe");
        }

        return this.roleRepository.save(new RoleEntity(myRoleOptional.get()));
    }

    public ERole obtainRole(String pName) {

        if(pName.equalsIgnoreCase("user")) {
            return ERole.USER;
        }
        else if(pName.equalsIgnoreCase("admin")) {
            return ERole.ADMIN;
        }

        return null;
    }
}
