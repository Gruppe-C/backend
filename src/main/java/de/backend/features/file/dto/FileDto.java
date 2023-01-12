package de.backend.features.file.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.backend.features.user.dto.UserDto;
import de.backend.media.dto.MediaDto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A DTO for the {@link de.backend.features.file.File} entity
 */
public record FileDto(String id, String name, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") Timestamp createdAt, MediaDto media, UserDto owner,
                      String subjectId) implements Serializable {
}
