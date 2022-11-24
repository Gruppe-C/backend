package de.backend.features.subject.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link de.backend.features.subject.Subject} entity
 */
public record CreateSubjectDto(String name, String color) implements Serializable {
}