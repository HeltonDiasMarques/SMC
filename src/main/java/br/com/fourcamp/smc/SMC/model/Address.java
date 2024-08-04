package br.com.fourcamp.smc.SMC.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Represents an address.
 * This class contains the details of an address, including the street, neighborhood, city, state, and house number.
 */
@Data
@Builder
public class Address {
    @Schema(description = "CEP of the address", example = "05442-100")
    private String cep;

    @Schema(description = "Street name of the address", example = "string")
    private String street;

    @Schema(description = "Neighborhood of the address", example = "string")
    private String neighborhood;

    @Schema(description = "City of the address", example = "string")
    private String city;

    @Schema(description = "State of the address", example = "string")
    private String state;

    @Schema(description = "House number of the address", example = "456")
    private String number;
}