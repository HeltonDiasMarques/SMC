package br.com.fourcamp.smc.SMC.dao.impl;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.Specialty;
import br.com.fourcamp.smc.SMC.enums.UserType;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Address;
import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.model.Patient;
import br.com.fourcamp.smc.SMC.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateUserImpl<U extends User> implements IJdbcTemplateUserDao<U> {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(U user, Class<U> clazz) {
        String tableName = clazz.equals(Patient.class) ? "patient" : "doctor";
        int paramCount = clazz.equals(Patient.class) ? 18 : 16;
        String sql = "SELECT save_" + tableName + "(" + String.join(", ", Collections.nCopies(paramCount, "?")) + ")";
        try {
            jdbcTemplate.execute((Connection conn) -> {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    setCommonParameters(ps, user);
                    ps.execute();
                    return null;
                }
            });
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorMessage.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }

    @Override
    public void update(U user, Class<U> clazz) {
        String tableName = clazz.equals(Patient.class) ? "patient" : "doctor";
        int paramCount = clazz.equals(Patient.class) ? 18 : 16;
        String sql = "SELECT update_" + tableName + "(" + String.join(", ", Collections.nCopies(paramCount, "?")) + ")";
        try {
            jdbcTemplate.execute((Connection conn) -> {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    setCommonParameters(ps, user);
                    ps.execute();
                    return null;
                }
            });
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorMessage.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }

    @Override
    public Optional<U> findById(String id, Class<U> clazz) {
        String tableName = clazz.equals(Patient.class) ? "patient" : "doctor";
        String sql = "SELECT * FROM get_" + tableName + "_by_id(?)";
        try {
            return jdbcTemplate.query(sql, new Object[]{id}, rs -> {
                if (rs.next()) {
                    U user = mapRowToUser(rs, clazz);
                    return Optional.of(user);
                } else {
                    return Optional.empty();
                }
            });
        } catch (Exception e) {
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }

    @Override
    public Optional<U> findByEmail(String email, Class<U> clazz) {
        String tableName = clazz.equals(Patient.class) ? "patients" : "doctors";
        String sql = "SELECT * FROM " + tableName + " WHERE email = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{email}, rs -> {
                if (rs.next()) {
                    U user = mapRowToUser(rs, clazz);
                    return Optional.of(user);
                } else {
                    return Optional.empty();
                }
            });
        } catch (Exception e) {
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }

    @Override
    public List<U> findAll(Class<U> clazz) {
        String tableName = clazz.equals(Patient.class) ? "patients" : "doctors";
        String sql = "SELECT * FROM " + tableName;
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToUser(rs, clazz));
        } catch (Exception e) {
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }

    @Override
    public boolean existsByCpf(String cpf, Class<U> clazz) {
        String tableName = clazz.equals(Patient.class) ? "patients" : "doctors";
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE cpf = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, new Object[]{cpf}, Integer.class);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }

    @Override
    public boolean existsByEmail(String email, Class<U> clazz) {
        String tableName = clazz.equals(Patient.class) ? "patients" : "doctors";
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE email = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }

    @Override
    public boolean existsByPhone(String phone, Class<U> clazz) {
        String tableName = clazz.equals(Patient.class) ? "patients" : "doctors";
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE phone = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, new Object[]{phone}, Integer.class);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }

    @Override
    public boolean existsByCrm(String crm) {
        String sql = "SELECT COUNT(*) FROM doctors WHERE crm = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, new Object[]{crm}, Integer.class);
            return count != null && count > 0;
        } catch (Exception e) {
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }

    protected void setCommonParameters(PreparedStatement ps, U user) throws SQLException {
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getPassword());
        ps.setString(5, user.getCpf());
        ps.setString(6, user.getDatebirth());
        ps.setString(7, user.getAddress().getCep());
        ps.setString(8, user.getAddress().getStreet());
        ps.setString(9, user.getAddress().getNeighborhood());
        ps.setString(10, user.getAddress().getCity());
        ps.setString(11, user.getAddress().getState());
        ps.setString(12, user.getAddress().getNumber());
        ps.setString(13, user.getPhone());
        if (user instanceof Patient) {
            ps.setString(14, ((Patient) user).getPhoneSpare());
            ps.setString(15, ((Patient) user).getAllergies());
            ps.setString(16, ((Patient) user).getNotes());
            ps.setString(17, ((Patient) user).getTranscript());
            ps.setString(18, user.getUserType().name());

        } else if (user instanceof Doctor) {
            ps.setString(14, ((Doctor) user).getCrm());
            ps.setString(15, ((Doctor) user).getSpecialtyDescription());
            ps.setString(16, user.getUserType().name());
        } else {
            ps.setString(14, null);
            ps.setString(15, null);
            ps.setString(16, user.getUserType().name());
        }
    }

    private U mapRowToUser(ResultSet rs, Class<U> clazz) throws SQLException {
        Address address = Address.builder()
                .cep(rs.getString("cep"))
                .street(rs.getString("street"))
                .neighborhood(rs.getString("neighborhood"))
                .city(rs.getString("city"))
                .state(rs.getString("state"))
                .number(rs.getString("number"))
                .build();

        UserType userType = UserType.valueOf(rs.getString("user_type"));

        if (clazz.equals(Patient.class)) {
            return clazz.cast(Patient.builder()
                    .id(rs.getString("id"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .cpf(rs.getString("cpf"))
                    .datebirth(rs.getString("datebirth"))
                    .address(address)
                    .phone(rs.getString("phone"))
                    .phoneSpare(rs.getString("phonespare"))
                    .allergies(rs.getString("allergies"))
                    .notes(rs.getString("notes"))
                    .transcript(rs.getString("transcript"))
                    .userType(userType)
                    .build());
        } else if (clazz.equals(Doctor.class)) {
            return clazz.cast(Doctor.builder()
                    .id(rs.getString("id"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .cpf(rs.getString("cpf"))
                    .datebirth(rs.getString("datebirth"))
                    .address(address)
                    .phone(rs.getString("phone"))
                    .crm(rs.getString("crm"))
                    .specialty(Specialty.fromDescription(rs.getString("specialty")).getCode())
                    .userType(userType)
                    .build());
        } else {
            throw new CustomException(ErrorMessage.UNSUPPORTED_USER_TYPE);
        }
    }
}