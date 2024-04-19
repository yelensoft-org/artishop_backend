package com.yelensoft.artishop_backend.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {
    @Bean
    public OpenAPI artEshopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Art EShop")
                        .description("API pour arteshop")
                        .version("1.0"));
    }
}
