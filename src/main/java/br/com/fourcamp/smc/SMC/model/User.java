package br.com.fourcamp.smc.SMC.model;

import br.com.fourcamp.smc.SMC.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a user in the system.
 * This class contains basic user information and is used as a base for other specific user types.
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class User {
    @Schema(description = "ID of the user", example = "string")
    private String id;

    @Schema(description = "Name of the user", example = "Helton Dias Marques")
    private String name;

    @Schema(description = "Email of the user", example = "helton.dias@example.com")
    private String email;

    @Schema(description = "Password of the user", example = "password123")
    private String password;

    @Schema(description = "CPF of the user", example = "941.300.040-93")
    private String cpf;

    @Schema(description = "Date of birth of the user", example = "2000-01-15")
    private String datebirth;

    @Schema(description = "Address of the user")
    private Address address;

    @Schema(description = "Phone number of the user", example = "11954778207")
    private String phone;

    @Schema(description = "Type of user", example = "userType")
    private UserType userType;

    /**
     * Constructs a new User with the specified details.
     *
     * @param id the ID of the user
     * @param name the name of the user
     * @param email the email of the user
     * @param password the password of the user
     * @param cpf the CPF of the user
     * @param datebirth the date of birth of the user
     * @param address the address of the user
     * @param phone the phone number of the user
     */
    public User(String id,
                String name,
                String email,
                String password,
                String cpf,
                String datebirth,
                Address address,
                String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.datebirth = datebirth;
        this.address = address;
        this.phone = phone;
    }
}