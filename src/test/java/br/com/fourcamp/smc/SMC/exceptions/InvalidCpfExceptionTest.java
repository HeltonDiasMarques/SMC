package br.com.fourcamp.smc.SMC.exceptions;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvalidCpfExceptionTest {

    @Test
    public void testInvalidCpfException() {
        InvalidCpfException exception = assertThrows(InvalidCpfException.class, () -> {
            throw new InvalidCpfException();
        });

        assertEquals(ErrorMessage.INVALID_CPF, exception.getErrorMessage());
        assertEquals(ErrorMessage.INVALID_CPF.getMessage(), exception.getMessage());
    }
}
