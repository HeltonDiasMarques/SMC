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
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService extends UserService<Doctor> {
    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);
    @Autowired
    public DoctorService(@Qualifier("doctorDao") IJdbcTemplateUserDao<Doctor> iJdbcTemplateUserDao, CepService cepService) {
        super(iJdbcTemplateUserDao, cepService);
    }

    @Override
    public void saveUser(Doctor doctor, Class<Doctor> clazz) {
        validateDoctor(doctor, true);
        doctor.setUserType(UserType.DOCTOR);
        super.saveUser(doctor, clazz);
    }

    @Override
    public void updateUser(Doctor doctor, Class<Doctor> clazz) {
        validateDoctor(doctor, false);
        doctor.setUserType(UserType.DOCTOR);
        super.updateUser(doctor, clazz);
    }

    public Doctor login(String id, String password) {
        Doctor doctor = getJdbcTemplateUserDao().findById(id, Doctor.class)
                .orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
        if (doctor.getPassword().equals(password)) {
            return doctor;
        } else {
            throw new CustomException(ErrorMessage.INVALID_PASSWORD);
        }
    }

    private boolean isCrmDuplicated(String crm) {
        boolean exists = getJdbcTemplateUserDao().existsByCrm(crm);
        logger.info("CRM {} duplication check: {}", crm, exists);
        return exists;
    }

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