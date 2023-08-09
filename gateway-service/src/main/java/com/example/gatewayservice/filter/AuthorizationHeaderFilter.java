package com.example.gatewayservice.filter;

import com.example.gatewayservice.filter.AuthorizationHeaderFilter.Config;
import com.example.gatewayservice.jwt.JwtTokenProvider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<Config> {

    private final String userSecret;
    private final String brandSecret;
    private final String adminSecret;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthorizationHeaderFilter(@Value("${token.secret}") String userSecret,
                                    @Value("${brand.secret}") String brandSecret,
                                    @Value("${admin.secret}") String adminSecret,
                                    JwtTokenProvider jwtTokenProvider) {
        super(Config.class);
        this.userSecret = userSecret;
        this.brandSecret = brandSecret;
        this.adminSecret = adminSecret;
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

            String secret = null;

            if (config.isHasUserRole() && jwtTokenProvider.isJwtValid(jwt, userSecret)) {
                secret = userSecret;
            } else if (config.isHasBrandRole() && jwtTokenProvider.isJwtValid(jwt, brandSecret)) {
                secret = brandSecret;
            } else if (config.isHasAdminRole() && jwtTokenProvider.isJwtValid(jwt, adminSecret)) {
                secret = adminSecret;
            }

            if (secret == null) {
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

    @Data
    public static class Config {
        private boolean hasUserRole;
        private boolean hasBrandRole;
        private boolean hasAdminRole;
    }

}
