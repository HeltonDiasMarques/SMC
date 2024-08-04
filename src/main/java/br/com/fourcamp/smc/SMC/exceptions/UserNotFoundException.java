package br.com.fourcamp.smc.SMC.exceptions;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;

/**
 * Exception thrown when a user is not found.
 */
public class UserNotFoundException extends CustomException {

    /**
     * Constructs a new UserNotFoundException with the default error message.
     */
    public UserNotFoundException() {
        super(ErrorMessage.USER_NOT_FOUND);
    }
}