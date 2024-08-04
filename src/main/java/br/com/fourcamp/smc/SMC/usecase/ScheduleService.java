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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing schedules.
 * This class provides methods for saving schedules, retrieving schedules by doctor or patient ID, booking consultations, and canceling consultations.
 */
@Service
public class ScheduleService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    private final JdbcTemplateScheduleImpl jdbcTemplateSchedule;

    public ScheduleService(JdbcTemplateScheduleImpl jdbcTemplateSchedule) {
        this.jdbcTemplateSchedule = jdbcTemplateSchedule;
    }

    /**
     * Saves a new schedule to the database.
     *
     * @param scheduleRequestDto the schedule request data transfer object
     * @throws CustomException if data integrity or database errors occur
     */
    public void saveSchedule(ScheduleRequestDto scheduleRequestDto) {
        LocalDate localDate = LocalDate.parse(scheduleRequestDto.getDate(), DateTimeFormatter.ISO_DATE);
        LocalTime localStartTime = LocalTime.parse(scheduleRequestDto.getStartTime(), DateTimeFormatter.ISO_TIME);
        LocalTime localEndTime = LocalTime.parse(scheduleRequestDto.getEndTime(), DateTimeFormatter.ISO_TIME);

        Date date = Date.valueOf(localDate);
        Time startTime = Time.valueOf(localStartTime);
        Time endTime = Time.valueOf(localEndTime);

        Schedule schedule = Schedule.builder()
                .doctorId(scheduleRequestDto.getDoctorId())
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .status(Status.AVAILABLE)
                .build();
        try {
            jdbcTemplateSchedule.save(schedule);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorMessage.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }

    /**
     * Retrieves schedules by doctor ID.
     *
     * @param doctorId the doctor ID
     * @return a list of schedules for the specified doctor ID
     * @throws CustomException if no schedules are found
     */
    public List<Schedule> getScheduleByDoctorId(String doctorId) {
        logger.info("Fetching schedules for doctorId: {}", doctorId);
        List<Schedule> schedules = jdbcTemplateSchedule.findSchedulesByDoctorId(doctorId);
        if (schedules.isEmpty()) {
            throw new CustomException(ErrorMessage.SCHEDULE_NOT_FOUND);
        }
        logger.info("Found {} schedules for doctorId: {}", schedules.size());
        return schedules;
    }

    /**
     * Retrieves schedules by patient ID.
     *
     * @param patientId the patient ID
     * @return a list of schedules for the specified patient ID
     * @throws CustomException if no schedules are found
     */
    public List<Schedule> getScheduleByPatientId(String patientId) {
        logger.info("Fetching schedules for patientId: {}", patientId);
        List<Schedule> schedules = jdbcTemplateSchedule.findSchedulesByPatientId(patientId);
        if (schedules.isEmpty()) {
            throw new CustomException(ErrorMessage.SCHEDULE_NOT_FOUND);
        }
        logger.info("Found {} schedules for patientId: {}", schedules.size());
        return schedules;
    }

    /**
     * Books a consultation.
     *
     * @param request the book consultation request data transfer object
     * @throws CustomException if the consultation cannot be booked
     */
    public void bookConsultation(BookConsultationRequest request) {
        String patientId = request.getPatientId();
        Date date = Date.valueOf(request.getDate());
        Time startTime = Time.valueOf(request.getStartTime());
        Specialty specialty = Specialty.fromDescription(request.getSpecialty());

        Optional<Doctor> optionalDoctor = jdbcTemplateSchedule.findAvailableDoctor(specialty, date, startTime);

        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            try {
                jdbcTemplateSchedule.bookConsultation(patientId, doctor.getId(), date, startTime);
            } catch (DataIntegrityViolationException e) {
                throw new CustomException(ErrorMessage.DATA_INTEGRITY_VIOLATION);
            } catch (Exception e) {
                throw new CustomException(ErrorMessage.DATABASE_ERROR);
            }
        } else {
            throw new CustomException(ErrorMessage.CONSULTATION_NOT_FOUND);
        }
    }

    /**
     * Cancels a consultation.
     *
     * @param patientId the patient ID
     * @param startTime the start time of the consultation
     * @throws CustomException if the consultation cannot be canceled
     */
    public void cancelConsultation(String patientId, String startTime) {
        try {
            jdbcTemplateSchedule.cancelConsultation(patientId, startTime);
        } catch (Exception e) {
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }
}