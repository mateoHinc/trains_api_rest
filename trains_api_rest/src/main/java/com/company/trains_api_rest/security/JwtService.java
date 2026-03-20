package com.company.trains_api_rest.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    /*La clave secreta en Base64
    En producción esto debe ir en application.properties o variables de entorno
     */
    private static final String SECRET_KEY = "MTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIxMjM0NTY3ODkwMTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMg==";

    /*Generar un token JWT con email y rol*/
    public String generateToken(String email, String role) {
        return Jwts.builder().setSubject(email)
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /*Extrae el email del token */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /*Extrae el rol del token */
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    /*Verifica si el token corresponde al usuario y no está expirado */
    public boolean isTokenValid(String token, String email) {
        final String username = extractUsername(token);
        return username.equals(email) && !isTokenExpired(token);
    }

    /*Verifica si el token expiró */
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    /*Extrae todos los claims del token */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /*Construye la clave de firma */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
