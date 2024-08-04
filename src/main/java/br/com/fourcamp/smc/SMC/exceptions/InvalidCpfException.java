package br.com.fourcamp.smc.SMC.exceptions;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;

/**
 * Exception thrown when an invalid CPF is encountered.
 */
public class InvalidCpfException extends CustomException {

    /**
     * Exception thrown when an invalid CPF is encountered.
     */
    public InvalidCpfException() {
        super(ErrorMessage.INVALID_CPF);
    }
}