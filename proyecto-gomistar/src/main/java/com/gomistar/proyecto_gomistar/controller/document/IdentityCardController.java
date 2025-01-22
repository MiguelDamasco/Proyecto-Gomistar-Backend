package com.gomistar.proyecto_gomistar.controller.document;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.user.CreateIdentityCardDTO;
import com.gomistar.proyecto_gomistar.DTO.response.user.IdentityCardLectureDTO;
import com.gomistar.proyecto_gomistar.DTO.response.user.ViewIdentityCardDTO;
import com.gomistar.proyecto_gomistar.model.user.document.IdentityCardDocument;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.service.document.DocumentUserService;

@RestController
@RequestMapping("/identity_card")
public class IdentityCardController {
    
    private final DocumentUserService documentUserService;

    public IdentityCardController(DocumentUserService pDocumentUserService) {
        this.documentUserService = pDocumentUserService;
    }

    @GetMapping("/image")
    public ResponseEntity<?> getImage(@RequestParam String pIdUser) {

        String image = this.documentUserService.getImageIdentityCard(pIdUser);
        ApiResponse<String> response = new ApiResponse<>(
        "Imagen encontrada",
        image);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getIdentityCard(@RequestParam String pIdUser) {

        ViewIdentityCardDTO myDTO = this.documentUserService.getIdentityCard(pIdUser);
        ApiResponse<ViewIdentityCardDTO> response = new ApiResponse<>(
        "Documento encontrado!",
        myDTO
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/download_image")
    public ResponseEntity<?> getDownloadImage(@RequestParam String pIdUser) {

        String imageURL = this.documentUserService.getDownloadIdentityCard(pIdUser);
        ApiResponse<String> response = new ApiResponse<>(
        "Imágen encontrada!",
        imageURL
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/lecture")
    public ResponseEntity<?> getLecture(@RequestParam MultipartFile pFile) throws IOException {

        IdentityCardLectureDTO pDTO = this.documentUserService.getLecture(pFile);
        ApiResponse<IdentityCardLectureDTO> response = new ApiResponse<>(
        "Lectura completada!", 
        pDTO
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateIdentityCardDTO pDTO) throws IOException {
        this.documentUserService.createIdentityCard(pDTO);
        ApiResponse<IdentityCardDocument> response = new ApiResponse<>(
        "Documento ingresado con exito!", 
        null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestParam String pFile) throws IOException {

        this.documentUserService.cancelIdentityCard(pFile);
        ApiResponse<IdentityCardDocument> response = new ApiResponse<IdentityCardDocument>(
        "cancelado con exito!",
        null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String pIdUser) throws IOException {

        this.documentUserService.deleteIdentityCard(pIdUser, "4");
        ApiResponse<IdentityCardDocument> response = new ApiResponse<>(
        "Docuemnto eliminado!", 
        null
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
