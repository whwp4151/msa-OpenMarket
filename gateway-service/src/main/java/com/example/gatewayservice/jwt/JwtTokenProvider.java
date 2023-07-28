package com.example.gatewayservice.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    public boolean isJwtValid(String jwt, String secret) {
        boolean returnValue = true;

        String subject = null;

        try {
            subject = Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(jwt).getBody()
                .getSubject();
        } catch (Exception e) {
            returnValue = false;
        }

        if (StringUtils.isEmpty(subject)) {
            returnValue = false;
        }

        return returnValue;
    }

    public String getUserId(String token, String secret) {
        return getClaimsFromJwtToken(token, secret).getSubject();
    }

    public String getRole(String token, String secret) {
        return (String) getClaimsFromJwtToken(token, secret).get("roles");
    }

    private Claims getClaimsFromJwtToken(String token, String secret) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
