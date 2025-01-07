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
import com.gomistar.proyecto_gomistar.model.ship.document.MandatoryInsuranceEntity;
import com.gomistar.proyecto_gomistar.service.ship.document.MandatoryInsuranceService;

@RestController
@RequestMapping("/mandatory_insurance")
public class MandatoryInsuranceController {
    
    private final MandatoryInsuranceService mandatoryInsuranceService;

    public MandatoryInsuranceController(MandatoryInsuranceService pMandatoryInsuranceService) {
        this.mandatoryInsuranceService = pMandatoryInsuranceService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMandatoryInsurance(@RequestParam MultipartFile file, @RequestParam LocalDate date) throws IOException {

        MandatoryInsuranceEntity myDocument = this.mandatoryInsuranceService.createMandatoryInsurance(file, date);
        ApiResponse<MandatoryInsuranceEntity> response = new ApiResponse<>("seguro creado!",
        myDocument
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
