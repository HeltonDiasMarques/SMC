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

@Service
public class PatientService extends UserService<Patient> {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PatientService(@Qualifier("patientDao") IJdbcTemplateUserDao<Patient> iJdbcTemplateUserDao, CepService cepService, PasswordEncoder passwordEncoder) {
        super(iJdbcTemplateUserDao, cepService);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(Patient patient, Class<Patient> clazz) {
        patient.setUserType(UserType.PATIENT);
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        super.saveUser(patient, clazz);
    }

    @Override
    public void updateUser(Patient patient, Class<Patient> clazz) {
        patient.setUserType(UserType.PATIENT);
        super.updateUser(patient, clazz);
    }

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