package com.gomistar.proyecto_gomistar.configuration.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;
    
    public String generateAccesToken(String userName) {
        return Jwts.builder()
                            .setSubject(userName)
                            .setIssuedAt(new Date(System.currentTimeMillis()))
                            .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                            .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                            .compact();
    }

    private Key getSignatureKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(getSignatureKey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
            return true;                
        }
        catch (Exception e) {
            System.out.println("Token invalido, error: " + e.getMessage());
            return false;
        }
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parser().setSigningKey(getSignatureKey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsFunction) {
        
        Claims claims = extractAllClaims(token);
        return claimsFunction.apply(claims);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }


}