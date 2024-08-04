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

public class EmailValidatorTest {

    @Mock
    private IJdbcTemplateUserDao<User> userDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsValid_ValidEmail() {
        assertTrue(EmailValidator.isValid("test@example.com"));
    }

    @Test
    public void testIsValid_InvalidEmail() {
        assertFalse(EmailValidator.isValid("invalid-email"));
    }

    @Test
    public void testValidateEmail_NewValidEmail() {
        User user = new User();
        user.setEmail("new@example.com");
        when(userDao.existsByEmail("new@example.com", User.class)).thenReturn(false);

        assertDoesNotThrow(() -> EmailValidator.validateEmail(user, true, userDao));
    }

    @Test
    public void testValidateEmail_NewEmailAlreadyExists() {
        User user = new User();
        user.setEmail("existing@example.com");
        when(userDao.existsByEmail("existing@example.com", User.class)).thenReturn(true);

        CustomException exception = assertThrows(CustomException.class, () -> {
            EmailValidator.validateEmail(user, true, userDao);
        });
        assertEquals(ErrorMessage.EMAIL_ALREADY_EXISTS.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidateEmail_ExistingValidEmail() {
        User user = new User();
        user.setEmail("existing@example.com");

        assertDoesNotThrow(() -> EmailValidator.validateEmail(user, false, userDao));
    }

    @Test
    public void testValidateEmail_InvalidEmail() {
        User user = new User();
        user.setEmail("invalid-email");

        CustomException exception = assertThrows(CustomException.class, () -> {
            EmailValidator.validateEmail(user, false, userDao);
        });
        assertEquals(ErrorMessage.INVALID_EMAIL_FORMAT.getMessage(), exception.getMessage());
    }
}