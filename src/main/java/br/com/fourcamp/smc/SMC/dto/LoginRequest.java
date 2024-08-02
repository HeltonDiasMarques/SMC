package br.com.fourcamp.smc.SMC.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    @Schema(description = "ID of the user", example = "U123")
    private String id;

    @Schema(description = "Email of the user", example = "user@example.com")
    private String email;

    @Schema(description = "Password of the user", example = "password123")
    private String password;
}
