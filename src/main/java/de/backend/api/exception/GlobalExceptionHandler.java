package de.backend.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

/**
 * Global Exception Handler
 *
 * @author Raphael Schnick
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle Global API Exceptions
     *
     * @param exception Thrown Exception
     * @param request   Requested API Web Request
     * @return ResponseEntity with Exception Modal and Status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalExceptionHandling(Exception exception, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        var exceptionStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        if (exceptionStatus != null) {
            status = exceptionStatus.value();
        }
        ApiExceptionModel exceptionModel =
                new ApiExceptionModel(exception.getMessage(), status.value(), request.getDescription(false));
        return new ResponseEntity<>(exceptionModel, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ArrayList<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.add(fieldName + " " + message);
        });
        return new ResponseEntity<>(new ApiValidationExceptionModel("Validation Error", httpStatus.value(), request.getDescription(false), errors), HttpStatus.BAD_REQUEST);
    }
}
