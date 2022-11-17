package de.backend.features.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema
public class LoginResponse {

   @Schema(description = "JWT Access Token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqamRlZ2VuZXIiLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sImlhdCI6MTY2NTkxNjU3MCwiZXhwIjoxNjY2MDAyOTcwfQ.d5n6hi6tmOHChjNdwK3MJnf2sBId-e7-B2FnH1eJN0QoWwOiZ5LJLscFDu9oyfilrVtfX42rU6l3L8lGqpDcKw")
   private String accessToken;

   @Schema(description = "Token type", example = "Bearer")
   private String tokenType = "Bearer";

   public LoginResponse(String accessToken) {
      this.accessToken = accessToken;
   }
}
