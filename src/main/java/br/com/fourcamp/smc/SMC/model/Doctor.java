package br.com.fourcamp.smc.SMC.model;

import br.com.fourcamp.smc.SMC.enums.Specialty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Doctor extends User {
    @Schema(description = "CRM of the doctor", example = "145689-SP")
    private String crm;

    @Schema(description = "Specialty of the doctor", example = "1")
    private Specialty specialty;

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
        this.specialty = Specialty.fromCode(specialtyCode);
    }
}