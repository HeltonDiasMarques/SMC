package br.com.fourcamp.smc.SMC.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
public class ScheduleResponseDto {
    private String id;
    private String doctorId;
    private Date date;
    private Time startTime;
    private Time endTime;
}
