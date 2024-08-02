package br.com.fourcamp.smc.SMC.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScheduleRequestDto {
    @Schema(description = "ID of the doctor", example = "D123")
    private String doctorId;

    @Schema(description = "Date of the schedule", example = "2024-08-01")
    private String date;

    @Schema(description = "Start time of the schedule", example = "10:00")
    private String startTime;

    @Schema(description = "End time of the schedule", example = "10:30")
    private String endTime;
}
