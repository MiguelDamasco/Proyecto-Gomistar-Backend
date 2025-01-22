package com.gomistar.proyecto_gomistar.DTO.response.user;

import java.time.LocalDate;

public record CreateIdentityCardDTO(String name,String lastname, String nationality, String identityNumber, LocalDate birthday, LocalDate expeditionDate, LocalDate expirationData, String idUser, String file) {}
