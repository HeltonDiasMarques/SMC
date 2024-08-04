package br.com.fourcamp.smc.SMC.exceptions;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomExceptionTest {

    @Test
    public void testCustomExceptionWithErrorMessage() {
        CustomException exception = assertThrows(CustomException.class, () -> {
            throw new CustomException(ErrorMessage.ACCESS_DENIED);
        });

        assertEquals(ErrorMessage.ACCESS_DENIED.getMessage(), exception.getMessage());
        assertEquals(ErrorMessage.ACCESS_DENIED.getStatusCode(), exception.getStatusCode());
        assertEquals(ErrorMessage.ACCESS_DENIED, exception.getErrorMessage());
    }
}