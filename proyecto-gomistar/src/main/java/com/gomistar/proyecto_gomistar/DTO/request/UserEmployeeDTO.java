package com.gomistar.proyecto_gomistar.DTO.request;

import java.util.List;

public record UserEmployeeDTO(String username, String password, String email, String name, String lastname, List<String> roles) {}
