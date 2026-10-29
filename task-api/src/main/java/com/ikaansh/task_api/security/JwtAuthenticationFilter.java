
package com.ikaansh.task_api.security;

import com.ikaansh.task_api.entity.AppUser;
import com.ikaansh.task_api.repository.AppUserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String username = jwtService.extractUsername(token);

                if (username != null
                        && SecurityContextHolder.getContext()
                        .getAuthentication() == null
                        && jwtService.isTokenValid(token, username)) {

                    AppUser user = appUserRepository
                            .findByUsername(username)
                            .orElse(null);

                    if (user != null && user.getRole() != null) {
                        String role = user.getRole().trim().toUpperCase();

                        if (role.startsWith("ROLE_")) {
                            role = role.substring(5);
                        }

                        if (role.equals("USER") || role.equals("ADMIN")) {
                            UsernamePasswordAuthenticationToken authentication =
                                    new UsernamePasswordAuthenticationToken(
                                            username,
                                            null,
                                            List.of(
                                                    new SimpleGrantedAuthority(
                                                            "ROLE_" + role
                                                    )
                                            )
                                    );

                            authentication.setDetails(
                                    new WebAuthenticationDetailsSource()
                                            .buildDetails(request));

                            SecurityContextHolder.getContext()
                                    .setAuthentication(authentication);
                        }
                    }
                }
            } catch (RuntimeException ex) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
