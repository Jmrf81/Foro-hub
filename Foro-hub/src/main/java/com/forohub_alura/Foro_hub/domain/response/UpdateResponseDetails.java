package com.forohub_alura.Foro_hub.domain.response;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateResponseDetails(@NotNull Long id, String solution, @NotNull Long idUser, @NotNull Long idTopic, LocalDateTime creationdate) {
}
