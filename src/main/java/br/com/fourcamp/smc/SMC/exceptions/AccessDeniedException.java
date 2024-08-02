package br.com.fourcamp.smc.SMC.exceptions;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;

public class AccessDeniedException extends CustomException {
    public AccessDeniedException() {
        super(ErrorMessage.ACCESS_DENIED);
    }
}