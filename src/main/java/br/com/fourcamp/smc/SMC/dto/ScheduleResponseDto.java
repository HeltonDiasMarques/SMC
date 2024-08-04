package br.com.fourcamp.smc.SMC.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

/**
 * Data Transfer Object (DTO) for schedule response.
 */
@Data
@NoArgsConstructor
public class ScheduleResponseDto {
    @Schema(description = "ID of the schedule", example = "S123")
    private String id;

    @Schema(description = "ID of the doctor", example = "D123")
    private String doctorId;

    @Schema(description = "Date of the schedule", example = "2024-08-01")
    private Date date;

    @Schema(description = "Start time of the schedule", example = "10:00")
    private Time startTime;

    @Schema(description = "End time of the schedule", example = "10:30")
    private Time endTime;
}
