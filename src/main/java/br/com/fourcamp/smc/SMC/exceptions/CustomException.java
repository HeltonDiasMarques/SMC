package br.com.fourcamp.smc.SMC.exceptions;


import br.com.fourcamp.smc.SMC.enums.ErrorMessage;

public class CustomException extends RuntimeException {
    private final int statusCode;

    public CustomException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.statusCode = errorMessage.getStatusCode();
    }

    public int getStatusCode() {
        return statusCode;
    }
}