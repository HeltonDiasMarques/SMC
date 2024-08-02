package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.User;

import java.util.regex.Pattern;

public class CpfValidator {

    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$|^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");

    public static String normalizeAndValidate(String cpf) {
        if (cpf != null && CPF_PATTERN.matcher(cpf).matches()) {
            String normalizedCpf = cpf.replaceAll("[^\\d]", "");
            if (isValid(normalizedCpf)) {
                return normalizedCpf;
            }
        }
        throw new CustomException(ErrorMessage.INVALID_CPF);
    }

    public static boolean isValid(String cpf) {
        if (cpf == null || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) {
            firstDigit = 0;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) {
            secondDigit = 0;
        }

        return firstDigit == Character.getNumericValue(cpf.charAt(9)) && secondDigit == Character.getNumericValue(cpf.charAt(10));
    }

    public static <U extends User> void validateCpf(U user, boolean isNew, IJdbcTemplateUserDao<U> iJdbcTemplateUserDao) {
        user.setCpf(normalizeAndValidate(user.getCpf()));
        if (!isValid(user.getCpf())) {
            throw new CustomException(ErrorMessage.INVALID_CPF);
        }
        if (isNew && iJdbcTemplateUserDao.existsByCpf(user.getCpf(), (Class<U>) user.getClass())) {
            throw new CustomException(ErrorMessage.CRM_ALREADY_REGISTERED);
        }
    }
}