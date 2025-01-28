package com.gomistar.proyecto_gomistar.DTO.request.user;

import java.util.List;

public record CreateUserDTO(String username, String password, String email, String name, String lastname, List<String> roles) {
    
}
