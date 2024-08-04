package br.com.fourcamp.smc.SMC.dto;

import br.com.fourcamp.smc.SMC.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Data Transfer Object (DTO) for consultation response.
 */
@Data
public class ConsultationResponseDto {
    @Schema(description = "ID of the consultation", example = "C123")
    private String id;

    @Schema(description = "ID of the doctor", example = "D123")
    private String doctorId;

    @Schema(description = "ID of the patient", example = "P123")
    private String patientId;

    @Schema(description = "Date of the consultation", example = "2024-08-01")
    private Date date;

    @Schema(description = "Start time of the consultation", example = "10:00")
    private Time startTime;

    @Schema(description = "End time of the consultation", example = "10:30")
    private Time endTime;

    @Schema(description = "Status of the consultation", example = "BOOKED")
    private Status status;

    @Schema(description = "Timestamp when the consultation was created", example = "2024-07-31T10:00:00Z")
    private Timestamp createdAt;

    @Schema(description = "Timestamp when the consultation was last updated", example = "2024-07-31T10:00:00Z")
    private Timestamp updatedAt;
}
