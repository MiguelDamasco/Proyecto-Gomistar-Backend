package com.gomistar.proyecto_gomistar.model.ship.document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "ship_document")
public abstract class AbstractDocumentShip {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne(targetEntity = AbstractShip.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "ship_id")
    @JsonBackReference
    private AbstractShip abstractShip;
}
