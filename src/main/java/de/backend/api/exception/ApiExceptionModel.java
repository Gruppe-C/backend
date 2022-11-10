package de.backend.api.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;


/**
 * Api Exception Message Model
 *
 * @author Raphael Schnick
 */
@Getter
@Setter
public class ApiExceptionModel {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Timestamp timestamp;

    private String message;

    private int status;

    private String details;

    public ApiExceptionModel(String message, int status, String details) {
        this.timestamp = new Timestamp(new Date().getTime());
        this.message = message;
        this.status = status;
        this.details = details;
    }
}
