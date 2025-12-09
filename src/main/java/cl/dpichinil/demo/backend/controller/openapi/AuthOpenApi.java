package cl.dpichinil.demo.backend.controller.openapi;

import org.springframework.http.ResponseEntity;

import cl.dpichinil.demo.backend.dto.ResponseDto;
import cl.dpichinil.demo.backend.dto.UserDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

public interface AuthOpenApi {

    @Operation(
        summary = "Authentica un usuario y Genera un nuevo JWT para usarlo en los demas endpoints de la aplicacion",
        description = "Valida usuario y contraseña del usuario y retorna un JWT como resultado correcto o Codigos de error."
    )
    @SecurityRequirement(name = "bearerAuth") // 1. Aplica la seguridad JWT definida en OpenApiConfig
    @ApiResponse(
        responseCode = "201",
        description = "Login Successful",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = " {\n" + //
                            "    \"status\": true,\n" + //
                            "    \"message\": \"Login successful\",\n" + //
                            "    \"data\": {\n" + //
                            "        \"accessToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkcGljaGluaWwiLCJpYXQiOjE3NjQ3MjEyMjIsImV4cCI6MTc2NTMyNjAyMn0.ySs7lTxKrC3Z-IYMlKdJ_225Y67jCqJ6rxXlwpCNEUM\",\n" + //
                            "        \"tokenType\": \"Bearer\"\n" + //
                            "    }\n" + //
                            "}"
            )
        )
    )
    @ApiResponse(
        responseCode = "500", 
        description = "Internal server error", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n" + //
                           "    \"status\": false,\n" + //
                           "    \"message\": \"Internal server error\",\n" + //
                           "    \"data\": null\n" + //
                           "}"
                            )
            )
        )
    @ApiResponse(
        responseCode = "401", 
        description = "Unautorized - Invalid credentials", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n" + //
                           "    \"status\": false,\n" + //
                           "    \"message\": \"Invalid username or password\",\n" + //
                           "    \"data\": null\n" + //
                           "}"
            )
        )
    )
    ResponseEntity<ResponseDto> login(
        @RequestBody(
            description = "Credenciales del usuario para la autenticación.",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserDto.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Ejemplo de credenciales",
                    value = "{\n" +
                            "  \"username\": \"testuser\",\n" +
                            "  \"password\": \"password123\"\n" +
                            "}"
                )
            )
        ) UserDto userDto
    );
}
