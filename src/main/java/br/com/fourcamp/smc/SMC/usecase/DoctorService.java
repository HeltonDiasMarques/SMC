package br.com.fourcamp.smc.SMC.usecase;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.enums.UserType;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.utils.CepService;
import br.com.fourcamp.smc.SMC.utils.CrmValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing doctors.
 * This class provides methods specific to doctors, including saving, updating, and logging in.
 */
@Service
public class DoctorService extends UserService<Doctor> {
    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public DoctorService(@Qualifier("doctorDao") IJdbcTemplateUserDao<Doctor> iJdbcTemplateUserDao, CepService cepService, PasswordEncoder passwordEncoder) {
        super(iJdbcTemplateUserDao, cepService);
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves a new doctor to the database.
     *
     * @param doctor the doctor to save
     * @param clazz  the class of the doctor
     * @throws CustomException if validation or database errors occur
     */

    @Override
    public void saveUser(Doctor doctor, Class<Doctor> clazz) {
        validateDoctor(doctor, true);
        doctor.setUserType(UserType.DOCTOR);
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        super.saveUser(doctor, clazz);
    }

    /**
     * Updates an existing doctor in the database.
     *
     * @param doctor the doctor to update
     * @param clazz  the class of the doctor
     * @throws CustomException if validation or database errors occur
     */
    @Override
    public void updateUser(Doctor doctor, Class<Doctor> clazz) {
        validateDoctor(doctor, false);
        doctor.setUserType(UserType.DOCTOR);
        super.updateUser(doctor, clazz);
    }

    /**
     * Logs in a doctor using email and password.
     *
     * @param email    the email of the doctor
     * @param password the password of the doctor
     * @return the authenticated doctor
     * @throws CustomException if the doctor is not found or the password is incorrect
     */
    public Doctor login(String email, String password) {
        Doctor doctor = getJdbcTemplateUserDao().findByEmail(email, Doctor.class)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
        if (passwordEncoder.matches(password, doctor.getPassword())) {
            return doctor;
        } else {
            throw new CustomException(ErrorMessage.INVALID_PASSWORD);
        }
    }

    /**
     * Checks if a CRM is duplicated.
     *
     * @param crm the CRM to check
     * @return true if the CRM is duplicated, false otherwise
     */
    private boolean isCrmDuplicated(String crm) {
        boolean exists = getJdbcTemplateUserDao().existsByCrm(crm);
        logger.info("CRM {} duplication check: {}", crm, exists);
        return exists;
    }

    /**
     * Validates a doctor.
     *
     * @param doctor the doctor to validate
     * @param isNew  indicates if the doctor is new (true) or existing (false)
     * @throws CustomException if validation errors occur
     */
    private void validateDoctor(Doctor doctor, boolean isNew) {
        validateUser(doctor, isNew);

        if (!CrmValidator.isValid(doctor.getCrm())) {
            throw new CustomException(ErrorMessage.INVALID_CRM_FORMAT);
        }

        if (isNew) {
            if (isCrmDuplicated(doctor.getCrm())) {
                throw new CustomException(ErrorMessage.CRM_ALREADY_REGISTERED);
            }
        } else {
            Optional<Doctor> existingDoctor = findUserById(doctor.getId(), Doctor.class);
            if (existingDoctor.isPresent() && !existingDoctor.get().getCrm().equals(doctor.getCrm())) {
                if (isCrmDuplicated(doctor.getCrm())) {
                    throw new CustomException(ErrorMessage.CRM_ALREADY_REGISTERED);
                }
            }
        }
    }
}