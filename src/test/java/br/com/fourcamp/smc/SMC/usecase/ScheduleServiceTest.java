package br.com.fourcamp.smc.SMC.usecase;

import br.com.fourcamp.smc.SMC.dao.impl.JdbcTemplateScheduleImpl;
import br.com.fourcamp.smc.SMC.dto.BookConsultationRequest;
import br.com.fourcamp.smc.SMC.dto.ScheduleRequestDto;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.enums.Specialty;
import br.com.fourcamp.smc.SMC.enums.Status;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.model.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ScheduleServiceTest {

    @Mock
    private JdbcTemplateScheduleImpl jdbcTemplateSchedule;

    @InjectMocks
    private ScheduleService scheduleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveSchedule_Success() {
        ScheduleRequestDto dto = new ScheduleRequestDto();
        dto.setDoctorId("D001");
        dto.setDate("2023-01-01");
        dto.setStartTime("10:00:00");
        dto.setEndTime("10:30:00");

        assertDoesNotThrow(() -> scheduleService.saveSchedule(dto));

        ArgumentCaptor<Schedule> captor = ArgumentCaptor.forClass(Schedule.class);
        verify(jdbcTemplateSchedule, times(1)).save(captor.capture());

        Schedule savedSchedule = captor.getValue();
        assertEquals("D001", savedSchedule.getDoctorId());
        assertEquals(Date.valueOf("2023-01-01"), savedSchedule.getDate());
        assertEquals(Time.valueOf("10:00:00"), savedSchedule.getStartTime());
        assertEquals(Time.valueOf("10:30:00"), savedSchedule.getEndTime());
        assertEquals(Status.AVAILABLE, savedSchedule.getStatus());
    }

    @Test
    public void testGetScheduleByDoctorId_Success() {
        String doctorId = "D001";
        Schedule schedule = new Schedule();
        schedule.setDoctorId(doctorId);
        schedule.setDate(Date.valueOf(LocalDate.now()));
        schedule.setStartTime(Time.valueOf(LocalTime.now()));
        schedule.setEndTime(Time.valueOf(LocalTime.now().plusMinutes(30)));
        schedule.setStatus(Status.AVAILABLE);

        when(jdbcTemplateSchedule.findSchedulesByDoctorId(doctorId)).thenReturn(Collections.singletonList(schedule));

        List<Schedule> schedules = scheduleService.getScheduleByDoctorId(doctorId);

        assertNotNull(schedules);
        assertEquals(1, schedules.size());
        assertEquals(doctorId, schedules.get(0).getDoctorId());
    }

    @Test
    public void testGetScheduleByDoctorId_NotFound() {
        String doctorId = "D001";
        when(jdbcTemplateSchedule.findSchedulesByDoctorId(doctorId)).thenReturn(Collections.emptyList());

        CustomException exception = assertThrows(CustomException.class, () -> scheduleService.getScheduleByDoctorId(doctorId));

        assertEquals(ErrorMessage.SCHEDULE_NOT_FOUND, exception.getErrorMessage());
    }

    @Test
    public void testGetScheduleByPatientId_Success() {
        String patientId = "P001";
        Schedule schedule = new Schedule();
        schedule.setPatientId(patientId);
        schedule.setDate(Date.valueOf(LocalDate.now()));
        schedule.setStartTime(Time.valueOf(LocalTime.now()));
        schedule.setEndTime(Time.valueOf(LocalTime.now().plusMinutes(30)));
        schedule.setStatus(Status.AVAILABLE);

        when(jdbcTemplateSchedule.findSchedulesByPatientId(patientId)).thenReturn(Collections.singletonList(schedule));

        List<Schedule> schedules = scheduleService.getScheduleByPatientId(patientId);

        assertNotNull(schedules);
        assertEquals(1, schedules.size());
        assertEquals(patientId, schedules.get(0).getPatientId());
    }

    @Test
    public void testGetScheduleByPatientId_NotFound() {
        String patientId = "P001";
        when(jdbcTemplateSchedule.findSchedulesByPatientId(patientId)).thenReturn(Collections.emptyList());

        CustomException exception = assertThrows(CustomException.class, () -> scheduleService.getScheduleByPatientId(patientId));

        assertEquals(ErrorMessage.SCHEDULE_NOT_FOUND, exception.getErrorMessage());
    }

    @Test
    public void testBookConsultation_Success() {
        BookConsultationRequest request = new BookConsultationRequest();
        request.setPatientId("P001");
        request.setDate("2023-01-01");
        request.setStartTime("10:00:00");
        request.setSpecialty("Cardiology");

        Doctor doctor = new Doctor();
        doctor.setId("D001");
        doctor.setSpecialty(Specialty.CARDIOLOGY.getCode());

        when(jdbcTemplateSchedule.findAvailableDoctor(any(Specialty.class), any(Date.class), any(Time.class)))
                .thenReturn(Optional.of(doctor));

        assertDoesNotThrow(() -> scheduleService.bookConsultation(request));

        verify(jdbcTemplateSchedule, times(1)).bookConsultation(eq("P001"), eq("D001"), any(Date.class), any(Time.class));
    }

    @Test
    public void testBookConsultation_DoctorNotFound() {
        BookConsultationRequest request = new BookConsultationRequest();
        request.setPatientId("P001");
        request.setDate("2023-01-01");
        request.setStartTime("10:00:00");
        request.setSpecialty("Cardiology");

        when(jdbcTemplateSchedule.findAvailableDoctor(any(Specialty.class), any(Date.class), any(Time.class)))
                .thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> scheduleService.bookConsultation(request));

        assertEquals(ErrorMessage.CONSULTATION_NOT_FOUND, exception.getErrorMessage());
    }

    @Test
    public void testCancelConsultation_Success() {
        String patientId = "P001";
        String startTime = "10:00:00";

        assertDoesNotThrow(() -> scheduleService.cancelConsultation(patientId, startTime));

        verify(jdbcTemplateSchedule, times(1)).cancelConsultation(eq(patientId), eq(startTime));
    }

    @Test
    public void testCancelConsultation_Failure() {
        String patientId = "P001";
        String startTime = "10:00:00";

        doThrow(new CustomException(ErrorMessage.DATABASE_ERROR)).when(jdbcTemplateSchedule).cancelConsultation(anyString(), anyString());

        CustomException exception = assertThrows(CustomException.class, () -> scheduleService.cancelConsultation(patientId, startTime));

        assertEquals(ErrorMessage.DATABASE_ERROR, exception.getErrorMessage());
    }
}