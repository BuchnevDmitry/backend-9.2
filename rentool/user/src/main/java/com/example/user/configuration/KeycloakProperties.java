package com.example.user.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak", ignoreUnknownFields = false)
public record KeycloakProperties(
    String url,
    String realm,
    String clientId,
    String clientSecret,
    String username,
    String password
) {
}
