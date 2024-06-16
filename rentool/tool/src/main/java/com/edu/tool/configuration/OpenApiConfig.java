package com.edu.tool.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(ApplicationConfig applicationConfig) {
        return new OpenAPI()
            .info(new Info().title("Your API Title")
                .version("1.0")
                .description("API description"))
            .addServersItem(new Server().url(applicationConfig.baseSwaggerUrl()));
    }
}
