package com.gomistar.proyecto_gomistar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomistar.proyecto_gomistar.model.ERole;
import com.gomistar.proyecto_gomistar.model.RoleEntity;



@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    
    RoleEntity findByName(ERole name);
}
