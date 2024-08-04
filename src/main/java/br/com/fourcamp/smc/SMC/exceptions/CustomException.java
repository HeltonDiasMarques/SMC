package br.com.fourcamp.smc.SMC.exceptions;


import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final int statusCode;
    private final ErrorMessage errorMessage;


    public CustomException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.statusCode = errorMessage.getStatusCode();
        this.errorMessage = errorMessage;
    }
}