package br.com.fourcamp.smc.SMC.model;

import br.com.fourcamp.smc.SMC.enums.Specialty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

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