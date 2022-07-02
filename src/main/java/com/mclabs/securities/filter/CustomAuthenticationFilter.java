package com.mclabs.securities.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    // generate token logic here
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // grab user
        User user = (User) authResult.getPrincipal();

        // don't use "secret" in prod, store some where else secure, encrypt and decrypt by utility class
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));

        // access_token: for 10 min exp. time
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 100 * 60 * 100))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        // refresh_token: for 30 min exp time
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // usually useful when brut force attack, exceed the time to login
        log.error("!! unsuccessfulAuthentication = {}!!", failed.getMessage());
        response.setHeader("error", failed.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, String> error = new HashMap<>();
        error.put("message", failed.getMessage());
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username  = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("username is {} and password is {}", username, password);
        UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
        // grabbing the username and password
        // authenticate user using authManager
        // after success, call successfulAuthentication
    }
}
