package com.gomistar.proyecto_gomistar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.alert.ModifyAlertDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.model.alert.ShipAlertEntity;
import com.gomistar.proyecto_gomistar.service.alert.ShipAlertService;

@RestController
@RequestMapping("/ship_alert")
public class ShipAlertController {
    
    private final ShipAlertService shipAlertService;

    public ShipAlertController(ShipAlertService pShipAlertService) {
        this.shipAlertService = pShipAlertService;
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyShipAlert(@RequestBody ModifyAlertDTO pDTO) {

        ShipAlertEntity myAlert =this.shipAlertService.modifyShipAlert(pDTO.id(), pDTO.date());
        ApiResponse<ShipAlertEntity> response = new ApiResponse<>(
        "Alerta modificada!",
        myAlert);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteShipAlert(@RequestParam String pId) {

        this.shipAlertService.deleteShipAlertById(pId);
        ApiResponse<ShipAlertEntity> response = new ApiResponse<>(
        "Alerta eliminada!",
        null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
