package br.com.fourcamp.smc.SMC.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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