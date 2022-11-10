package de.backend.features.user.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A DTO for the {@link de.backend.features.user.User} entity
 */
public record UserDto(String id, String username, Timestamp createdAt) implements Serializable {
}
