package com.gomistar.proyecto_gomistar.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class RequestException extends RuntimeException {
    
    private String code;

    public RequestException(String pCode, String pMessage) {
        super(pMessage);
        this.code = pCode;
    }
}
