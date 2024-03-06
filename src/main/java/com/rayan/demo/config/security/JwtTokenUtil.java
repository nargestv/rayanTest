package com.rayan.demo.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {

    @Value("${jwt.token-prefix}")
    private String tokenPrefix;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.private-key}")
    private String secret;

    @Value("${jwt.jwtTokenValidity}")
    public long jwtTokenValidity;

    @Value("${jwt.jwtRefreshTokenValidity}")
    public long jwtRefreshTokenValidity;

    private final JwtDecoder jwtDecoder;
    private final HttpServletRequest httpServletRequest;

    public JwtTokenUtil(JwtDecoder jwtDecoder, HttpServletRequest httpServletRequest) {
        this.jwtDecoder = jwtDecoder;
        this.httpServletRequest = httpServletRequest;
    }


    public String getUsernameFromToken() {
        String token = getTokenFromHeader();
        return jwtDecoder.decode(token).getSubject();
    }

    public String getUsernameFromToken(String token) {
        return jwtDecoder.decode(token).getSubject();
    }

    public LoggedInUser getLoggedInUser() {
        String token = getTokenFromHeader();
        LoggedInUser loggedInUser = new LoggedInUser();
        Jwt jwt = jwtDecoder.decode(token);
        loggedInUser.setUserName(jwt.getSubject());
        Map<String, Object> claimMap = jwt.getClaims();
        if (claimMap.containsKey("customer_id")) {
            loggedInUser.setCustomerId((String) claimMap.get("customer_id"));
        }
        if (claimMap.containsKey("public_key")) {
            loggedInUser.setPublicKey((String) claimMap.get("public_key"));
        }
        if (claimMap.containsKey("secret_key")) {
            loggedInUser.setSecretKey((String) claimMap.get("secret_key"));
        }
        return loggedInUser;
    }

    public Instant getExpirationDateFromToken(String token) {
        return jwtDecoder.decode(token).getExpiresAt();
    }


    public Boolean isTokenExpired(String token) {
        try {
            final Instant expiration = jwtDecoder.decode(token).getExpiresAt();
            assert expiration != null;
            return expiration.isBefore(Instant.now());
        } catch (Exception e) {
            return true;
        }
    }

    public String generateToken(UserDetailsImpl userDetails) {
        try {

            Set<String> authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            Map<String, Object> claimMap = new HashMap<>() {{
                put("authorities", authorities);
            }};

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(secret.getBytes()));

            KeyFactory rsaFact = KeyFactory.getInstance("RSA");
            RSAPrivateKey key = (RSAPrivateKey) rsaFact.generatePrivate(keySpec);
            return Jwts.builder()
                    .setClaims(claimMap)
                    .setSubject(userDetails.getUser().getEmail())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setIssuer(issuer)
                    .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000))
                    .signWith(SignatureAlgorithm.RS256, key)
                    .compact();
        } catch (InvalidKeySpecException e) {
            return null;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public String generateRefreshToken(String uuid) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(secret.getBytes()));

            KeyFactory rsaFact = KeyFactory.getInstance("RSA");
            RSAPrivateKey key = (RSAPrivateKey) rsaFact.generatePrivate(keySpec);
            return Jwts.builder()
                    .setSubject(uuid)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setIssuer(issuer)
                    .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshTokenValidity * 1000))
                    .signWith(SignatureAlgorithm.RS256, key)
                    .compact();
        } catch (InvalidKeySpecException e) {
            return null;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public String getTokenFromHeader() {
        return httpServletRequest.getHeader("Authorization").replace(tokenPrefix, "");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // log exception or handle as needed
            return false;
        }
    }

}