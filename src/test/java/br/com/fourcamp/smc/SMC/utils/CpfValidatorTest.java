package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CpfValidatorTest {

    @Mock
    private IJdbcTemplateUserDao<User> userDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNormalizeAndValidate_ValidCpfWithoutFormatting() {
        String cpf = "12345678909";
        assertEquals("12345678909", CpfValidator.normalizeAndValidate(cpf));
    }

    @Test
    public void testNormalizeAndValidate_ValidCpfWithFormatting() {
        String cpf = "123.456.789-09";
        assertEquals("12345678909", CpfValidator.normalizeAndValidate(cpf));
    }

    @Test
    public void testNormalizeAndValidate_InvalidCpf() {
        String cpf = "12345678900";
        CustomException exception = assertThrows(CustomException.class, () -> {
            CpfValidator.normalizeAndValidate(cpf);
        });
        assertEquals(ErrorMessage.INVALID_CPF.getMessage(), exception.getMessage());
    }

    @Test
    public void testIsValid_ValidCpf() {
        assertTrue(CpfValidator.isValid("12345678909"));
    }

    @Test
    public void testIsValid_InvalidCpf() {
        assertFalse(CpfValidator.isValid("12345678900"));
    }

    @Test
    public void testValidateCpf_NewValidCpf() {
        User user = new User();
        user.setCpf("123.456.789-09");
        when(userDao.existsByCpf("12345678909", User.class)).thenReturn(false);

        assertDoesNotThrow(() -> CpfValidator.validateCpf(user, true, userDao));
    }

    @Test
    public void testValidateCpf_NewCpfAlreadyExists() {
        User user = new User();
        user.setCpf("123.456.789-09");
        when(userDao.existsByCpf("12345678909", User.class)).thenReturn(true);

        CustomException exception = assertThrows(CustomException.class, () -> {
            CpfValidator.validateCpf(user, true, userDao);
        });
        assertEquals(ErrorMessage.CRM_ALREADY_REGISTERED.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidateCpf_ExistingValidCpf() {
        User user = new User();
        user.setCpf("123.456.789-09");

        assertDoesNotThrow(() -> CpfValidator.validateCpf(user, false, userDao));
    }

    @Test
    public void testValidateCpf_InvalidCpf() {
        User user = new User();
        user.setCpf("12345678900");

        CustomException exception = assertThrows(CustomException.class, () -> {
            CpfValidator.validateCpf(user, false, userDao);
        });
        assertEquals(ErrorMessage.INVALID_CPF.getMessage(), exception.getMessage());
    }
}