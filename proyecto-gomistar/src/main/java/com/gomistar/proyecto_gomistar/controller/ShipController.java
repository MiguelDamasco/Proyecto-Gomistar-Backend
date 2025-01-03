package com.gomistar.proyecto_gomistar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.ship.DeleteShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.ListAllShipsDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.service.ship.ShipService;

@RestController
@RequestMapping("/ship")
public class ShipController {
    
    private final ShipService shipService;

    public ShipController(ShipService pShipService) {
        this.shipService = pShipService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> listAll() {

        List<ListAllShipsDTO> mylist = this.shipService.listAllShips();
        ApiResponse<List<ListAllShipsDTO>> response = new ApiResponse<>(
            "Lista de barcos: ",
            mylist);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteShip(@RequestBody DeleteShipDTO pDTO) {
        
        this.shipService.deleteShip(pDTO);
        String myMessage = "Embarcación de " + (Integer.parseInt(pDTO.type()) == 1 ? "carga" : "pasajeros") + " eliminada con exito!";
        ApiResponse<AbstractShip> response = new ApiResponse<>(myMessage,
         null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
