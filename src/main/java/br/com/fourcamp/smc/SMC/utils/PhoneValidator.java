package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.regex.Pattern;

@Schema(description = "Utility class for validating phone numbers")
public class PhoneValidator {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{11}$|^\\(\\d{2}\\) \\d{5}-\\d{4}$");

    @Schema(description = "Normalizes and validates the phone number format")
    public static String normalizeAndValidate(String phone) {
        if (phone != null && PHONE_PATTERN.matcher(phone).matches()) {
            String normalizedPhone = phone.replaceAll("[^\\d]", "");
            if (isValid(normalizedPhone)) {
                return normalizedPhone;
            }
        }
        throw new CustomException(ErrorMessage.INVALID_PHONE_FORMAT);
    }

    @Schema(description = "Checks if the provided phone number is valid")
    public static boolean isValid(String phone) {
        return phone != null && phone.length() == 11;
    }

    public static <U extends User> void validatePhone(U user, boolean isNew, IJdbcTemplateUserDao<U> iJdbcTemplateUserDao) {
        user.setPhone(normalizeAndValidate(user.getPhone()));
        if (!isValid(user.getPhone())) {
            throw new CustomException(ErrorMessage.INVALID_PHONE_FORMAT);
        }
        if (isNew && iJdbcTemplateUserDao.existsByPhone(user.getPhone(), (Class<U>) user.getClass())) {
            throw new CustomException(ErrorMessage.PHONE_ALREADY_EXISTS);
        }
    }
}