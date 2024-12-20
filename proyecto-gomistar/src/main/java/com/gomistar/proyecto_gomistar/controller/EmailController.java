package com.gomistar.proyecto_gomistar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gomistar.proyecto_gomistar.DTO.email.EmailDTO;
import com.gomistar.proyecto_gomistar.DTO.request.ConfirmEmailDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ApiResponse;
import com.gomistar.proyecto_gomistar.service.UserService;
import com.gomistar.proyecto_gomistar.service.email.EmailService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/email")
public class EmailController {
    
    private final EmailService emailService;

    private final UserService userService;

    public EmailController(EmailService pEmailService, UserService pUserService) {
        this.emailService = pEmailService;
        this.userService = pUserService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDTO pDTO) throws MessagingException {
        this.emailService.sendMail(pDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Mensjae enviado correctamente!");
    }

    @PostMapping("/token")
    public ResponseEntity<?> createConfirmationToken(@RequestParam String pIdUser) {

        this.userService.addConfirmationToken(pIdUser);
        ApiResponse<String> response = new ApiResponse<String>(
            "Confirmation token crado con exito!",
            pIdUser
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmToken(@RequestBody ConfirmEmailDTO pDTO) {

        this.userService.confirmEmail(pDTO);
        ApiResponse<String> response = new ApiResponse<>(
            "Email confirmado!", 
            pDTO.token());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
