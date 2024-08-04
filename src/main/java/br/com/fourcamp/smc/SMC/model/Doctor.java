package br.com.fourcamp.smc.SMC.model;

import br.com.fourcamp.smc.SMC.enums.Specialty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a doctor in the system.
 * This class extends the User class and includes additional information specific to doctors.
 */
@Data
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Doctor extends User {
    @Schema(description = "CRM of the doctor", example = "145689-SP")
    private String crm;

    @Schema(description = "Specialty of the doctor", example = "1")
    private int specialty;

    /**
     * Constructs a new Doctor with the specified details.
     *
     * @param id             the ID of the doctor
     * @param name           the name of the doctor
     * @param email          the email of the doctor
     * @param password       the password of the doctor
     * @param cpf            the CPF of the doctor
     * @param dateBirth      the date of birth of the doctor
     * @param address        the address of the doctor
     * @param phone          the phone number of the doctor
     * @param crm            the CRM of the doctor
     * @param specialtyCode  the specialty code of the doctor
     */
    public Doctor(String id,
                  String name,
                  String email,
                  String password,
                  String cpf,
                  String dateBirth,
                  Address address,
                  String phone,
                  String crm,
                  int specialtyCode) {
        super(id, name, email, password, cpf, dateBirth, address, phone);
        this.crm = crm;
        this.specialty = specialtyCode;
    }

    public String getSpecialtyDescription() {
        return Specialty.fromCode(this.specialty).getDescription();
    }
}