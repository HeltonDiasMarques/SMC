package br.com.fourcamp.smc.SMC.config;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.dao.impl.JdbcTemplateUserImpl;
import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.model.Patient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Configuration class for setting up DAOs used in the application.
 */
@Configuration
@Tag(name = "DAO Configuration", description = "Configuration for DAOs used in the application")
public class DaoConfig {

    /**
     * Creates a bean for the Doctor DAO.
     *
     * @param jdbcTemplate the JdbcTemplate used for database operations
     * @return an instance of IJdbcTemplateUserDao for Doctor
     */
    @Bean(name = "doctorDao")
    public IJdbcTemplateUserDao<Doctor> doctorDao(JdbcTemplate jdbcTemplate) {
        return new JdbcTemplateUserImpl<>(jdbcTemplate);
    }

    /**
     * Creates a bean for the Patient DAO.
     *
     * @param jdbcTemplate the JdbcTemplate used for database operations
     * @return an instance of IJdbcTemplateUserDao for Patient
     */
    @Bean(name = "patientDao")
    public IJdbcTemplateUserDao<Patient> patientDao(JdbcTemplate jdbcTemplate) {
        return new JdbcTemplateUserImpl<>(jdbcTemplate);
    }
}
