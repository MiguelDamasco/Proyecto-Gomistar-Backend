package com.gomistar.proyecto_gomistar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.ship.AddUserToShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.ModifyPassengerShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.cargoShip.CreateShipCargoDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.cargoShip.ModifyCargoShipDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.ship.CargoShipEntity;
import com.gomistar.proyecto_gomistar.service.ship.CargoShipService;
import com.gomistar.proyecto_gomistar.service.ship.ShipService;

@RestController
@RequestMapping("/cargoShip")
public class CargoShipController {
    
    private final CargoShipService cargoShipService;

    private final ShipService shipService;

    public CargoShipController(CargoShipService pCargoShipService, ShipService pShipService) {
        this.cargoShipService = pCargoShipService;
        this.shipService = pShipService;
    }

    @GetMapping("/listAll")
    public ResponseEntity<?> listAllCargoShip() {

        List<AbstractShip> myList = this.cargoShipService.listAll();
        ApiResponse<List<AbstractShip>> response = new ApiResponse<>(
            "Lista de todos los barcos de carga", 
            myList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCargoShip(@RequestBody CreateShipCargoDTO pDTO) {

        CargoShipEntity myCargoShip = this.cargoShipService.saveCargoShip(pDTO);
        ApiResponse<CargoShipEntity> response = new ApiResponse<CargoShipEntity>(
            "Barco de carga creado!",
             myCargoShip
             );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUserToCargoShip(@RequestBody AddUserToShipDTO pDTO) {
        CargoShipEntity myShip = this.cargoShipService.addUser(pDTO);
        ApiResponse<CargoShipEntity> response = new ApiResponse<>(
            "Usuario agregado con exito!",
             myShip
             );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editCargoShip(@RequestBody ModifyCargoShipDTO pDTO) {

        CargoShipEntity myShip = this.cargoShipService.modifyCargoShip(pDTO);
        ApiResponse<CargoShipEntity> response = new ApiResponse<>(
        "Barco de carga modificado!",
        myShip
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PutMapping("/cargo_to_passenger")
    public ResponseEntity<?> editCargoShipToPassengerShip(@RequestBody ModifyPassengerShipDTO pDTO) {

        AbstractShip myShip = this.shipService.modifyShip(pDTO);
        ApiResponse<AbstractShip> response = new ApiResponse<>(
"Barco Modificado con exito!",
        myShip
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
