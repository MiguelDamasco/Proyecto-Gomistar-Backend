package com.gomistar.proyecto_gomistar.repository.ship;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;

@NoRepositoryBean
public interface AbstractShipRespository extends CrudRepository<AbstractShip, Long> {}