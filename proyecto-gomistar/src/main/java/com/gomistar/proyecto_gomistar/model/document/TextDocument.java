package com.gomistar.proyecto_gomistar.model.document;

import com.gomistar.proyecto_gomistar.model.AbstractDocument;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "Text")
public class TextDocument extends AbstractDocument {
    
    private String texto;

    public TextDocument(String pName, String pTexto) {
        super(pName);
        this.texto = pTexto;
    }

    public TextDocument() {}
}
