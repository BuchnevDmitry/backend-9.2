package com.example.user.configuration;

import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

    private final KeycloakProperties keycloakProperties;

    @Bean
    public Keycloak getInstance(){
        return KeycloakBuilder.builder()
            .serverUrl(keycloakProperties.url())
            .realm(keycloakProperties.realm())
            .grantType(OAuth2Constants.PASSWORD)
            .username(keycloakProperties.username())
            .password(keycloakProperties.password())
            .clientId(keycloakProperties.clientId())
            .clientSecret(keycloakProperties.clientSecret())
            .resteasyClient(new ResteasyClientBuilder()
                .connectionPoolSize(10)
                .build()
            )
            .build();
    }
}
