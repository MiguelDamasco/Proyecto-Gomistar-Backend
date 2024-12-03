package com.gomistar.proyecto_gomistar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gomistar.proyecto_gomistar.DTO.response.ErrorDTO;

@RestControllerAdvice
public class ControllerAdvice {
    
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDTO> runtimeExceptionHandler(RuntimeException ex) {
        ErrorDTO myError = new ErrorDTO("P-100", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myError);
    }

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<ErrorDTO> requestExceptionHandler(RequestException ex) {
        ErrorDTO myError = new ErrorDTO(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myError);
    }
}
