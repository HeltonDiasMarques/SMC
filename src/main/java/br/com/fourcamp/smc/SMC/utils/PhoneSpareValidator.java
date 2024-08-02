package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Patient;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Utility class for validating spare phone numbers")
public class PhoneSpareValidator {

    @Schema(description = "Checks if the spare phone number is equal to the main phone number")
    public static boolean isPhoneSpareEqualToPhone(String phone, String phoneSpare) {
        return phone != null && phone.equals(phoneSpare);
    }

    @Schema(description = "Validates the spare phone number of a patient")
    public static void validatePhoneSpare(Patient patient) {
        String phone = patient.getPhone();
        String phoneSpare = patient.getPhoneSpare();

        if (phoneSpare != null) {
            phoneSpare = PhoneValidator.normalizeAndValidate(phoneSpare);
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