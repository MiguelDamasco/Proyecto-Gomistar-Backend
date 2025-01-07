package com.gomistar.proyecto_gomistar.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.model.ship.document.MandatoryInsuranceEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.MinimumSecurityEquipmentEntity;
import com.gomistar.proyecto_gomistar.service.ship.document.MinimumSecurityEquipmentService;

@RestController
@RequestMapping("/minimum_security_equipment")
public class MinimumSecurityEquipmentController {
    
    private final MinimumSecurityEquipmentService minimumSecurityEquipmentService;

    public MinimumSecurityEquipmentController(MinimumSecurityEquipmentService pMinimumSecurityEquipmentService) {
        this.minimumSecurityEquipmentService = pMinimumSecurityEquipmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMinimumSecurityEquipment(@RequestParam MultipartFile file, @RequestParam LocalDate date) throws IOException {

        MinimumSecurityEquipmentEntity myDocument = this.minimumSecurityEquipmentService.createMinimumSecurityEquipment(file, date);
        ApiResponse<MinimumSecurityEquipmentEntity> response = new ApiResponse<>("documento creado!",
        myDocument
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
