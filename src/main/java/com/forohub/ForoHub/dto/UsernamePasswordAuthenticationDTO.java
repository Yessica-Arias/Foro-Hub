package com.forohub.ForoHub.dto;

import jakarta.validation.constraints.NotBlank;

public record UsernamePasswordAuthenticationDTO(

        @NotBlank
        String login,

        @NotBlank
        String clave) {
}
