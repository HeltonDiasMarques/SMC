package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.regex.Pattern;

@Schema(description = "Utility class for validating CRM")
public class CrmValidator {
    private static final Pattern CRM_PATTERN = Pattern.compile("^\\d{6}-[A-Z]{2}$");

    @Schema(description = "Normalizes and validates the CRM format")
    public static String normalizeAndValidate(String crm) {
        if (crm != null && CRM_PATTERN.matcher(crm).matches()) {
            return crm.toUpperCase();
        }
        throw new CustomException(ErrorMessage.INVALID_CRM_FORMAT);
    }

    @Schema(description = "Checks if the provided CRM is valid")
    public static boolean isValid(String crm) {
        return crm != null && CRM_PATTERN.matcher(crm).matches();
    }

    public static void validateCrm(Doctor doctor, boolean isNew, IJdbcTemplateUserDao<Doctor> iJdbcTemplateUserDao) {
        doctor.setCrm(normalizeAndValidate(doctor.getCrm()));
        if (!isValid(doctor.getCrm())) {
            throw new CustomException(ErrorMessage.INVALID_CRM_FORMAT);
        }
        if (isNew && iJdbcTemplateUserDao.existsByCrm(doctor.getCrm())) {
            throw new CustomException(ErrorMessage.CRM_ALREADY_REGISTERED);
        }
    }
}