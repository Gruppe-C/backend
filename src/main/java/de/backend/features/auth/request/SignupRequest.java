package de.backend.features.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Schema
public class SignupRequest {
    @NotBlank
    @Schema(description = "Username", example = "admin")
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    @Schema(description = "Password", example = "nimda")
    private String password;
}
