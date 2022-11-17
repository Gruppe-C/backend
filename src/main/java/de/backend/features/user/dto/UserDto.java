package de.backend.features.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A DTO for the {@link de.backend.features.user.User} entity
 */
public record UserDto(String id, String username, String displayName, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") Timestamp createdAt) implements Serializable {
}
