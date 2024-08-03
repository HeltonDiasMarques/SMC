package br.com.fourcamp.smc.SMC.dao;

import br.com.fourcamp.smc.SMC.model.Schedule;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface IJdbcTemplateScheduleDao {
    void save(Schedule schedule);
    boolean existsDoctorById(String doctorId);
    List<Schedule> findSchedulesByDoctorId(String doctorId);

    List<Schedule> findSchedulesByPatientId(String patientId);

    void bookConsultation(String patientId, String doctorId, Date date, Time startTime);
}