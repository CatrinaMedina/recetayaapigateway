
package com.java.recetayaapigateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtUtils jwtUtils) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    HttpMethod.POST, "/usuarios/login"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.OPTIONS, "/**"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.POST, "/recetas"
                ).hasRole("MEDICO")

                .requestMatchers(
                    HttpMethod.PUT, "/recetas/**"
                ).hasRole("MEDICO")

                .requestMatchers(
                    HttpMethod.DELETE, "/recetas/**"
                ).hasRole("MEDICO")

                .requestMatchers(
                    HttpMethod.GET, "/recetas", "/recetas/**"
                ).hasAnyRole("MEDICO", "FARMACEUTICO")

                .requestMatchers(
                    "/dispensaciones", "/dispensaciones/**"
                ).hasRole("FARMACEUTICO")

                .requestMatchers(
                    HttpMethod.GET,
                    "/inventario", "/inventario/**",
                    "/medicamentos", "/medicamentos/**",
                    "/sucursales", "/sucursales/**"
                ).hasAnyRole("MEDICO", "FARMACEUTICO")

                .requestMatchers(
                    "/inventario", "/inventario/**",
                    "/medicamentos", "/medicamentos/**",
                    "/sucursales", "/sucursales/**"
                ).hasRole("FARMACEUTICO")

                .requestMatchers(
                    "/usuarios", "/usuarios/**",
                    "/roles", "/roles/**"
                ).authenticated()

                .anyRequest().denyAll()
            )

            .addFilterBefore(
                new JwtAuthFilter(jwtUtils),
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
