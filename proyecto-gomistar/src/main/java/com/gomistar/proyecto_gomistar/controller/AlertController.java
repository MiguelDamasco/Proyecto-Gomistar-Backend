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
import com.gomistar.proyecto_gomistar.model.alert.AbstractAlert;
import com.gomistar.proyecto_gomistar.model.alert.ShipAlertEntity;
import com.gomistar.proyecto_gomistar.service.alert.AlertService;

@RestController
@RequestMapping("/alert")
public class AlertController {
    
    private final AlertService alertService;

    public AlertController(AlertService pAlertService) {
        this.alertService = pAlertService;
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyShipAlert(@RequestBody ModifyAlertDTO pDTO) {

        this.alertService.modifyAlert(pDTO.id(), pDTO.date());
        ApiResponse<ShipAlertEntity> response = new ApiResponse<>(
        "Alerta modificada!",
        null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAlert(@RequestParam String pIdAlert) {

        this.alertService.deleteAlert(pIdAlert);
        ApiResponse<AbstractAlert> response = new ApiResponse<>(
        "Alerta eliminada!", 
        null
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
