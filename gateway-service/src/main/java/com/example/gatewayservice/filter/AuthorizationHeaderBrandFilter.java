package com.example.gatewayservice.filter;

import com.example.gatewayservice.filter.AuthorizationHeaderBrandFilter.Config;
import com.example.gatewayservice.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationHeaderBrandFilter extends AbstractGatewayFilterFactory<Config> {

    private final String secret;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthorizationHeaderBrandFilter(@Value("${brand.secret}") String secret, JwtTokenProvider jwtTokenProvider) {
        super(Config.class);
        this.secret = secret;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");

            if (!jwtTokenProvider.isJwtValid(jwt, secret)) {
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }

            String subject = jwtTokenProvider.getUserId(jwt, secret);
            String role = jwtTokenProvider.getRole(jwt, secret);

            ServerHttpRequest newRequest = request.mutate()
                .header("user_id", subject)
                .header("role", role)
                .build();

            return chain.filter(exchange.mutate().request(newRequest).build());
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();
    }

    public static class Config {

    }

}
