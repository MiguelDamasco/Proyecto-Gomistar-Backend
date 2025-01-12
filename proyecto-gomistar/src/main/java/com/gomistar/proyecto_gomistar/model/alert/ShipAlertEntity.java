package com.gomistar.proyecto_gomistar.model.alert;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ship_alert")
public class ShipAlertEntity extends AbstractAlert {
    
    @ManyToOne(targetEntity = AbstractShip.class)
    @JoinColumn(name = "id_ship")
    @JsonBackReference
    private AbstractShip ship;

    private byte type;
}
