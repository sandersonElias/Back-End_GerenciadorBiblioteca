package com.Gerenciador.Biblioteca_BackEnd.dto;

public record AuthResponse(String accessToken, String tokenType, long expiresIn) {
}
