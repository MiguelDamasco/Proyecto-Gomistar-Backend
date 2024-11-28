package com.gomistar.proyecto_gomistar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.request.CreateIdentityCardDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ResponseIdentityCardDTO;
import com.gomistar.proyecto_gomistar.service.document.IdentityCardService;

@RestController
@RequestMapping("/identity_card")
public class IdentityCardController {
    
    private final IdentityCardService identityCardService;

    public IdentityCardController(IdentityCardService pIdentityCardService) {
        this.identityCardService = pIdentityCardService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getIdentityCard(@RequestParam String pId) {

        ResponseIdentityCardDTO myDocument = this.identityCardService.getIdentityCardById(pId);

        if(myDocument.document() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(myDocument);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveIdentityCard(@RequestBody CreateIdentityCardDTO pIdentityCard) {
        
        return ResponseEntity.status(HttpStatus.CREATED).body(this.identityCardService.saveIdentityCard(pIdentityCard));
    }

}
