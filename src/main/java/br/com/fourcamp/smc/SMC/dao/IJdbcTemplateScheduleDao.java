package br.com.fourcamp.smc.SMC.dao;

import br.com.fourcamp.smc.SMC.model.Schedule;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Interface for DAO operations related to Schedule using JdbcTemplate.
 */
public interface IJdbcTemplateScheduleDao {
    /**
     * Saves a schedule.
     *
     * @param schedule the schedule to save
     */
    void save(Schedule schedule);



    /**
     * Checks if a doctor exists by their ID.
     *
     * @param doctorId the ID of the doctor
     * @return true if the doctor exists, false otherwise
     */
    boolean existsDoctorById(String doctorId);

    /**
     * Finds schedules by doctor ID.
     *
     * @param doctorId the ID of the doctor
     * @return a list of schedules for the doctor
     */
    List<Schedule> findSchedulesByDoctorId(String doctorId);

    /**
     * Finds schedules by patient ID.
     *
     * @param patientId the ID of the patient
     * @return a list of schedules for the patient
     */
    List<Schedule> findSchedulesByPatientId(String patientId);

    /**
     * Books a consultation.
     *
     * @param patientId the ID of the patient
     * @param doctorId the ID of the doctor
     * @param date the date of the consultation
     * @param startTime the start time of the consultation
     */
    void bookConsultation(String patientId, String doctorId, Date date, Time startTime);
}