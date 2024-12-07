package com.gomistar.proyecto_gomistar.controller;

import java.io.IOException;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.IDocument;
import com.gomistar.proyecto_gomistar.DTO.request.CreateIdentityCardDTO;
import com.gomistar.proyecto_gomistar.DTO.request.CreateTextDocument;
import com.gomistar.proyecto_gomistar.DTO.request.CreateTextDocumentDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.DTO.response.S3ResponseDTO;
import com.gomistar.proyecto_gomistar.model.AbstractDocument;
import com.gomistar.proyecto_gomistar.model.document.TextDocument;
import com.gomistar.proyecto_gomistar.service.EmployeeDocumentService;
import com.gomistar.proyecto_gomistar.service.document.DocumentService;
import com.gomistar.proyecto_gomistar.service.s3.S3Service;

@RestController
@RequestMapping("/document")
public class DocumentController {
    
    private final DocumentService documentService;

    private final EmployeeDocumentService employeeDocumentService;

    private final S3Service s3Service;

    public DocumentController(DocumentService pDocumentService, EmployeeDocumentService pEmployeeDocumentService, S3Service pS3Service) {
        this.documentService = pDocumentService;
        this.employeeDocumentService = pEmployeeDocumentService;
        this.s3Service = pS3Service;
    }

    @PostMapping("/test")
    public ResponseEntity<?> createTextDocument(CreateTextDocumentDTO pDocument) {

        AbstractDocument myDocument = this.employeeDocumentService.saveAndAddToEmployee(pDocument);
        ApiResponse<AbstractDocument> response = new ApiResponse<>(
            "Documento de texto creado ",
            myDocument
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile pFile) throws IOException {

        S3ResponseDTO myDTO = this.s3Service.uploadFile(pFile);
        ApiResponse<S3ResponseDTO> response = new ApiResponse<>(
            "Archivo subido correctamente!",
            myDTO
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/listDocuments")
    public ResponseEntity<?> listAll(@RequestBody CreateTextDocumentDTO pDocument) {

        System.out.println("id: " + pDocument.idUser());
        System.out.println("name: " + pDocument.name());
        System.out.println("text: " + pDocument.text());
        List<TextDocument> myList = this.employeeDocumentService.listDocuments(pDocument);
        ApiResponse<List<TextDocument>> response = new ApiResponse<>(
            "Lista de documentos: ",
            myList
        );

        return ResponseEntity.status(HttpStatus.OK).body(response); 
    } 


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllDocuments() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.documentService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDocument(@RequestBody CreateTextDocument document) {

        TextDocument myDocument = new TextDocument(document.getName(), document.getText());
        AbstractDocument result = this.documentService.save(myDocument);
        if(result != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear usuario.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDocument(@RequestParam String id) {

        boolean result = this.documentService.deleteDocument(Long.parseLong(id));
        if(result) {
            return ResponseEntity.status(HttpStatus.OK).body("Documento eliminado!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, el documento no existe.");
    }



}
