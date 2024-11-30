package com.gomistar.proyecto_gomistar.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseOne<T> {
    
    private String message;

    private int status;

    private T data;
}
