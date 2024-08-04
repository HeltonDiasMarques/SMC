package br.com.fourcamp.smc.SMC.config.security;

import br.com.fourcamp.smc.SMC.dao.impl.JdbcTemplateUserImpl;
import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Custom implementation of UserDetailsService for loading user-specific data.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private JdbcTemplateUserImpl<Patient> patientRepository;

    @Autowired
    private JdbcTemplateUserImpl<Doctor> doctorRepository;

    /**
     * Loads the user by their email.
     *
     * @param email the email of the user
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Looking for user with email: {}", email);
        Patient patient = patientRepository.findByEmail(email, Patient.class).orElse(null);
        Doctor doctor = doctorRepository.findByEmail(email, Doctor.class).orElse(null);

        if (patient != null) {
            logger.info("User found: {}", patient);
            return new User(patient.getEmail(), patient.getPassword(), new ArrayList<>());
        } else if (doctor != null) {
            logger.info("User found: {}", doctor);
            return new User(doctor.getEmail(), doctor.getPassword(), new ArrayList<>());
        } else {
            logger.error("User not found with email: {}", email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}