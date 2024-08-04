package br.com.fourcamp.smc.SMC.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a patient in the system.
 * This class extends the User class and includes additional information specific to patients.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Patient extends User {
    @Schema(description = "Spare phone number of the patient", example = "11983958418")
    private String phoneSpare;

    @Schema(description = "Allergies of the patient", example = "string")
    private String allergies;

    @Schema(description = "Additional notes about the patient", example = "string")
    private String notes;

    @Schema(description = "Medical transcript of the patient", example = "string")
    private String transcript;

    /**
     * Constructs a new Patient with the specified details.
     *
     * @param id         the ID of the patient
     * @param name       the name of the patient
     * @param email      the email of the patient
     * @param password   the password of the patient
     * @param cpf        the CPF of the patient
     * @param dateBirth  the date of birth of the patient
     * @param address    the address of the patient
     * @param phone      the phone number of the patient
     * @param phoneSpare the spare phone number of the patient
     * @param allergies  the allergies of the patient
     * @param notes      additional notes about the patient
     * @param transcript the medical transcript of the patient
     */
    public Patient(String id,
                   String name,
                   String email,
                   String password,
                   String cpf,
                   String dateBirth,
                   Address address,
                   String phone,
                   String phoneSpare,
                   String allergies,
                   String notes,
                   String transcript) {
        super(id, name, email, password, cpf, dateBirth, address, phone);
        this.phoneSpare = phoneSpare;
        this.allergies = allergies;
        this.notes = notes;
        this.transcript = transcript;
    }
}