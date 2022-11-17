package de.backend.features.group.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link de.backend.features.group.Group} entity
 */
public record CreateGroupDto(@NotNull String name, String color) implements Serializable {
}
