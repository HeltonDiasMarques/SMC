package br.com.fourcamp.smc.SMC.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BookConsultationRequest {
    @Schema(description = "ID of the patient", example = "P123")
    private String patientId;

    @Schema(description = "Date of the consultation", example = "2024-08-01")
    private String date;

    @Schema(description = "Start time of the consultation", example = "10:00")
    private String startTime;

    @Schema(description = "Specialty for the consultation", example = "CARDIOLOGY")
    private String specialty;
}
