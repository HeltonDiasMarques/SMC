package br.com.fourcamp.smc.SMC.config.security;

/**
 * Class representing a JWT authentication response.
 */
public class JwtResponse {
    private final String jwtToken;

    /**
     * Constructor to create a JWT response.
     *
     * @param jwtToken the JWT token
     */
    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    /**
     * Gets the JWT token.
     *
     * @return the JWT token
     */
    public String getToken() {
        return this.jwtToken;
    }
}