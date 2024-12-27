package com.gomistar.proyecto_gomistar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.ship.AddUserToShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.cargoShip.CreateShipCargoDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.ship.CargoShipEntity;
import com.gomistar.proyecto_gomistar.service.ship.CargoShipService;

@RestController
@RequestMapping("/cargoShip")
public class CargoShipController {
    
    private final CargoShipService cargoShipService;

    public CargoShipController(CargoShipService pCargoShipService) {
        this.cargoShipService = pCargoShipService;
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
}
