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
import com.gomistar.proyecto_gomistar.service.ship.document.CertificateNavigabilityService;

@RestController
@RequestMapping("/certificate_navigability")
public class CertificateNavigabilityController {
    
    private final CertificateNavigabilityService certificateNavigabilityService;

    public CertificateNavigabilityController(CertificateNavigabilityService pCertificateNavigabilityService) {
        this.certificateNavigabilityService = pCertificateNavigabilityService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBoatRegistrationController(@RequestParam MultipartFile file, @RequestParam LocalDate date) throws IOException {

        CertificateNavigabilityEntity myDocument = this.certificateNavigabilityService.createCertificateNavigability(file, date);
        ApiResponse<CertificateNavigabilityEntity> response = new ApiResponse<>("Certificado de navegabilidad creado!",
        myDocument
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
