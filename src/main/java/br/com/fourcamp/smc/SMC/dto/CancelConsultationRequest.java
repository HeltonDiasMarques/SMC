package br.com.fourcamp.smc.SMC.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for canceling a consultation request.
 */
@Data
public class CancelConsultationRequest {
    @Schema(description = "ID of the patient", example = "P123")
    private String patientId;

    @Schema(description = "Start time of the consultation to be canceled", example = "10:00")
    private String startTime;
}
