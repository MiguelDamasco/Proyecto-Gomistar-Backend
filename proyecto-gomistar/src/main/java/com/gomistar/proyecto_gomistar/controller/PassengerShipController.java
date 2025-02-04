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

import com.gomistar.proyecto_gomistar.DTO.request.ship.AddEmployeesToShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.ModifyPassengerShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.PassengerShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.cargoShip.ModifyCargoShipDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.DTO.response.UserEmployeeResponseDTO;
import com.gomistar.proyecto_gomistar.model.ship.PassengerShipEntity;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.service.ship.PassengerShipService;
import com.gomistar.proyecto_gomistar.service.ship.ShipService;

@RestController
@RequestMapping("/Passenger_ship")
public class PassengerShipController {
    
    private final PassengerShipService passengerShipService;

    private final ShipService shipService;

    public PassengerShipController(PassengerShipService pPassengerShipService, ShipService pShipService) {
        this.passengerShipService = pPassengerShipService;
        this.shipService = pShipService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> listAllPassengerShip() {
        
        List<PassengerShipEntity> myList = this.passengerShipService.listAllPassengerShip();
        ApiResponse<List<PassengerShipEntity>> response = new ApiResponse<>(
            "Lista de barcos de pasajeros", 
            myList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get_users")
    public ResponseEntity<?> getUsers(@RequestParam String pId) {

        List<UserEmployeeResponseDTO> myList = this.passengerShipService.getUsers(pId);
        ApiResponse<List<UserEmployeeResponseDTO>> response = new ApiResponse<>(
            "Lista de tripulantes: ",
            myList
            );

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("/create")
    public ResponseEntity<?> createPassengerShip(@RequestBody PassengerShipDTO pDTO) {

        PassengerShipEntity myShip = this.passengerShipService.savePassengerShip(pDTO);
        ApiResponse<PassengerShipEntity> response = new ApiResponse<PassengerShipEntity>(
        "¡Barco de pasajeros creado con éxito!", 
        myShip);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> modifyPassengerShip(@RequestBody ModifyPassengerShipDTO pDTO) {

        PassengerShipEntity myShip = this.passengerShipService.modiftPassengerShip(pDTO);
        ApiResponse<PassengerShipEntity> response = new ApiResponse<PassengerShipEntity>(
            "¡Barco de pasajeros modificado con éxito!",
            myShip
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/passenger_to_cargo")
    public ResponseEntity<?> modifyPassengerShipToCargoShip(@RequestBody ModifyCargoShipDTO pDTO) {

        AbstractShip myShip = this.shipService.modifyShip(pDTO);
        ApiResponse<AbstractShip> response = new ApiResponse<>(
"¡Barco Modificado con éxito!",
        myShip
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePassengerShip(@RequestParam String pId) {
        this.passengerShipService.deletePassengerShip(pId);
        ApiResponse<PassengerShipEntity> response = new ApiResponse<PassengerShipEntity>(
            "¡Barco de pasajeros eliminado con éxito!",
            null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/remove_user")
    public ResponseEntity<?> removeUser(@RequestParam String pIdUser, @RequestParam String pIdShip) {

        this.passengerShipService.removeUserFromPassengerShip(pIdUser, pIdShip);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "¡Tripulante removido!", 
            null
            );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add_employees")
    public ResponseEntity<?> addEmployees(@RequestBody AddEmployeesToShipDTO pDTO) {
        
        this.passengerShipService.addEmployeesToPassenger(pDTO);
        ApiResponse<AbstractShip> response = new ApiResponse<>(
            "¡Tripulante vinculados con éxito!",
             null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
} 
