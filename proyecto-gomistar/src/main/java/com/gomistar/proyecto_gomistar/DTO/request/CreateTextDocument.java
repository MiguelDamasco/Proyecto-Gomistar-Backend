package com.gomistar.proyecto_gomistar.DTO.request;

import com.gomistar.proyecto_gomistar.DTO.IDocument;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTextDocument implements IDocument {
    
    private String name;

    private String text;
}
