package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Patient;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Utility class for validating spare phone numbers.
 */
@Schema(description = "Utility class for validating spare phone numbers")
public class PhoneSpareValidator {

    /**
     * Checks if the spare phone number is equal to the main phone number.
     *
     * @param phone the main phone number
     * @param phoneSpare the spare phone number
     * @return true if the spare phone number is equal to the main phone number, false otherwise
     */
    @Schema(description = "Checks if the spare phone number is equal to the main phone number")
    public static boolean isPhoneSpareEqualToPhone(String phone, String phoneSpare) {
        return phone != null && phone.equals(phoneSpare);
    }

    /**
     * Validates the spare phone number of a patient.
     *
     * @param patient the patient whose spare phone number needs to be validated
     * @throws CustomException if the spare phone number is invalid or equal to the main phone number
     */
    @Schema(description = "Validates the spare phone number of a patient")
    public static void validatePhoneSpare(Patient patient) {
        String phone = patient.getPhone();
        String phoneSpare = patient.getPhoneSpare();

        if (phoneSpare != null) {
            try {
                phoneSpare = PhoneValidator.normalizeAndValidate(phoneSpare);
            }catch (CustomException e){
                throw new CustomException(ErrorMessage.INVALID_PHONE_SPARE);
            }
            if (isPhoneSpareEqualToPhone(phone, phoneSpare)) {
                throw new CustomException(ErrorMessage.PHONE_SPARE_EQUAL_TO_PHONE);
            }
            if (!PhoneValidator.isValid(phoneSpare)) {
                throw new CustomException(ErrorMessage.INVALID_PHONE_SPARE);
            }
            patient.setPhoneSpare(phoneSpare);
        }
    }
}