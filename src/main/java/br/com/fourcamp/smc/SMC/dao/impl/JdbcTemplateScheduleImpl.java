package br.com.fourcamp.smc.SMC.dao.impl;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateScheduleDao;
import br.com.fourcamp.smc.SMC.enums.Specialty;
import br.com.fourcamp.smc.SMC.enums.Status;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.model.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the IJdbcTemplateScheduleDao interface using JdbcTemplate.
 */
@Repository
public class JdbcTemplateScheduleImpl implements IJdbcTemplateScheduleDao {
    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplateScheduleImpl.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Schedule schedule) {
        LocalTime startTime = schedule.getStartTime().toLocalTime();
        LocalTime endTime = schedule.getEndTime().toLocalTime();

        while (!startTime.isAfter(endTime.minusMinutes(30))) {
            LocalTime slotEndTime = startTime.plusMinutes(30);

            String conflictSql = "SELECT check_schedule_conflict(?, ?, ?, ?)";
            Boolean conflictExists = jdbcTemplate.queryForObject(conflictSql, Boolean.class,
                    schedule.getDoctorId(),
                    schedule.getDate(),
                    Time.valueOf(startTime),
                    Time.valueOf(slotEndTime));

            if (Boolean.TRUE.equals(conflictExists)) {
                throw new CustomException(ErrorMessage.SCHEDULE_CONFLICT);
            }

            String sql = "SELECT save_schedule(?, ?, ?, ?)";
            LocalTime finalStartTime1 = startTime;
            jdbcTemplate.execute(sql, (PreparedStatement ps) -> {
                ps.setString(1, schedule.getDoctorId());
                ps.setDate(2, new java.sql.Date(schedule.getDate().getTime()));
                ps.setTime(3, Time.valueOf(finalStartTime1));
                ps.setTime(4, Time.valueOf(slotEndTime));
                return ps.execute();
            });
            startTime = slotEndTime;
        }
    }

    @Override
    public boolean existsDoctorById(String doctorId) {
        String sql = "SELECT COUNT(*) FROM doctors WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, doctorId);
        return count != null && count > 0;
    }

    @Override
    public List<Schedule> findSchedulesByDoctorId(String doctorId) {
        String sql = "SELECT * FROM schedules WHERE doctor_id = ?";
        logger.info("Executing query: {} with doctorId: {}", sql, doctorId);
        List<Schedule> schedules = jdbcTemplate.query(sql, new Object[]{doctorId}, this::mapRowToSchedule);
        logger.info("Query result: {} schedules found", schedules.size());
        return schedules;
    }

    @Override
    public List<Schedule> findSchedulesByPatientId(String patientId) {
        String sql = "SELECT * FROM schedules WHERE patient_id = ?";
        logger.info("Executing query: {} with patientId: {}", sql, patientId);
        List<Schedule> schedules = jdbcTemplate.query(sql, new Object[]{patientId}, this::mapRowToSchedule);
        logger.info("Query result: {} schedules found", schedules.size());
        return schedules;
    }

    public Optional<Doctor> findAvailableDoctor(Specialty specialty, Date date, Time startTime) {
        String sql = "SELECT * FROM doctors d " +
                "JOIN schedules s ON d.id = s.doctor_id " +
                "WHERE d.specialty = ? AND s.date = ? AND s.start_time = ? AND s.status = 'AVAILABLE' " +
                "ORDER BY s.start_time " +
                "LIMIT 1";

        int specialtyCode;
        try{
            specialtyCode = Specialty.fromDescription(String.valueOf(specialty)).getCode();
        }catch(IllegalArgumentException e){
            logger.error("Invalid specialty description: {}", specialty);
            return Optional.empty();
        }
        logger.info("Executing findAvailableDoctor with specialty: {}, date: {}, startTime: {}", specialty, date, startTime);

        return jdbcTemplate.query(sql, new Object[]{specialty.getDescription(), date, startTime}, rs -> {
            if (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getString("id"));
                doctor.setName(rs.getString("name"));
                doctor.setEmail(rs.getString("email"));
                doctor.setSpecialty(Specialty.fromDescription(rs.getString("specialty")).getCode());
                logger.info("Available doctor found: {}", doctor.getId());
                return Optional.of(doctor);
            } else {
                logger.warn("No available doctor found.");
                return Optional.empty();
            }
        });
    }

    @Override
    public void bookConsultation(String patientId, String doctorId, Date date, Time startTime) {
        Time endTime = calculateEndTime(startTime);
        String sql = "SELECT book_consultation(?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.execute(sql, (PreparedStatement ps) -> {
                ps.setString(1, patientId);
                ps.setString(2, doctorId);
                ps.setDate(3, new java.sql.Date(date.getTime()));
                ps.setTime(4, startTime);
                ps.setTime(5, endTime);
                ps.execute();
                return null;
            });
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorMessage.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }


    public void cancelConsultation(String patientId, String startTime) {
        String sql = "SELECT cancel_and_register_consultation(?, ?)";
        try {
            jdbcTemplate.execute(sql, (PreparedStatement ps) -> {
                ps.setString(1, patientId);
                ps.setTime(2, Time.valueOf(startTime));
                ps.execute();
                return null;
            });
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorMessage.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }

    private Time calculateEndTime(Time startTime) {
        long millis = startTime.getTime();
        millis += 30 * 60 * 1000;
        return new Time(millis);
    }

    private Schedule mapRowToSchedule(ResultSet rs, int rowNum) throws SQLException {
        return Schedule.builder()
                .id(rs.getString("id"))
                .doctorId(rs.getString("doctor_id"))
                .date(rs.getDate("date"))
                .startTime(rs.getTime("start_time"))
                .endTime(rs.getTime("end_time"))
                .status(Status.valueOf(rs.getString("status")))
                .createdAt(rs.getTimestamp("created_at"))
                .updatedAt(rs.getTimestamp("updated_at"))
                .build();
    }
}