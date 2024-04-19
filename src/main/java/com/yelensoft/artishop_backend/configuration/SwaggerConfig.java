package com.yelensoft.artishop_backend.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {
    @Bean
    public OpenAPI productOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("SERVICE USER")
                        .description(
                                "Service charger de gérer les services qui seront fourni à l'utilisateur"
                        )
                        .version("1.0.0"));
    }
}
