package com.yelensoft.artishop_backend.configuration;

import com.yelensoft.artishop_backend.services.DataInitializationService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class DefaultDataInitializer implements ApplicationRunner {
    private DataInitializationService dataInitializationService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        dataInitializationService.createDefaultRole();
    }
}
