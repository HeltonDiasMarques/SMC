package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Utility class for validating birth dates.
 */
public class BirthDateValidator {

    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{8}$|^\\d{4}-\\d{2}-\\d{2}$|^\\d{4}/\\d{2}/\\d{2}$");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    static {
        sdf.setLenient(false);
    }

    /**
     * Normalizes and validates the date.
     *
     * @param date the date to normalize and validate
     * @return the normalized date in "yyyyMMdd" format
     * @throws CustomException if the date format is invalid
     */
    @Schema(description = "Normalize and validate the date", example = "2024-08-01")
    public static String normalizeAndValidate(String date) {
        if (date != null && DATE_PATTERN.matcher(date).matches()) {
            try {
                if (date.contains("-")) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    format.setLenient(false);
                    format.parse(date);
                    return new SimpleDateFormat("yyyyMMdd").format(format.parse(date));
                } else if (date.contains("/")) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    format.setLenient(false);
                    format.parse(date);
                    return new SimpleDateFormat("yyyyMMdd").format(format.parse(date));
                } else if (date.length() == 8) {
                    sdf.parse(date);
                    return date;
                }
            } catch (ParseException e) {
                throw new CustomException(ErrorMessage.INVALID_DATE_FORMAT);
            }
        }
        throw new CustomException(ErrorMessage.INVALID_DATE_FORMAT);
    }

    /**
     * Checks if the date is valid.
     *
     * @param date the date to check
     * @return true if the date is valid, false otherwise
     */
    @Schema(description = "Check if the date is valid", example = "2024-08-01")
    public static boolean isValid(String date) {
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Checks if the date corresponds to an adult (18 years or older).
     *
     * @param date the date to check
     * @return true if the date corresponds to an adult, false otherwise
     * @throws CustomException if the age is invalid or the date format is invalid
     */
    @Schema(description = "Check if the date corresponds to an adult (18 years or older)", example = "2024-08-01")
    public static boolean isAdult(String date) {
        try {
            sdf.parse(date);
            long ageInMillis = System.currentTimeMillis() - sdf.parse(date).getTime();
            long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365);
            if (ageInYears >= 18) {
                return true;
            } else {
                throw new CustomException(ErrorMessage.INVALID_AGE);
            }
        } catch (ParseException e){
            throw new CustomException(ErrorMessage.INVALID_DATE_FORMAT);
        }
    }

    /**
     * Validates the date of birth of a user.
     *
     * @param <U> the type of the user
     * @param user the user to validate
     * @throws CustomException if the date of birth is invalid or the user is not an adult
     */
    public static <U extends User> void validateDateOfBirth(U user) {
        user.setDatebirth(normalizeAndValidate(user.getDatebirth()));
        if (!isValid(user.getDatebirth())) {
            throw new CustomException(ErrorMessage.INVALID_DATE_FORMAT);
        }
        if (!isAdult(user.getDatebirth())) {
            throw new CustomException(ErrorMessage.INVALID_AGE);
        }
    }
}