package cl.dpichinil.demo.backend.config.openapi;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Usuarios y authenticación (Spring Boot 3.2.5)",
        version = "1.0.0",
        description = "Documentación de la API REST para el aplicativo de Usuarios y authenticacion.",
        contact = @Contact(
            name = "Soporte Técnico",
            email = "soporte@empresa.com"
        )
    )
)
@SecurityScheme(
    // Configuración para permitir ingresar el token JWT en Swagger UI
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class OpenApiConfig {
    /**
     * Define un grupo de API para la documentación de OpenAPI.
     * Esto permite filtrar los endpoints que se mostrarán en la documentación,
     * en este caso, limitándolos a los controladores dentro del paquete 'cl.dpichinil.demo.backend.controller'.
     * @return Un objeto GroupedOpenApi configurado.
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("demo-backend-spring-boot") // Nombre del grupo que aparecerá en la UI de Swagger
                // Apuntamos específicamente al paquete que contiene los controladores para la documentación pública.
                .packagesToScan("cl.dpichinil.demo.backend.controller")
                .build();
    }
}
