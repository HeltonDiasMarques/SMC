package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Utility class for validating CEP (Postal Code).
 */
@Schema(description = "Utility class for validating CEP")
public class CepValidator {

    /**
     * Validates if the provided CEP format is correct.
     *
     * @param cep the CEP to validate
     * @return true if the CEP format is correct
     * @throws CustomException if the CEP format is invalid
     */
    @Schema(description = "Validates if the provided CEP format is correct")
    public static boolean isValid(String cep) {
        if (cep == null || !cep.matches("\\d{8}|\\d{5}-\\d{3}")) {
            throw new CustomException(ErrorMessage.INVALID_CEP_FORMAT);
        }
        return true;
    }

    /**
     * Validates the CEP of a user's address.
     *
     * @param <U> the type of the user
     * @param user the user whose CEP needs to be validated
     * @param cepService the service used to validate the CEP
     * @throws CustomException if the CEP is invalid or the address cannot be validated
     */
    public static <U extends User> void validateCep(U user, CepService cepService) {
        if (!isValid(user.getAddress().getCep()) || !cepService.isValidCep(user.getAddress().getCep())) {
            throw new CustomException(ErrorMessage.INVALID_ADDRESS);
        }
    }
}