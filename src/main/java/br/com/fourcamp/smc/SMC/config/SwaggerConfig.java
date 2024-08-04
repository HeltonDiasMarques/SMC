package br.com.fourcamp.smc.SMC.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Swagger for API documentation.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates a custom OpenAPI bean with API information and security configuration.
     *
     * @return an OpenAPI instance with the specified configuration
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SMC API")
                        .version("1.0")
                        .description("API documentation for SMC system"))
                .addSecurityItem(new SecurityRequirement().addList("basicScheme"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("basicScheme",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")));
    }
}