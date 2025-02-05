package com.gomistar.proyecto_gomistar.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.request.ship.DeleteShipDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ship.ListAllShipsDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.ship.document.AbstractDocumentShip;
import com.gomistar.proyecto_gomistar.service.ship.ShipService;
import com.gomistar.proyecto_gomistar.service.ship.document.DocumentShipService;

@RestController
@RequestMapping("/ship")
public class ShipController {
    
    private final ShipService shipService;

    private final DocumentShipService documentShipService;

    public ShipController(ShipService pShipService, DocumentShipService pDocumentShipService) {
        this.shipService = pShipService;
        this.documentShipService = pDocumentShipService;
    }


    @GetMapping("/list")
    public ResponseEntity<?> listAll() {

        List<ListAllShipsDTO> mylist = this.shipService.listAllShips();
        ApiResponse<List<ListAllShipsDTO>> response = new ApiResponse<>(
            "Lista de barcos: ",
            mylist);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/amount_documents")
    public ResponseEntity<?> getAmountDocuments(@RequestParam String pId) {

        String[] result = this.documentShipService.getAmountDocuments(pId);
        ApiResponse<String[]> response = new ApiResponse<>("Documentos disponibles",
        result
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add_document")
    public ResponseEntity<?> addDocumentToShip(@RequestParam String pIdShip, @RequestParam MultipartFile pFile, @RequestParam LocalDate pExpirationDate,@RequestParam String pDocumentNumber) throws IOException {

        this.documentShipService.addDocument(pIdShip, pFile, pExpirationDate, Byte.parseByte(pDocumentNumber));
        ApiResponse<AbstractDocumentShip> response = new ApiResponse<>(
        "¡Documento agregado con éxito!", 
        null
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteShip(@RequestBody DeleteShipDTO pDTO) {
        
        this.shipService.deleteShip(pDTO);
        String myMessage = "¡Embarcación de " + (Integer.parseInt(pDTO.type()) == 1 ? "carga" : "pasajeros") + " eliminada con éxito!";
        ApiResponse<AbstractShip> response = new ApiResponse<>(
        myMessage,
        null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
