package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.regex.Pattern;

/**
 * Utility class for validating phone numbers.
 */
@Schema(description = "Utility class for validating phone numbers")
public class PhoneValidator {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{11}$|^\\(\\d{2}\\) \\d{5}-\\d{4}$");

    /**
     * Normalizes and validates the phone number format.
     *
     * @param phone the phone number to normalize and validate
     * @return the normalized phone number
     * @throws CustomException if the phone number format is invalid
     */
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

    /**
     * Checks if the provided phone number is valid.
     *
     * @param phone the phone number to check
     * @return true if the phone number is valid, false otherwise
     */
    @Schema(description = "Checks if the provided phone number is valid")
    public static boolean isValid(String phone) {
        return phone != null && phone.length() == 11;
    }

    /**
     * Validates the phone number of a user.
     *
     * @param <U> the type of the user
     * @param user the user whose phone number needs to be validated
     * @param isNew indicates if the user is new (true) or existing (false)
     * @param iJdbcTemplateUserDao the DAO used to check for existing phone numbers
     * @throws CustomException if the phone number is invalid or already registered
     */
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