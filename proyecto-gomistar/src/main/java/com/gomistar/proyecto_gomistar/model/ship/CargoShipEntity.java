package com.gomistar.proyecto_gomistar.model.ship;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@Entity
@Table(name = "cargo_ship")
public class CargoShipEntity extends AbstractShip {
    
    @ManyToOne(targetEntity = LoadTypeEntity.class)
    @JoinColumn(name = "load_type_id")
    @JsonBackReference
    private LoadTypeEntity loadType;
}
