package com.forohub_alura.Foro_hub.domain.user;

import jakarta.validation.constraints.NotNull;

public record UserDataUpdate(@NotNull Long id,
                             String name,
                             String email) {
}
