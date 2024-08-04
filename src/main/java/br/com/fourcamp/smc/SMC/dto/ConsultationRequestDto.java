package br.com.fourcamp.smc.SMC.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for consultation request.
 */
@Data
public class ConsultationRequestDto {
    @Schema(description = "ID of the patient", example = "P123")
    private String patientId;

    @Schema(description = "Specialty for the consultation", example = "CARDIOLOGY")
    private String specialty;

    @Schema(description = "Date of the consultation", example = "2024-08-01")
    private String date;

    @Schema(description = "Start time of the consultation", example = "10:00")
    private String startTime;

    @Schema(description = "End time of the consultation", example = "10:30")
    private String endTime;
}
