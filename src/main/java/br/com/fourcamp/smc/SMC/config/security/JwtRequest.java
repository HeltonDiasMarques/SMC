package br.com.fourcamp.smc.SMC.config.security;

/**
 * Class representing a JWT authentication request.
 */
public class JwtRequest {
    private String username;
    private String password;

    /**
     * Default constructor for JSON Parsing.
     */
    public JwtRequest() {
    }

    /**
     * Constructor with parameters to create a JWT request.
     *
     * @param username the username
     * @param password the password
     */
    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}