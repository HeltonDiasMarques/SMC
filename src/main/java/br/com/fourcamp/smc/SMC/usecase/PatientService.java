package br.com.fourcamp.smc.SMC.usecase;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.enums.UserType;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Patient;
import br.com.fourcamp.smc.SMC.utils.CepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PatientService extends UserService<Patient> {
    @Autowired
    public PatientService(@Qualifier("patientDao") IJdbcTemplateUserDao<Patient> iJdbcTemplateUserDao, CepService cepService) {
        super(iJdbcTemplateUserDao, cepService);
    }

    @Override
    public void saveUser(Patient patient, Class<Patient> clazz) {
        patient.setUserType(UserType.PATIENT);
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
        if (patient.getPassword().equals(password)) {
            return patient;
        } else {
            throw new CustomException(ErrorMessage.INVALID_PASSWORD);
        }
    }
}