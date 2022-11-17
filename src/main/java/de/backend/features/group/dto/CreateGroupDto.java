package de.backend.features.group.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link de.backend.features.group.Group} entity
 */
public record CreateGroupDto(String name, String color) implements Serializable {
}
