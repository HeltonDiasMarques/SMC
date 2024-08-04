package br.com.fourcamp.smc.SMC.exceptions;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccessDeniedExceptionTest {

    @Test
    public void testAccessDeniedException() {
        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> {
            throw new AccessDeniedException();
        });

        assertEquals(ErrorMessage.ACCESS_DENIED.getMessage(), exception.getMessage());
        assertEquals(ErrorMessage.ACCESS_DENIED.getStatusCode(), exception.getStatusCode());
    }
}