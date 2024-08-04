package br.com.fourcamp.smc.SMC.model;

import br.com.fourcamp.smc.SMC.enums.Status;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScheduleTest {

    @Test
    public void testScheduleCreation() {
        String id = "S123";
        String doctorId = "D123";
        String patientId = "P123";
        Date date = Date.valueOf("2024-08-01");
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("10:30:00");
        Status status = Status.AVAILABLE;
        Timestamp createdAt = Timestamp.valueOf("2024-07-31 10:00:00");
        Timestamp updatedAt = Timestamp.valueOf("2024-07-31 10:00:00");

        Schedule schedule = new Schedule(id, doctorId, patientId, date, startTime, endTime, status, createdAt, updatedAt);

        assertEquals(id, schedule.getId());
        assertEquals(doctorId, schedule.getDoctorId());
        assertEquals(patientId, schedule.getPatientId());
        assertEquals(date, schedule.getDate());
        assertEquals(startTime, schedule.getStartTime());
        assertEquals(endTime, schedule.getEndTime());
        assertEquals(status, schedule.getStatus());
        assertEquals(createdAt, schedule.getCreatedAt());
        assertEquals(updatedAt, schedule.getUpdatedAt());
    }

    @Test
    public void testScheduleBuilder() {
        String id = "S123";
        String doctorId = "D123";
        String patientId = "P123";
        Date date = Date.valueOf("2024-08-01");
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("10:30:00");
        Status status = Status.AVAILABLE;
        Timestamp createdAt = Timestamp.valueOf("2024-07-31 10:00:00");
        Timestamp updatedAt = Timestamp.valueOf("2024-07-31 10:00:00");

        Schedule schedule = Schedule.builder()
                .id(id)
                .doctorId(doctorId)
                .patientId(patientId)
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        assertEquals(id, schedule.getId());
        assertEquals(doctorId, schedule.getDoctorId());
        assertEquals(patientId, schedule.getPatientId());
        assertEquals(date, schedule.getDate());
        assertEquals(startTime, schedule.getStartTime());
        assertEquals(endTime, schedule.getEndTime());
        assertEquals(status, schedule.getStatus());
        assertEquals(createdAt, schedule.getCreatedAt());
        assertEquals(updatedAt, schedule.getUpdatedAt());
    }

    @Test
    public void testScheduleSettersAndGetters() {
        Schedule schedule = new Schedule();

        String id = "S123";
        String doctorId = "D123";
        String patientId = "P123";
        Date date = Date.valueOf("2024-08-01");
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("10:30:00");
        Status status = Status.AVAILABLE;
        Timestamp createdAt = Timestamp.valueOf("2024-07-31 10:00:00");
        Timestamp updatedAt = Timestamp.valueOf("2024-07-31 10:00:00");

        schedule.setId(id);
        schedule.setDoctorId(doctorId);
        schedule.setPatientId(patientId);
        schedule.setDate(date);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setStatus(status);
        schedule.setCreatedAt(createdAt);
        schedule.setUpdatedAt(updatedAt);

        assertEquals(id, schedule.getId());
        assertEquals(doctorId, schedule.getDoctorId());
        assertEquals(patientId, schedule.getPatientId());
        assertEquals(date, schedule.getDate());
        assertEquals(startTime, schedule.getStartTime());
        assertEquals(endTime, schedule.getEndTime());
        assertEquals(status, schedule.getStatus());
        assertEquals(createdAt, schedule.getCreatedAt());
        assertEquals(updatedAt, schedule.getUpdatedAt());
    }
}