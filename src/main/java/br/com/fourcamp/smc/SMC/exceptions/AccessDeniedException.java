package br.com.fourcamp.smc.SMC.exceptions;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;

/**
 * Exception thrown when access is denied.
 */
public class AccessDeniedException extends CustomException {

    /**
     * Constructs a new AccessDeniedException with the default error message.
     */
    public AccessDeniedException() {
        super(ErrorMessage.ACCESS_DENIED);
    }
}