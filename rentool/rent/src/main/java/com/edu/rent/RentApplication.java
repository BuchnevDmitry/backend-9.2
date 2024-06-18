package com.edu.rent;

import com.edu.rent.configuration.ApplicationConfig;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@EnableConfigurationProperties({ApplicationConfig.class})
@SecurityScheme(
    name = "Keycloak",
    openIdConnectUrl = "${keycloak.openid-connect-url}",
    scheme = "bearer",
    type = SecuritySchemeType.OPENIDCONNECT,
    in = SecuritySchemeIn.HEADER
)
public class RentApplication {
    public static void main(String[] args) {
        SpringApplication.run(RentApplication.class, args);
    }
}
