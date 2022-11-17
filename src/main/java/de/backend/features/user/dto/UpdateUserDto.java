package de.backend.features.user.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link de.backend.features.user.User} entity
 */
public record UpdateUserDto(String displayName) implements Serializable {
}
