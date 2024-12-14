package com.gomistar.proyecto_gomistar.DTO.request.user;

public record ModifyUserEmailDTO(String idUser, String email)  implements IUserModify {}
