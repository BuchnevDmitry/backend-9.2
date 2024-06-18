package com.edu.tool.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
    private String openidConnectUrl;

    public String getOpenidConnectUrl() {
        return openidConnectUrl;
    }

    public void setOpenidConnectUrl(String openidConnectUrl) {
        this.openidConnectUrl = openidConnectUrl;
    }
}
