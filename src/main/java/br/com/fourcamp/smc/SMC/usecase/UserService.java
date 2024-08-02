package br.com.fourcamp.smc.SMC.usecase;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Address;
import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.model.Patient;
import br.com.fourcamp.smc.SMC.model.User;
import br.com.fourcamp.smc.SMC.utils.CepService;
import br.com.fourcamp.smc.SMC.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public abstract class UserService<U extends User> {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final IJdbcTemplateUserDao<U> iJdbcTemplateUserDao;
    private final CepService cepService;

    @Autowired
    public UserService(IJdbcTemplateUserDao<U> iJdbcTemplateUserDao, CepService cepService) {
        this.iJdbcTemplateUserDao = iJdbcTemplateUserDao;
        this.cepService = cepService;
    }

    protected IJdbcTemplateUserDao<U> getJdbcTemplateUserDao() {
        return iJdbcTemplateUserDao;
    }

    public void saveUser(U user, Class<U> clazz) {
        try {
            validateUser(user, true);
            user.setId(generateUserId(clazz));
            Address completeAddress = cepService.getAddressByCep(user.getAddress().getCep());
            completeAddress.setNumber(user.getAddress().getNumber());
            user.setAddress(completeAddress);
            iJdbcTemplateUserDao.save(user, clazz);
        } catch (CustomException e) {
            logger.error("Validation error: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Error saving user", e);
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }

    public void updateUser(U user, Class<U> clazz) {
        try {
            Optional<U> existingUserOptional = iJdbcTemplateUserDao.findById(user.getId(), clazz);
            U existingUser = existingUserOptional.orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));

            String normalizedExistingCpf = CpfValidator.normalizeAndValidate(existingUser.getCpf());
            String normalizedUpdatedCpf = CpfValidator.normalizeAndValidate(user.getCpf());

            if (!normalizedExistingCpf.equals(normalizedUpdatedCpf)) {
                throw new CustomException(ErrorMessage.INVALID_CPF);
            }

            validateUser(user, false);

            Address completeAddress = cepService.getAddressByCep(user.getAddress().getCep());
            completeAddress.setNumber(user.getAddress().getNumber());
            user.setAddress(completeAddress);

            iJdbcTemplateUserDao.update(user, clazz);
        } catch (CustomException e) {
            logger.error("Validation error: "+ e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Error updating user", e);
            throw new CustomException(ErrorMessage.DATABASE_ERROR);
        }
    }

    public Optional<U> findUserById(String id, Class<U> clazz) {
        return iJdbcTemplateUserDao.findById(id, clazz);
    }

    public List<U> findAllUsers(Class<U> clazz) {
        return iJdbcTemplateUserDao.findAll(clazz);
    }

    private String generateUserId(Class<? extends User> clazz) {
        String prefix = clazz.equals(Patient.class) ? "P" : clazz.equals(Doctor.class) ? "D" : "U";
        Random random = new Random();
        int suffix = random.nextInt(900) + 100;
        return String.format("%s%03d", prefix, suffix);
    }

    protected void validateUser(U user, boolean isNew) {
        NotEmptyValidator.validate(user);
        CpfValidator.validateCpf(user, isNew, iJdbcTemplateUserDao);
        EmailValidator.validateEmail(user, isNew, iJdbcTemplateUserDao);
        PhoneValidator.validatePhone(user, isNew, iJdbcTemplateUserDao);
        BirthDateValidator.validateDateOfBirth(user);
        CepValidator.validateCep(user, cepService);

        if (user instanceof Doctor) {
            CrmValidator.validateCrm((Doctor) user, isNew, (IJdbcTemplateUserDao<Doctor>) iJdbcTemplateUserDao);
        }

        if (user instanceof Patient) {
            PhoneSpareValidator.validatePhoneSpare((Patient) user);
        }
    }
}