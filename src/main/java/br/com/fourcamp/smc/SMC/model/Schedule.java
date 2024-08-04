package br.com.fourcamp.smc.SMC.model;

import br.com.fourcamp.smc.SMC.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Represents a schedule in the system.
 * This class contains the details of a schedule, including the doctor, patient, date, time, status, and timestamps.
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class Schedule {
    @Schema(description = "ID of the schedule", example = "S123")
    private String id;

    @Schema(description = "ID of the doctor", example = "D123")
    private String doctorId;

    @Schema(description = "ID of the patient", example = "P123")
    private String patientId;

    @Schema(description = "Date of the schedule", example = "2024-08-01")
    private Date date;

    @Schema(description = "Start time of the schedule", example = "10:00")
    private Time startTime;

    @Schema(description = "End time of the schedule", example = "10:30")
    private Time endTime;

    @Schema(description = "Status of the schedule", example = "AVAILABLE")
    private Status status;

    @Schema(description = "Timestamp when the schedule was created", example = "2024-07-31T10:00:00Z")
    private Timestamp createdAt;

    @Schema(description = "Timestamp when the schedule was last updated", example = "2024-07-31T10:00:00Z")
    private Timestamp updatedAt;

    /**
     * Constructs a new Schedule with the specified details.
     *
     * @param id         the ID of the schedule
     * @param doctorId   the ID of the doctor
     * @param patientId  the ID of the patient
     * @param date       the date of the schedule
     * @param startTime  the start time of the schedule
     * @param endTime    the end time of the schedule
     * @param status     the status of the schedule
     * @param createdAt  the timestamp when the schedule was created
     * @param updatedAt  the timestamp when the schedule was last updated
     */
    public Schedule(String id,
                    String doctorId,
                    String patientId,
                    Date date,
                    Time startTime,
                    Time endTime,
                    Status status,
                    Timestamp createdAt,
                    Timestamp updatedAt) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}