package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Utility class for validating email addresses")
public class EmailValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Schema(description = "Checks if the provided email is valid")
    public static boolean isValid(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    public static <U extends User> void validateEmail(U user, boolean isNew, IJdbcTemplateUserDao<U> iJdbcTemplateUserDao) {
        if (!isValid(user.getEmail())) {
            throw new CustomException(ErrorMessage.INVALID_EMAIL_FORMAT);
        }
        if (isNew && iJdbcTemplateUserDao.existsByEmail(user.getEmail(), (Class<U>) user.getClass())) {
            throw new CustomException(ErrorMessage.EMAIL_ALREADY_EXISTS);
        }
    }
}