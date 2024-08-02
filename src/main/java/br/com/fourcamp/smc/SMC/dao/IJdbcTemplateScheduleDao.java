package br.com.fourcamp.smc.SMC.dao;

import br.com.fourcamp.smc.SMC.model.Schedule;
import java.util.List;

public interface IJdbcTemplateScheduleDao {
    void save(Schedule schedule);
    boolean existsDoctorById(String doctorId);
    List<Schedule> findSchedulesByDoctorId(String doctorId);

    List<Schedule> findSchedulesByPatientId(String patientId);
}