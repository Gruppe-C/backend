package de.backend.features.schoolyear.dto;

import java.io.Serializable;
import java.time.Year;

/**
 * A DTO for the {@link de.backend.features.schoolyear.SchoolYear} entity
 */
public record CreateSchoolYearDto(int startYear, int endYear) implements Serializable {
}
