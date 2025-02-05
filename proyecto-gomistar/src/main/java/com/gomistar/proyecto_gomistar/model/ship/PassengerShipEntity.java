package com.gomistar.proyecto_gomistar.model.ship;

import jakarta.persistence.Entity;
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
@Table(name = "passenger_ship")
public class PassengerShipEntity extends AbstractShip {}
