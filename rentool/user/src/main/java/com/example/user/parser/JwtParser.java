package com.example.user.parser;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class JwtParser {
    public UUID getIdFromToken(Jwt jwt) {
        return UUID.fromString(jwt.getClaim(JwtClaimNames.SUB));
    }
}
