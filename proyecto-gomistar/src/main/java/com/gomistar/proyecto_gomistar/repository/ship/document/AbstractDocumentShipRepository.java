package com.gomistar.proyecto_gomistar.repository.ship.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.gomistar.proyecto_gomistar.model.ship.document.AbstractDocumentShip;

@NoRepositoryBean
public interface AbstractDocumentShipRepository extends JpaRepository<AbstractDocumentShip, Long> {
    
}
