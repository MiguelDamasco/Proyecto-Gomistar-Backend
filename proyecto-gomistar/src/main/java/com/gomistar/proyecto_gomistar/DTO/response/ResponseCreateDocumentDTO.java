package com.gomistar.proyecto_gomistar.DTO.response;

import com.gomistar.proyecto_gomistar.model.user.document.AbstractDocument;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCreateDocumentDTO {
    
    private String response;

    private AbstractDocument document;
}
