package com.mclabs.securities.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
public class CustomAuthorizationFilter  extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().equals("/api/login") ||
                request.getServletPath().equals("/api/token/refresh") ||
                request.getServletPath().equals("/api/confirmAccount") ||
                request.getServletPath().equals("/api/password")

        ) {
            filterChain.doFilter(request, response);
        } else {
            String authHeader = request.getHeader(AUTHORIZATION);
            try {
                if(authHeader != null && authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    Arrays.stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority((role)));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } else {
                    filterChain.doFilter(request, response);
                }
            } catch(Exception ex) {
                log.error("error in loggin {}", ex.getMessage());
                response.setHeader("error", ex.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("message", ex.getMessage());
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        }
    }
}
