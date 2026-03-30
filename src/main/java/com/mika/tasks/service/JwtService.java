package com.mika.tasks.service;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;
    private static final String ISSUER = "TasksMika";

    public String generateToken(String userEmail){
    try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                .withIssuer(ISSUER)
                .withSubject(userEmail)
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(3600))) 
                .sign(algorithm); 

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro em criar JWT token", exception);
        }
    }
    public String validateToken(String token){
    try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String email = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();
                return email;
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token inválido.", e);

        }
    }
}
