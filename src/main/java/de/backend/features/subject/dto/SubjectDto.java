package de.backend.features.subject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.backend.features.schoolyear.dto.SchoolYearDto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A DTO for the {@link de.backend.features.subject.Subject} entity
 */
public record SubjectDto(String id, String name, String color, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") Timestamp createdAt,
                         SchoolYearDto schoolYear) implements Serializable {
}