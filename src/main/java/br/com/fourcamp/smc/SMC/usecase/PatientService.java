package br.com.fourcamp.smc.SMC.usecase;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.enums.UserType;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Patient;
import br.com.fourcamp.smc.SMC.utils.CepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for managing patients.
 * This class provides methods specific to patients, including saving, updating, and logging in.
 */
@Service
public class PatientService extends UserService<Patient> {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PatientService(@Qualifier("patientDao") IJdbcTemplateUserDao<Patient> iJdbcTemplateUserDao, CepService cepService, PasswordEncoder passwordEncoder) {
        super(iJdbcTemplateUserDao, cepService);
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves a new patient to the database.
     *
     * @param patient the patient to save
     * @param clazz   the class of the patient
     * @throws CustomException if validation or database errors occur
     */
    @Override
    public void saveUser(Patient patient, Class<Patient> clazz) {
        patient.setUserType(UserType.PATIENT);
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        super.saveUser(patient, clazz);
    }

    /**
     * Updates an existing patient in the database.
     *
     * @param patient the patient to update
     * @param clazz   the class of the patient
     * @throws CustomException if validation or database errors occur
     */
    @Override
    public void updateUser(Patient patient, Class<Patient> clazz) {
        patient.setUserType(UserType.PATIENT);
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        super.updateUser(patient, clazz);
    }

    /**
     * Logs in a patient using email and password.
     *
     * @param email    the email of the patient
     * @param password the password of the patient
     * @return the authenticated patient
     * @throws CustomException if the patient is not found or the password is incorrect
     */
    public Patient login(String email, String password) {
        Patient patient = getJdbcTemplateUserDao().findByEmail(email, Patient.class)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
        if (passwordEncoder.matches(password, patient.getPassword())) {
            return patient;
        } else {
            throw new CustomException(ErrorMessage.INVALID_PASSWORD);
        }
    }
}