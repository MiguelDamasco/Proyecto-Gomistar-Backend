package com.gomistar.proyecto_gomistar.repository.alert;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.gomistar.proyecto_gomistar.model.alert.AbstractAlert;

@NoRepositoryBean
public interface AbstractAlertRepository extends CrudRepository<AbstractAlert, Long> {}
