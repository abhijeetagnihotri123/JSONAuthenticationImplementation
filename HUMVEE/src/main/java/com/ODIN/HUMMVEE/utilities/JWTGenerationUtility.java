package com.ODIN.HUMMVEE.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTGenerationUtility {

    private final String SECRET = "my-super-secret-string-key-that-is-long-enough-for-encryption-123456789-!@#$%^&*(-bits-long";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    private final long EXPIRATION_TIME = 1000*60*60;
    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key , SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String jwtToken) {

        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

    }

    public String extractUserName(String token) {

       Claims body = extractClaims(token);
       return body.getSubject();

    }

    public boolean validateToken(String usernameFromJWT, UserDetails userDetails , String jwtToken) {

        boolean condition1 = usernameFromJWT.equals(userDetails.getUsername());
        boolean condition2 = !(jwtTokenValid(jwtToken));

        return (condition1 && condition2);
    }

    public boolean jwtTokenValid(String jwtToken) {

        Claims body = extractClaims(jwtToken);
        return body.getExpiration().before(new Date());

    }

}
