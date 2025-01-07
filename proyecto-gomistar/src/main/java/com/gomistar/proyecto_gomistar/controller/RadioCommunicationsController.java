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
import com.gomistar.proyecto_gomistar.model.ship.document.CertificateNavigabilityEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.RadioCommunicationsEntity;
import com.gomistar.proyecto_gomistar.service.ship.document.RadioCommunicationsService;

@RestController
@RequestMapping("/radio_communications")
public class RadioCommunicationsController {
    
    private final RadioCommunicationsService radioCommunicationsService;

    public RadioCommunicationsController(RadioCommunicationsService pRadioCommunicationsService) {
        this.radioCommunicationsService = pRadioCommunicationsService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRadioCommunications(@RequestParam MultipartFile file, @RequestParam LocalDate date) throws IOException {

        RadioCommunicationsEntity myDocument = this.radioCommunicationsService.createRadioCommunications(file, date);
        ApiResponse<RadioCommunicationsEntity> response = new ApiResponse<>("Certificado de navegabilidad creado!",
        myDocument
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
