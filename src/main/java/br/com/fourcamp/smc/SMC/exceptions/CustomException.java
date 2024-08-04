package br.com.fourcamp.smc.SMC.exceptions;


import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import lombok.Getter;

/**
 * Custom exception class for handling application-specific exceptions.
 */
@Getter
public class CustomException extends RuntimeException {
    private final int statusCode;
    private final ErrorMessage errorMessage;

    /**
     * Constructs a new CustomException with the specified error message.
     *
     * @param errorMessage the error message enum containing the status code and message
     */
    public CustomException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.statusCode = errorMessage.getStatusCode();
        this.errorMessage = errorMessage;
    }
}