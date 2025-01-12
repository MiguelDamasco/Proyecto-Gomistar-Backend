package com.gomistar.proyecto_gomistar.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.DTO.response.ShipDocumentResponseDTO;
import com.gomistar.proyecto_gomistar.model.ship.document.CertificateNavigabilityEntity;
import com.gomistar.proyecto_gomistar.service.ship.document.CertificateNavigabilityService;
import com.gomistar.proyecto_gomistar.service.ship.document.DocumentShipService;

@RestController
@RequestMapping("/certificate_navigability")
public class CertificateNavigabilityController {
    
    private final CertificateNavigabilityService certificateNavigabilityService;

    private final DocumentShipService documentShipService;

    public CertificateNavigabilityController(CertificateNavigabilityService pCertificateNavigabilityService, DocumentShipService pDocumentShipService) {
        this.certificateNavigabilityService = pCertificateNavigabilityService;
        this.documentShipService = pDocumentShipService;
    }

    @GetMapping("/get_document")
    public ResponseEntity<?> getDocument(@RequestParam String pIdShip) {

        ShipDocumentResponseDTO myDTO = this.documentShipService.getCertificateNavigability(pIdShip);
        ApiResponse<ShipDocumentResponseDTO> response = new ApiResponse<>(
        "documento enviado:",
        myDTO
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/download_image")
    public ResponseEntity<?> getDownloadImage(@RequestParam String pIdShip) {

        String imageURL = this.documentShipService.getDownloadCertificateNavigability(pIdShip);
        ApiResponse<String> response = new ApiResponse<>(
        "Imagen encontada!",
        imageURL
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBoatRegistrationController(@RequestParam MultipartFile file, @RequestParam LocalDate date) throws IOException {

        CertificateNavigabilityEntity myDocument = this.certificateNavigabilityService.createCertificateNavigability(file, date);
        ApiResponse<CertificateNavigabilityEntity> response = new ApiResponse<>("Certificado de navegabilidad creado!",
        myDocument
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add_document")
    public ResponseEntity<?> addDocument(@RequestParam String idShip, @RequestParam MultipartFile file, @RequestParam LocalDate date, @RequestParam String number) throws NumberFormatException, IOException {
        
        this.documentShipService.addDocument(idShip, file, date, Byte.parseByte(number));
        ApiResponse<CertificateNavigabilityEntity> response = new ApiResponse<>("Documento añadido con exito!",
        null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/delete_document")
    public ResponseEntity<?> deleteDocument(@RequestParam String pIdShip, @RequestParam String pType) throws IOException {

        this.documentShipService.deleteCertificateNavigability(pIdShip);
        ApiResponse<CertificateNavigabilityEntity> response = new ApiResponse<CertificateNavigabilityEntity>("Documento eliminado!"
        , null
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
}
