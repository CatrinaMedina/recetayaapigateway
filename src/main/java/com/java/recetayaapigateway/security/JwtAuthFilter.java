
package com.java.recetayaapigateway.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtAuthFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.equals("/usuarios/login")
                || request.getMethod().equalsIgnoreCase("OPTIONS")) {

            filterChain.doFilter(request, response);
            return;
        }

        String authHeader =
                request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Token no enviado");

            return;
        }

        String token = authHeader.substring(7);

        if (!jwtUtils.validateToken(token)) {

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Token invalido o expirado");

            return;
        }

        String correo = jwtUtils.getCorreo(token);
        String rol = jwtUtils.getRol(token);

        if (correo == null || correo.isBlank()
                || rol == null || rol.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Token sin datos validos");

            return;
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        correo,
                        null,
                        List.of(new SimpleGrantedAuthority(
                                "ROLE_" + rol.toUpperCase(Locale.ROOT)))
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
