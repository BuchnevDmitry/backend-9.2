package com.example.user.service;

import com.example.user.Credentials;
import com.example.user.api.model.UserRequest;
import com.example.user.configuration.KeycloakProperties;
import com.example.user.model.User;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;
    private final KeycloakProperties keycloakProperties;

    public User registrationUser(UserRequest userRequest) {
        CredentialRepresentation credential = Credentials.createPasswordCredentials(userRequest.password());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userRequest.login());
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);
        UsersResource instance = getInstance();
        Response response = instance.create(user);

        log.info("Response status: {}", response.getStatus());
        log.info("Response: {}", response);

        if (response.getStatus() != 201) {
            String error = response.readEntity(String.class);
            log.error("Failed to create user. Status: {}, Error: {}", response.getStatus(), error);
            throw new RuntimeException("Failed to create user: " + error);
        }

        String location = response.getHeaderString("Location");
        if (location == null || location.isEmpty()) {
            throw new RuntimeException("Failed to get user ID from response");
        }
        String userId = location.substring(location.lastIndexOf('/') + 1);
        log.info("User created with ID: {}", userId);

        User userRegistration = new User();
        userRegistration.setLogin(user.getUsername());
        userRegistration.setId(UUID.fromString(userId));
        userRegistration.setPhone(userRequest.phone());
        userRegistration.setEmail(user.getEmail());
        userRegistration.setFirstName(userRequest.firstName());
        userRegistration.setLastName(userRequest.lastName());
        return userRegistration;
    }

    public UsersResource getInstance(){
        return keycloak.realm(keycloakProperties.realm()).users();
    }
}
