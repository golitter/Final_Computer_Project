package com.golemon.blogbackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    // Valid for 30 days
    public static final Long JWT_TTL = 30 * 24 * 60 * 60 * 1000L;
    // Set secret key plaintext
    public static final String JWT_KEY = "golemon_blog";

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * Generate JWT token
     *
     * @param subject Data to be stored in token (in JSON format)
     * @return JWT token
     */
    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID()); // Set expiration time
        return builder.compact();
    }

    /**
     * Generate JWT token
     *
     * @param subject   Data to be stored in token (in JSON format)
     * @param ttlMillis Token expiration time
     * @return JWT token
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID()); // Set expiration time
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)              // Unique ID
                .setSubject(subject)      // Subject can be JSON data
                .setIssuer("glm")         // Issuer
                .setIssuedAt(now)         // Issue time
                .signWith(signatureAlgorithm, secretKey) // Sign with HS256 symmetric encryption algorithm, second parameter is secret key
                .setExpiration(expDate);
    }

    /**
     * Create token
     *
     * @param id        Token ID
     * @param subject   Data to be stored in token
     * @param ttlMillis Token expiration time
     * @return JWT token
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id); // Set expiration time
        return builder.compact();
    }

    /**
     * Generate encrypted secret key
     *
     * @return SecretKey object
     */
    public static SecretKey generalKey() {
        // Process URL-safe Base64 characters, convert "-" and "_" to standard Base64 characters "+" and "/"
        String base64Key = JwtUtil.JWT_KEY.replace('-', '+').replace('_', '/');
        byte[] encodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * Parse JWT token
     *
     * @param jwt JWT token string
     * @return Claims object containing token data
     * @throws Exception If token parsing fails
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
