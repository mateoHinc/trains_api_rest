package com.company.trains_api_rest.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Este filtro permite leer el header Authorization
 * Extrae el token
 * Oobtiene el email del token
 * Valida el token
 * Autentica al usuario en Spring Security
*/

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    
    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /*
        Intercepta cada request para validar el token JWT
    */
   @Override
   protected void doFilterInternal(HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
   ) throws ServletException, IOException {

    // Obtener header Authorization
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;

    // Si no existe header o no empieza con Bearer, continuar sin autenticar
    if(authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }

    // Extraer token quitando el prefijo "Bearer "
    jwt = authHeader.substring(7);

    // Extraer email desde el token
    userEmail = jwtService.extractUsername(jwt);

    // Solo autenticar si aún no hay usuario autenticado
    if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

        // Validar token contra el usuario cargado
        if(jwtService.isTokenValid(jwt, userDetails.getUsername())){
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEmail, null, userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Guardar autenticación en el contexto Spring
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    filterChain.doFilter(request, response);
   }

}
