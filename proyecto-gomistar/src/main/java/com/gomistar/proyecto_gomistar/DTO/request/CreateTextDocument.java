package com.gomistar.proyecto_gomistar.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTextDocument {
    
    private String name;

    private String text;
}
