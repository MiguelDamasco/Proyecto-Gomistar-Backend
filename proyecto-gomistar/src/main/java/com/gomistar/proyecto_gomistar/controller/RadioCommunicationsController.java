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
import com.gomistar.proyecto_gomistar.model.ship.document.RadioCommunicationsEntity;
import com.gomistar.proyecto_gomistar.service.ship.document.DocumentShipService;
import com.gomistar.proyecto_gomistar.service.ship.document.RadioCommunicationsService;

@RestController
@RequestMapping("/radio_communications")
public class RadioCommunicationsController {
    
    private final RadioCommunicationsService radioCommunicationsService;

    private final DocumentShipService documentShipService;

    public RadioCommunicationsController(RadioCommunicationsService pRadioCommunicationsService, DocumentShipService pDocumentShipService) {
        this.radioCommunicationsService = pRadioCommunicationsService;
        this.documentShipService = pDocumentShipService;
    }

    @GetMapping("/get_document")
    public ResponseEntity<?> getDocument(@RequestParam String pIdShip) {

        ShipDocumentResponseDTO myDTO = this.documentShipService.getRadioCommunications(pIdShip);
        ApiResponse<ShipDocumentResponseDTO> response = new ApiResponse<>(
        "documento enviado:",
        myDTO
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/download_image")
    public ResponseEntity<?> getDownloadImage(@RequestParam String pIdShip) {

        String imageURL = this.documentShipService.getDownloadRadioCommunications(pIdShip);
        ApiResponse<String> response = new ApiResponse<>(
        "Imagen encontada!",
        imageURL
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createRadioCommunications(@RequestParam MultipartFile file, @RequestParam LocalDate date) throws IOException {

        RadioCommunicationsEntity myDocument = this.radioCommunicationsService.createRadioCommunications(file, date);
        ApiResponse<RadioCommunicationsEntity> response = new ApiResponse<>("¡Certificado de navegabilidad creado!",
        myDocument
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add_document")
    public ResponseEntity<?> addDocument(@RequestParam String idShip, @RequestParam MultipartFile file, @RequestParam LocalDate date, @RequestParam String number) throws NumberFormatException, IOException {
        
        this.documentShipService.addDocument(idShip, file, date, Byte.parseByte(number));
        ApiResponse<RadioCommunicationsEntity> response = new ApiResponse<>("¡Documento añadido con éxito!",
        null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/delete_document")
    public ResponseEntity<?> deleteDocument(@RequestParam String pIdShip, @RequestParam String pType) throws IOException {

        this.documentShipService.deleteRadioCommunicationsEntity(pIdShip, pType);
        ApiResponse<RadioCommunicationsEntity> response = new ApiResponse<>("¡Documento eliminado!"
        , null
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
