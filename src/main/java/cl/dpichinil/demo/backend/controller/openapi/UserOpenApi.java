package cl.dpichinil.demo.backend.controller.openapi;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.web.bind.annotation.PathVariable;

import cl.dpichinil.demo.backend.dto.ResponseDto;
import cl.dpichinil.demo.backend.dto.ResponsePageDto;
import cl.dpichinil.demo.backend.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

public interface UserOpenApi {

    /**
     * --------------------------------------------------------------------------
     * Obtiene un usuario por su ID
     * --------------------------------------------------------------------------
     * 
     * @param id
     * @return
     */

    @Operation(
        summary = "Obtiene un usuario por su ID",
        description = "Busca y devuelve los datos de un usuario específico basado en su ID."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(
        responseCode = "200",
        description = "Usuario encontrado exitosamente.",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n" +
                    "\t\"status\": true,\n" +
                    "\t\"message\": \"User found\",\n"+
                    "\t\"data\": {\n" +
                        "\t\t\"id\": 1,\n" +
                        "\t\t\"username\": \"johndoe\",\n" +
                        "\t\t\"password\": \"hashedpassword\",\n" +
                        "\t\t\"active\": true\n" +
                        "\t}" +
                    "\n} "
            )
        )
    )
    @ApiResponse(
        responseCode = "400",
        description = "ID de usuario inválido.",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Invalid user ID\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "401",
        description = "No autorizado. Se requiere un token válido.",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Acceso denegado. Se requiere una autenticación válida.\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "404",
        description = "Usuario no encontrado.",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"User not found\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Internal server error\",\n\t\"data\": null\n}"
            )
        )
    )
    public ResponseEntity<ResponseDto> getById(
        @Parameter(
            description = "ID numérico del usuario a buscar.",
            required = true,
            example = "1"
        ) 
        @PathVariable("id") 
        Integer id
    );

    /**
     * --------------------------------------------------------------------------
     * Actualiza un usuario existente
     * --------------------------------------------------------------------------
     *
     * @param id
     * @param userDto
     * @return
     */
    @Operation(
        summary = "Actualiza un usuario existente",
        description = "Recibe los datos para actualizar un usuario existente y los guarda en la base de datos."
    )
    @SecurityRequirement(name = "bearerAuth") // 1. Aplica la seguridad JWT definida en OpenApiConfig
    @ApiResponse(
        responseCode = "200",
        description = "Usuario actualizado exitosamente",
        content = @Content( 
            mediaType = "application/json", 
            schema = @Schema(
                //TODO revisar data de este endpoint puede que devuelba el usuario actualizado
                example = "{\n\t\"status\": false,\n\t\"message\": \"user updated successfully\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "400", 
        description = "Bad Request - Invalid user data", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Invalid user data\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "404", 
        description = "User not found", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"User not found\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "500", 
        description = "Internal server error", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Internal server error\",\n\t\"data\": null\n}"
            )
        )
    )
    public ResponseEntity<ResponseDto> update(
        @Parameter(
            description = "ID numérico del usuario a actualizar.",
            required = true,
            example = "1"
        )
        @PathVariable("id") 
        Integer id, 
        @RequestBody(
            description = "Datos del usuario a actualizar",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserDto.class),
                examples = @ExampleObject(
                    name = "Ejemplo de usuario a actualizar",
                    value = "{\n" + //
                                "\t\"id\": 1,\n" + //            
                                "\t\"username\": \"updateduser\",\n" + //
                                "\t\"password\": \"newsecurepassword\",\n" + //
                                "\t\"active\": false\n"+
                            "}"
                )
            )
        ) 
        UserDto userDto
    );

    /**
     * --------------------------------------------------------------------------
     * Elimina un usuario por su ID
     * --------------------------------------------------------------------------
     * 
     * @param id
     * @return
     */
    @Operation(
        summary = "Elimina un usuario por su ID",
        description = "Elimina un usuario específico de la base de datos basado en su ID."
    )
    @SecurityRequirement(name = "bearerAuth") // 1. Aplica la seguridad JWT definida en OpenApiConfig
    @ApiResponse(
        responseCode = "200",
        description = "Usuario eliminado exitosamente",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": true,\n\t\"message\": \"User deleted successfully\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "400", 
        description = "Bad Request - Invalid user ID", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Invalid user ID\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "404", 
        description = "Usuario no encontrado", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"User not found\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "500", 
        description = "Error interno del servidor", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Internal server error\",\n\t\"data\": null\n}"
            )
        )
    )
    public ResponseEntity<ResponseDto> delete(
        @Parameter(
            description = "ID numérico del usuario a eliminar.",
            required = true,
            example = "1"
        )
        @PathVariable("id")
        Integer id
    );

    /**
     * --------------------------------------------------------------------------
     * Obtiene todos los usuarios
     * --------------------------------------------------------------------------
     * 
     * @return
     */
    @Operation(
        summary = "Obtiene todos los usuarios",
        description = "Recupera una lista completa de todos los usuarios almacenados en la base de datos."
    )
    @SecurityRequirement(name = "bearerAuth") // 1. Aplica la seguridad JWT definida en OpenApiConfig
    @ApiResponse(
        responseCode = "200",
        description = "Lista de usuarios obtenida correctamente",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = " {\n" + //   
                            "\t\"status\": true,\n" + //
                            "\t\"message\": \"Login successful\",\n" + //
                            "\t\"data\": [" +
                                "\t{\n" + //
                                    "\t\t\"id\": 1,\n" + //
                                    "\t\t\"username\": \"johndoe\",\n" + //
                                    "\t\t\"password\": \"hashedpassword\",\n" + //
                                    "\t\t\"active\": true\n" + //                     
                                "\t},\n" + //
                                "\t{\n" + //
                                    "\t\t\"id\": 2,\n" + //
                                    "\t\t\"username\": \"janedoe2\",\n" + //
                                    "\t\t\"password\": \"hashedpassword2\",\n" + //
                                    "\t\t\"active\": false\n" + //
                                "\t}\n" + //
                            "\t]\n" + //
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
                example = "{\n\t\"status\": false,\n\t\"message\": \"Acceso denegado. Se requiere una autenticación válida  .\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "500", 
        description = "Internal server error", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Internal server error\",\n\t\"data\": null\n}"
            )
        )
    )
    public ResponseEntity<ResponseDto> getAll();

    /**
     * --------------------------------------------------------------------------
     * Guarda un nuevo usuario
     * --------------------------------------------------------------------------
     * @param userDto
     * @return
     */
    @Operation(
        summary = "Guarda un nuevo usuario",
        description = "Recibe los datos de un nuevo usuario y los almacena en la base de datos."
    )
    @SecurityRequirement(name = "bearerAuth") // 1. Aplica la seguridad JWT definida en OpenApiConfig
    @ApiResponse(
        responseCode = "201",
        description = "Usuario creado exitosamente",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"user created successfully\",\n\t\"data\": 1\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "400", 
        description = "Bad Request - Invalid user data", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Invalid user data\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "401", 
        description = "Unautorized - Invalid credentials", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Acceso denegado. Se requiere una autenticación válida.\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "409", 
        description = "Conflict - Username already exists", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Username already exists\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "500", 
        description = "Internal server error", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Internal server error\",\n\t\"data\": null\n}"
            )
        )
    )
    public ResponseEntity<ResponseDto> save(
        @RequestBody(
            description = "Datos del usuario a crear",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserDto.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Ejemplo de usuario a crear",
                    value = "{\n" + //
                                "\t\"id\": 0,\n" + //            
                                "\t\"username\": \"newuser\",\n" + //
                                "\t\"password\": \"securepassword\",\n" + //
                                "\t\"active\": true\n"+
                            "}"
                )
            )                    
        ) UserDto userDto
    );


    /**
     * --------------------------------------------------------------------------
     * Cambia la contraseña de un usuario
     * --------------------------------------------------------------------------
     * 
     * @param id
     * @param userDto
     * @return
     */
    @Operation(
        summary = "Cambia la contraseña de un usuario",
        description = "Actualiza la contraseña de un usuario específico."
    )
    @SecurityRequirement(name = "bearerAuth") // 1. Aplica la seguridad JWT definida en OpenApiConfig
    @ApiResponse(
        responseCode = "200",
        description = "Contraseña cambiada exitosamente",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Password changed successfully\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "400", 
        description = "Bad Request - Invalid user ID or password", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(implementation = ResponseDto.class),
            examples = {
                @ExampleObject (
                    name = "Invalid User ID",
                    summary = "El ID del usuario proporcionado no es válido.",
                    value = "{\n\t\"status\": false,\n\t\"message\": \"Invalid user ID\",\n\t\"data\": null\n}"
                ),
                @ExampleObject (
                    name = "Invalid User Password",
                    summary = "El Password del usuario proporcionado no es válido.",
                    value = "{\n\t\"status\": false,\n\t\"message\": \"Invalid user password\",\n\t\"data\": null\n}"
                )
            }
        )
    )
    @ApiResponse(
        responseCode = "404", 
        description = "User not found", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"User not found\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "500", 
        description = "Internal server error", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Internal server error\",\n\t\"data\": null\n}"
            )
        )
    )
    public ResponseEntity<ResponseDto> changePassword(Integer id, UserDto userDto);


    /**
     * --------------------------------------------------------------------------
     * Resetea la contraseña de un usuario
     * --------------------------------------------------------------------------
     * 
     * @param id
    
     * @param userDto
     * @return
     */
    @Operation(
        summary = "Resetea la contraseña de un usuario",
        description = "Permite resetear la contraseña de un usuario."
    )
    @SecurityRequirement(name = "bearerAuth") // 1. Aplica la seguridad JWT definida en OpenApiConfig
    @ApiResponse(
        responseCode = "200",
        description = "Contraseña reseteada exitosamente",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Password reset successfully\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "400", 
        description = "Bad Request - Invalid username user", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Invalid username user \",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "404", 
        description = "User not found", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"User not found\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "500", 
        description = "Internal server error", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Internal server error\",\n\t\"data\": null\n}"
            )
        )
    )
    public ResponseEntity<ResponseDto> resetPassword(UserDto userDto);




    

    
    /**
     * --------------------------------------------------------------------------
     * Obtiene un usuario por su nombre de usuario
     * --------------------------------------------------------------------------
     * 
     * @param username
     * @return
     */
    @Operation(
        summary = "Obtiene un usuario por su nombre de usuario",
        description = "Busca y devuelve los datos de un usuario específico basado en su nombre de usuario."
    )
    @SecurityRequirement(name = "bearerAuth") // 1. Aplica la seguridad JWT definida en OpenApiConfig
    @ApiResponse(
        responseCode = "200",
        description = "Usuario encontrado exitosamente.",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n" +
                    "\t\"status\": true,\n" +
                    "\t\"message\": \"User found\",\n"+
                    "\t\"data\": {\n" +
                        "\t\t\"id\": 1,\n" +
                        "\t\t\"username\": \"johndoe\",\n" +
                        "\t\t\"password\": \"hashedpassword\",\n" +
                        "\t\t\"active\": true\n" +
                        "\t}" +
                    "\n} "
            )
        )
    )
    @ApiResponse(
        responseCode = "400",
        description = "Nombre de usuario inválido.",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Invalid username\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "401",
        description = "No autorizado. Se requiere un token válido.",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Acceso denegado. Se requiere una autenticación válida.\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "404",
        description = "Usuario no encontrado.",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"User not found\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Internal server error\",\n\t\"data\": null\n}"
            )
        )
    )
    public ResponseEntity<ResponseDto> getByUsername(
        @Parameter(
            description = "Nombre de usuario del usuario a buscar.",
            required = true,
            example = "johndoe"
        )
        @PathVariable("username") 
        String username
    );
    
    
    /**
     * --------------------------------------------------------------------------
     * Obtiene una lista paginada de usuarios
     * --------------------------------------------------------------------------
     * 
     * @param page
     * @param size
     * @return
     */
    @Operation(
        summary = "Envia una lista paginada de usuarios",
        description = "Genera una lista paginada de usuarios desde la base de datos."
    )
    @SecurityRequirement(name = "bearerAuth") // 1. Aplica la seguridad JWT definida en OpenApiConfig
    @ApiResponse(
        responseCode = "200",
        description = "Lista de usuarios obtenida correctamente",
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(implementation = ResponsePageDto.class),
            examples = @ExampleObject(
                name = "Ejemplo de respuesta",
                value = "{\n" + //
                            "\"size\": 10,\n" + //
                            "\"number\": 0,\n" + //
                            "\"totalElements\": 2,\n" + //
                            "\"totalPages\": 1,\n" + //
                            "\"content\": [\n" + //
                            "{\n" + //
                            "    \"id\": 1,\n" + //
                            "    \"username\": \"johndoe\",\n" + //
                            "    \"password\": \"hashedpassword\",\n" + //
                            "    \"active\": true\n" + //
                            "},\n" + //
                            "{\n" + //
                            "    \"id\": 2,\n" + //
                            "    \"username\": \"janedoe2\",\n" + //
                            "    \"password\": \"hashedpassword2\",\n" + //
                            "    \"active\": false\n" + // 
                            "}\n" + //
                            "]\n" + //
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
                example = "{\n\t\"status\": false,\n\t\"message\": \"Acceso denegado. Se requiere una autenticación válida.\",\n\t\"data\": null\n}"
            )
        )
    )
    @ApiResponse(
        responseCode = "500", 
        description = "Internal server error", 
        content = @Content(
            mediaType = "application/json", 
            schema = @Schema(
                example = "{\n\t\"status\": false,\n\t\"message\": \"Internal server error\",\n\t\"data\": null\n}"
            )
        )
    )
    public ResponseEntity<ResponseDto> getAllPaginated(
        @Parameter(
            description = "Número de página a solicitar",
            in = ParameterIn.QUERY,
            schema = @Schema(type = "integer", defaultValue = "0")
        )
        Integer page, 
        
        @Parameter(
            description = "Número de elementos por página",
            in = ParameterIn.QUERY,
            schema = @Schema(type = "integer", defaultValue = "10")
        )
        Integer size
    );
    
    


}
