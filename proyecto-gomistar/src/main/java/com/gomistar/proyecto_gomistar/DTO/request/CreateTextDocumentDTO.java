package com.gomistar.proyecto_gomistar.DTO.request;

import com.gomistar.proyecto_gomistar.DTO.IDocument;

public record CreateTextDocumentDTO(String idUser, String name, String text) implements IDocument {}
