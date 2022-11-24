package de.backend.features.schoolyear.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Year;

/**
 * A DTO for the {@link de.backend.features.schoolyear.SchoolYear} entity
 */
public record SchoolYearDto(String id, int startYear, int endYear, String groupId,
                            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") Timestamp createdAt) implements Serializable {
}
