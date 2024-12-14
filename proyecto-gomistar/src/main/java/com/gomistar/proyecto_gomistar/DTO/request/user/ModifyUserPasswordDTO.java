package com.gomistar.proyecto_gomistar.DTO.request.user;

public record ModifyUserPasswordDTO(String idUser, String oldPassword, String newPassword) implements IUserModify {}
