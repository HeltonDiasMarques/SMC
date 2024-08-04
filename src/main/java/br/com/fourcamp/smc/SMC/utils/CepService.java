package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service for retrieving and validating address information using CEP.
 */
@Service
@Schema(description = "Service for retrieving and validating address information using CEP")
public class CepService {

    private static final Logger logger = LoggerFactory.getLogger(CepService.class);
    private static final String VIACEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    /**
     * Retrieves address information by CEP.
     *
     * @param cep the CEP to search for
     * @return the address information corresponding to the CEP
     * @throws CustomException if the CEP is invalid or the address cannot be retrieved
     */
    @Schema(description = "Retrieves address information by CEP")
    public Address getAddressByCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        ViaCepResponse response = restTemplate.getForObject(VIACEP_URL, ViaCepResponse.class, cep);
        logger.info("Received response from ViaCEP: {}", response);

        if (response == null || response.getErro() != null) {
            throw new CustomException(ErrorMessage.INVALID_ADDRESS);
        }

        return Address.builder()
                .cep(cep)
                .street(response.getLogradouro())
                .neighborhood(response.getBairro())
                .city(response.getLocalidade())
                .state(response.getUf())
                .build();
    }

    /**
     * Validates if the provided CEP is valid.
     *
     * @param cep the CEP to validate
     * @return true if the CEP is valid, false otherwise
     */
    @Schema(description = "Validates if the provided CEP is valid")
    public boolean isValidCep(String cep) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ViaCepResponse response = restTemplate.getForObject(VIACEP_URL, ViaCepResponse.class, cep);
            logger.info("Received response from ViaCEP: {}", response);
            return response != null && response.getErro() == null;
        } catch (Exception e) {
            logger.error("Error validating CEP", e);
            return false;
        }
    }

    /**
     * Inner class to represent the response from ViaCEP.
     */
    private static class ViaCepResponse {
        private String logradouro;
        private String bairro;
        private String localidade;
        private String uf;
        private Boolean erro;

        public String getLogradouro() {
            return logradouro;
        }

        public void setLogradouro(String logradouro) {
            this.logradouro = logradouro;
        }

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public String getLocalidade() {
            return localidade;
        }

        public void setLocalidade(String localidade) {
            this.localidade = localidade;
        }

        public String getUf() {
            return uf;
        }

        public void setUf(String uf) {
            this.uf = uf;
        }

        public Boolean getErro() {
            return erro;
        }

        public void setErro(Boolean erro) {
            this.erro = erro;
        }
    }
}