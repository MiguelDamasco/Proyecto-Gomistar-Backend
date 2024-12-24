package com.gomistar.proyecto_gomistar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.ship.ModifyPassengerShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.PassengerShipDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.model.ship.PassengerShipEntity;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.service.ship.PassengerShipService;

@RestController
@RequestMapping("/Passenger_ship")
public class PassengerShipController {
    
    private final PassengerShipService passengerShipService;

    public PassengerShipController(PassengerShipService pPassengerShipService) {
        this.passengerShipService = pPassengerShipService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> listAllPassengerShip() {
        List<AbstractShip> myList = this.passengerShipService.listAllPassengerShip();
        ApiResponse<List<AbstractShip>> response = new ApiResponse<>(
            "Lista de barcos de pasajeros", 
            myList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPassengerShip(@RequestBody PassengerShipDTO pDTO) {

        PassengerShipEntity myShip = this.passengerShipService.savePassengerShip(pDTO);
        ApiResponse<PassengerShipEntity> response = new ApiResponse<PassengerShipEntity>(
        "Barco de pasajeros creado con exito!", 
        myShip);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyPassengerShip(@RequestBody ModifyPassengerShipDTO pDTO) {

        PassengerShipEntity myShip = this.passengerShipService.modiftPassengerShip(pDTO);
        ApiResponse<PassengerShipEntity> response = new ApiResponse<PassengerShipEntity>(
            "Barco de pasajeros modificado con exito!",
            myShip
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePassengerShip(@RequestParam String pId) {
        this.passengerShipService.deletePassengerShip(pId);
        ApiResponse<PassengerShipEntity> response = new ApiResponse<PassengerShipEntity>(
            "Barco de pasajeros eliminado con exito!",
            null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
} 
