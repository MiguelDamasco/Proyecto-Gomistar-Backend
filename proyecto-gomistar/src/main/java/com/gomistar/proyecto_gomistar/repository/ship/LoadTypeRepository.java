package com.gomistar.proyecto_gomistar.repository.ship;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomistar.proyecto_gomistar.model.ship.LoadTypeEntity;

@Repository
public interface LoadTypeRepository extends CrudRepository<LoadTypeEntity, Long> {}
