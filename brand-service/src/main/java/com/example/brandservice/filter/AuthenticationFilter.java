package com.example.brandservice.filter;

import com.example.brandservice.dto.BrandAccountDto;
import com.example.brandservice.dto.SigninDto;
import com.example.brandservice.service.BrandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final BrandService brandService;
    private final Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager, BrandService brandService, Environment environment) {
        super(authenticationManager);
        this.brandService = brandService;
        this.env = environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            SigninDto signinDto = new ObjectMapper().readValue(request.getInputStream(), SigninDto.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    signinDto.getLoginId(),
                    signinDto.getPassword(),
                    Collections.emptyList()
                )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((User) authResult.getPrincipal()).getUsername();
        BrandAccountDto userDetails = brandService.getBrandAccount(username);

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", "ROLE_BRAND");

        String token = Jwts.builder()
            .setSubject(userDetails.getLoginId())
            .addClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("brand.expiration_time"))))
            .signWith(SignatureAlgorithm.HS512, env.getProperty("brand.secret"))
            .compact();

        response.addHeader("token", token);
        response.addHeader("loginId", userDetails.getLoginId());
    }
}
