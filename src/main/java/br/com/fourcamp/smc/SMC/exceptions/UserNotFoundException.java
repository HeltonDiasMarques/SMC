package br.com.fourcamp.smc.SMC.exceptions;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(ErrorMessage.USER_NOT_FOUND);
    }
}