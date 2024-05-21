package com.edu.rent.parser;

import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@Slf4j
public class JwtParser {

    public UUID getIdFromAccessToken(String token) {
        try {
            token = token.substring(7);
            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setSkipSignatureVerification()
                .setExpectedAudience("account")
                .build();
            jwtConsumer.setSkipVerificationKeyResolutionOnNone(true);
            JwtClaims jwtClaims = jwtConsumer.processToClaims(token);

            return UUID.fromString(jwtClaims.getStringClaimValue("sub"));
        } catch (InvalidJwtException | MalformedClaimException e) {
            log.error(e.getMessage());
            throw new RuntimeException("jwt not valid");
        }
    }
}
