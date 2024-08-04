package br.com.fourcamp.smc.SMC.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * Utility class for generating JWT tokens for testing purposes.
 */
public class JwtTestUtil {

    private static final String SECRET_KEY = "mySecretKey"; // Use a mesma chave secreta usada na configuração JWT

    /**
     * Generates a JWT token for the specified username.
     *
     * @param username the username for which to generate the token
     * @return the generated JWT token
     */
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}