package de.backend.api.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Api Validation Exception Model
 *
 * @author Raphael Schnick
 */
@Getter
@Setter
public class ApiValidationExceptionModel extends ApiExceptionModel {

    private List<String> errors;

    public ApiValidationExceptionModel(String message, int status, String details, List<String> errors) {
        super(message, status, details);
        this.errors = errors;
    }
}
