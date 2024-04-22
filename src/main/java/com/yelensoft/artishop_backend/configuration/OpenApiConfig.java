package com.yelensoft.artishop_backend.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {
    @Bean
    public OpenAPI artShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Art Shop")
                        .description("API pour artshop")
                        .version("1.0"));
    }
}
