package com.edu.tool;

import com.edu.tool.configuration.ApplicationConfig;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class})
@SecurityScheme(
    name = "Keycloak",
    openIdConnectUrl = "http://localhost:8180/realms/rentool/.well-known/openid-configuration",
    scheme = "bearer",
    type = SecuritySchemeType.OPENIDCONNECT,
    in = SecuritySchemeIn.HEADER
)
public class ToolApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToolApplication.class, args);
    }
}
