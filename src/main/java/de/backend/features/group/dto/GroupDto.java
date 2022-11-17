package de.backend.features.group.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.backend.features.user.dto.UserDto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * A DTO for the {@link de.backend.features.group.Group} entity
 */
public record GroupDto(String id, String name, String color, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") Timestamp createdAt, UserDto owner,
                       Set<UserDto> members) implements Serializable {
}
