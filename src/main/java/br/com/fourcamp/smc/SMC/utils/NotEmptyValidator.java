package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.model.Patient;
import br.com.fourcamp.smc.SMC.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Utility class for validating if fields are not empty")
public class NotEmptyValidator {

    @Schema(description = "Validates if the user fields are not empty")
    public static void validate(User user) {
        if (isNullOrEmpty(user.getName())) {
            throw new CustomException(ErrorMessage.NAME_CANNOT_BE_EMPTY);
        }
        if (isNullOrEmpty(user.getEmail())) {
            throw new CustomException(ErrorMessage.EMAIL_CANNOT_BE_EMPTY);
        }
        if (isNullOrEmpty(user.getPassword())) {
            throw new CustomException(ErrorMessage.PASSWORD_CANNOT_BE_EMPTY);
        }
        if (isNullOrEmpty(user.getCpf())) {
            throw new CustomException(ErrorMessage.CPF_CANNOT_BE_EMPTY);
        }
        if (isNullOrEmpty(user.getDatebirth())) {
            throw new CustomException(ErrorMessage.BIRTHDATE_CANNOT_BE_EMPTY);
        }
        if (user.getAddress() == null || isNullOrEmpty(user.getAddress().getCep())) {
            throw new CustomException(ErrorMessage.CEP_CANNOT_BE_EMPTY);
        }
        if (user.getAddress() == null || isNullOrEmpty(user.getAddress().getNumber())) {
            throw new CustomException(ErrorMessage.ADDRESS_NUMBER_CANNOT_BE_EMPTY);
        }
        if (isNullOrEmpty(user.getPhone())) {
            throw new CustomException(ErrorMessage.PHONE_CANNOT_BE_EMPTY);
        }
        if (user instanceof Patient) {
            Patient patient = (Patient) user;
            if (patient.getPhoneSpare() != null && isNullOrEmpty(patient.getPhoneSpare())) {
                throw new CustomException(ErrorMessage.PHONE_SPARE_CANNOT_BE_EMPTY);
            }
        }
        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            if (isNullOrEmpty(doctor.getCrm())) {
                throw new CustomException(ErrorMessage.CRM_CANNOT_BE_EMPTY);
            }
            if (doctor.getSpecialty() == -1) {
                throw new CustomException(ErrorMessage.SPECIALTY_CANNOT_BE_EMPTY);
            }
        }
    }

    @Schema(description = "Checks if a string is null or empty")
    private static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}