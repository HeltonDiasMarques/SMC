package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Utility class for validating CEP")
public class CepValidator {

    @Schema(description = "Validates if the provided CEP format is correct")
    public static boolean isValid(String cep) {
        if (cep == null || !cep.matches("\\d{8}|\\d{5}-\\d{3}")) {
            throw new CustomException(ErrorMessage.INVALID_CEP_FORMAT);
        }
        return true;
    }

    public static <U extends User> void validateCep(U user, CepService cepService) {
        if (!isValid(user.getAddress().getCep()) || !cepService.isValidCep(user.getAddress().getCep())) {
            throw new CustomException(ErrorMessage.INVALID_ADDRESS);
        }
    }
}