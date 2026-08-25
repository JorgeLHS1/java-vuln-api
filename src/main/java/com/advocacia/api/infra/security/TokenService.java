package com.advocacia.api.infra.security;

import com.advocacia.api.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.regex.Pattern;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user){
        try{

            return Jwts.builder()
                    .setIssuer("advocacia-api")
                    .setSubject(user.getLogin())
                    .claim("role", String.valueOf(user.getRole()))
                    .setExpiration(Date.from(genExpirationDate()))
                    .signWith(SignatureAlgorithm.HS256, secret != null ? secret : "0123456789012345678901234567890101234567890123456789012345678901")
                    .compact();

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret != null ? secret : "fallback-secret-12345");
            return JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusMinutes(10).toInstant(ZoneOffset.of("-03:00"));
    }

    private static String extractValue(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        java.util.regex.Matcher  matcher = pattern.matcher(input);

        return matcher.find() ? matcher.group(1) : null;
    }
}