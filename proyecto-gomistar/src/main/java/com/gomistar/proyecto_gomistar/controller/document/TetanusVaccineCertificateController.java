package com.gomistar.proyecto_gomistar.controller.document;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.DTO.response.user.DocumentResponseDTO;
import com.gomistar.proyecto_gomistar.model.user.document.TetanusVaccineCertificateEntity;
import com.gomistar.proyecto_gomistar.service.document.DocumentUserService;

@RestController
@RequestMapping("/tetanus_vaccine_certificate")
public class TetanusVaccineCertificateController {
    
    private final DocumentUserService documentUserService;

    public TetanusVaccineCertificateController(DocumentUserService pDocumentUserService) {
        this.documentUserService = pDocumentUserService;
    }

    @GetMapping("/get_document")
    public ResponseEntity<?> getDocument(@RequestParam String pIdUser) {

        DocumentResponseDTO myDTO = this.documentUserService.getTetanusVaccineCertificate(pIdUser);
        ApiResponse<DocumentResponseDTO> response = new ApiResponse<>(
        "documento enviado:",
        myDTO
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/download_image")
    public ResponseEntity<?> getDownloadImage(@RequestParam String pIdUser) {

        String imageURL = this.documentUserService.getDownloadTetanusVaccineCertificate(pIdUser);
        ApiResponse<String> response = new ApiResponse<>(
        "Imagen encontada!",
        imageURL
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete_document")
    public ResponseEntity<?> deleteDocument(@RequestParam String pIdUser, @RequestParam String pType) throws IOException {

        this.documentUserService.deleteTetanusVaccineCertificate(pIdUser, pType);
        ApiResponse<TetanusVaccineCertificateEntity> response = new ApiResponse<>("Documento eliminado!"
        , null
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
