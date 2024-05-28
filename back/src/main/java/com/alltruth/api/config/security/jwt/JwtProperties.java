package com.alltruth.api.config.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("jwt")
@Component
public class JwtProperties {
    private String issuer;
    private String secretkey;
    private Integer accessTokenExpiry;
}
