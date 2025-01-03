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
import com.gomistar.proyecto_gomistar.DTO.request.ship.AddUserToShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.ModifyPassengerShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.cargoShip.CreateShipCargoDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.cargoShip.ModifyCargoShipDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.DTO.response.UserEmployeeResponseDTO;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.ship.CargoShipEntity;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
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

        List<CargoShipEntity> myList = this.cargoShipService.listAllCargoShip();
        ApiResponse<List<CargoShipEntity>> response = new ApiResponse<>(
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

    @GetMapping("/get_users")
    public ResponseEntity<?> getUsers(@RequestParam String pId) {

        List<UserEmployeeResponseDTO> myList = this.cargoShipService.getUsers(pId);
        ApiResponse<List<UserEmployeeResponseDTO>> response = new ApiResponse<>(
            "Lista de tripulantes: ",
            myList
            );

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/remove_user")
    public ResponseEntity<?> removeUser(@RequestParam String pIdUser, @RequestParam String pIdShip) {

        this.shipService.removeUserFromCargoShip(pIdUser, pIdShip);
        ApiResponse<UserEntity> response = new ApiResponse<>(
            "Tripulante removido!", 
            null
            );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add_employees")
    public ResponseEntity<?> addEmployees(@RequestBody AddEmployeesToShipDTO pDTO) {
        
        this.shipService.addEmployeesToCargoShip(pDTO);
        ApiResponse<AbstractShip> response = new ApiResponse<>(
            "Tripulantes vinculados con exito!",
             null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
