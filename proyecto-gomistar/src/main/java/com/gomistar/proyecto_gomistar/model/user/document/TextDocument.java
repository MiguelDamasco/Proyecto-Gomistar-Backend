package com.gomistar.proyecto_gomistar.model.user.document;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@Entity
@Table(name = "Text")
public class TextDocument extends AbstractDocument {
    
    private String texto;

    public TextDocument(String pName, String pTexto) {
        super(pName);
        this.texto = pTexto;
    }

    public TextDocument(String pName) {
        super(pName);
    }

    public TextDocument() {}
}
