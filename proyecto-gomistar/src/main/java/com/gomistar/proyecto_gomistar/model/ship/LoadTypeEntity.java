package com.gomistar.proyecto_gomistar.model.ship;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "load_type")
public class LoadTypeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "loadType", targetEntity = CargoShipEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CargoShipEntity> shipList;

    public void addShip(CargoShipEntity pShip) {
        pShip.setLoadType(this);
        this.shipList.add(pShip);
    }

    public void removeShip(CargoShipEntity pShip) {
        pShip.setLoadType(null);
        this.shipList.remove(pShip);
    }
}
