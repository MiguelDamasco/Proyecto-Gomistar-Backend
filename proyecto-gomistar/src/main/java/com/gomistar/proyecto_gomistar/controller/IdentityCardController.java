package com.gomistar.proyecto_gomistar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.CreateIdentityCardDTO;
import com.gomistar.proyecto_gomistar.DTO.request.RemoveDocumentFromEmployeeDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.model.AbstractDocument;
import com.gomistar.proyecto_gomistar.model.document.IdentityCardDocument;
import com.gomistar.proyecto_gomistar.service.EmployeeDocumentService;
import com.gomistar.proyecto_gomistar.service.document.IdentityCardService;

@RestController
@RequestMapping("/identity_card")
public class IdentityCardController {
    
    private final IdentityCardService identityCardService;

    private final EmployeeDocumentService employeeDocumentService;

    public IdentityCardController(IdentityCardService pIdentityCardService, EmployeeDocumentService pEmployeeDocumentService) {
        this.employeeDocumentService = pEmployeeDocumentService;
        this.identityCardService = pIdentityCardService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addIdentityCard(@RequestBody CreateIdentityCardDTO pDTO) {

        IdentityCardDocument myDocument = (IdentityCardDocument) this.employeeDocumentService.saveAndAddToEmployee(pDTO);
        ApiResponse<IdentityCardDocument> response = new ApiResponse<>(
            "Documento agregado con exito!",
            myDocument
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getIdentityCard(@RequestParam String pId) {

        AbstractDocument myDocument = this.identityCardService.getIdentityCardById(pId);
        ApiResponse<AbstractDocument> response = new ApiResponse<>(
            "Documento encontrado!",
            myDocument
        );
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> removeDocumentFromEmployee(@RequestBody RemoveDocumentFromEmployeeDTO pDTO){

        this.employeeDocumentService.removeDocumentFromEMployee(pDTO);
        ApiResponse<AbstractDocument> response = new ApiResponse<>(
            "Docuemnto con el id: " + pDTO.idDocument() + " ha sido eliminado con exito!",
            null
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

/* 
    @PostMapping("/create")
    public ResponseEntity<?> saveIdentityCard(@RequestBody CreateIdentityCardDTO pIdentityCard) {
        
        AbstractDocument myDocument = this.identityCardService.saveIdentityCard(pIdentityCard);
        ApiResponse<AbstractDocument> response = new ApiResponse<>(
            "Documento guardado!",
            myDocument
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    */
}
