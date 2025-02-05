package com.gomistar.proyecto_gomistar.DTO.request.ship.cargoShip;

import com.gomistar.proyecto_gomistar.DTO.request.ship.IModify;

public record ModifyCargoShipDTO(String id, String name, String idLoad, String trnasform) implements IModify {}
