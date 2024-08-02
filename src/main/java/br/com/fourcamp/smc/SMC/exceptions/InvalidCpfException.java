package br.com.fourcamp.smc.SMC.exceptions;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;

public class InvalidCpfException extends CustomException {
    public InvalidCpfException() {
        super(ErrorMessage.INVALID_CPF);
    }
}