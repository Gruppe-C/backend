package de.backend.features.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Schema
public class LoginRequest {

	@NotBlank
	@Schema(description = "Username", example = "admin")
	private String username;

	@NotBlank
	@Schema(description = "Password", example = "nimda")
	private String password;
}
