package com.gomistar.proyecto_gomistar.DTO.request;

import com.gomistar.proyecto_gomistar.DTO.IDocument;

public record CreateIdentityCardDTO(String idUser,String name, String lastname, String nationality, String 
                                    identityNumber, String birthPlace, String birthday, String expeditionDate,
                                    String expirationData) implements IDocument {}
    
