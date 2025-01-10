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
import com.gomistar.proyecto_gomistar.model.ship.document.MandatoryInsuranceEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.MinimumSecurityEquipmentEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.RadioCommunicationsEntity;
import com.gomistar.proyecto_gomistar.service.ship.document.DocumentShipService;
import com.gomistar.proyecto_gomistar.service.ship.document.MinimumSecurityEquipmentService;

@RestController
@RequestMapping("/minimum_security_equipment")
public class MinimumSecurityEquipmentController {
    
    private final MinimumSecurityEquipmentService minimumSecurityEquipmentService;

    private final DocumentShipService documentShipService;

    public MinimumSecurityEquipmentController(MinimumSecurityEquipmentService pMinimumSecurityEquipmentService, DocumentShipService pDocumentShipService) {
        this.minimumSecurityEquipmentService = pMinimumSecurityEquipmentService;
        this.documentShipService = pDocumentShipService;
    }

     @GetMapping("/get_document")
    public ResponseEntity<?> getDocument(@RequestParam String pIdShip) {

        ShipDocumentResponseDTO myDTO = this.documentShipService.getMinimumSecurityEquipment(pIdShip);
        ApiResponse<ShipDocumentResponseDTO> response = new ApiResponse<>(
        "documento enviado:",
        myDTO
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/download_image")
    public ResponseEntity<?> getDownloadImage(@RequestParam String pIdShip) {

        String imageURL = this.documentShipService.getDownloadMinimumSecurityEquipment(pIdShip);
        ApiResponse<String> response = new ApiResponse<>(
        "Imagen encontada!",
        imageURL
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMinimumSecurityEquipment(@RequestParam MultipartFile file, @RequestParam LocalDate date) throws IOException {

        MinimumSecurityEquipmentEntity myDocument = this.minimumSecurityEquipmentService.createMinimumSecurityEquipment(file, date);
        ApiResponse<MinimumSecurityEquipmentEntity> response = new ApiResponse<>("documento creado!",
        myDocument
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add_document")
    public ResponseEntity<?> addDocument(@RequestParam String idShip, @RequestParam MultipartFile file, @RequestParam LocalDate date, @RequestParam String number) throws NumberFormatException, IOException {
        
        this.documentShipService.addDocument(idShip, file, date, Byte.parseByte(number));
        ApiResponse<MinimumSecurityEquipmentEntity> response = new ApiResponse<>("Documento añadido con exito!",
        null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/delete_document")
    public ResponseEntity<?> deleteDocument(@RequestParam String pIdShip) throws IOException {

        this.documentShipService.deleteMinimumSecurityEquipment(pIdShip);
        ApiResponse<MinimumSecurityEquipmentEntity> response = new ApiResponse<>("Documento eliminado!"
        , null
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
