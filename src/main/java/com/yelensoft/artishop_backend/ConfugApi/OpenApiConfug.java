package com.yelensoft.artishop_backend.ConfugApi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class OpenApiConfug {
    @Bean
    public OpenAPI quizOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("tpApi_artEshop")
                        .description("API  Art_e_Shop pour les artisans locaux")
                        .version("1.0"));
    }
}
