package com.kamilwnek.lift_management.security;

import com.google.common.net.HttpHeaders;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

    private String secretKey;
    private String tokenPrefix;
    private Integer acceptTokenExpiration;
    private Integer refreshTokenExpiration;


    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
