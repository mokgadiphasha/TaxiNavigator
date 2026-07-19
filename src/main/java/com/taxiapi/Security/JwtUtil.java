package com.taxiapi.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Value("${JWT_SECRET}")
    private static String  secretKey;
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

    public String generateToken(String username){

        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                    System.currentTimeMillis() +
                            1000 * 60 * 60 * 2))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public String  extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public boolean isTokenExpired(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }


    public boolean isTokenValid(String token,String username){
        return (username.equals(extractUsername(token)))
                && (!isTokenExpired(token));
    }
}
