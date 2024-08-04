package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Address;
import br.com.fourcamp.smc.SMC.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CepValidatorTest {

    @Mock
    private CepService cepService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsValid_ValidCepWithoutDash() {
        assertTrue(CepValidator.isValid("12345678"));
    }

    @Test
    public void testIsValid_ValidCepWithDash() {
        assertTrue(CepValidator.isValid("12345-678"));
    }

    @Test
    public void testIsValid_InvalidCepFormat() {
        CustomException exception = assertThrows(CustomException.class, () -> {
            CepValidator.isValid("1234-678");
        });
        assertEquals(ErrorMessage.INVALID_CEP_FORMAT.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidateCep_Valid() {
        User user = createUserWithCep("12345678");
        when(cepService.isValidCep("12345678")).thenReturn(true);

        assertDoesNotThrow(() -> CepValidator.validateCep(user, cepService));
    }

    @Test
    public void testValidateCep_InvalidCepService() {
        User user = createUserWithCep("12345678");
        when(cepService.isValidCep("12345678")).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            CepValidator.validateCep(user, cepService);
        });
        assertEquals(ErrorMessage.INVALID_ADDRESS.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidateCep_InvalidCepFormat() {
        User user = createUserWithCep("1234-678");

        CustomException exception = assertThrows(CustomException.class, () -> {
            CepValidator.validateCep(user, cepService);
        });
        assertEquals(ErrorMessage.INVALID_CEP_FORMAT.getMessage(), exception.getMessage());
    }

    private User createUserWithCep(String cep) {
        Address address = Address.builder()
                .cep(cep)
                .build();
        User user = new User();
        user.setAddress(address);
        return user;
    }
}