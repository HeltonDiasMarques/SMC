package br.com.fourcamp.smc.SMC.usecase;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.enums.UserType;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.utils.CepService;
import br.com.fourcamp.smc.SMC.utils.CrmValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorServiceTest {

    @Mock
    private IJdbcTemplateUserDao<Doctor> doctorDao;

    @Mock
    private CepService cepService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DoctorService doctorService;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doctor = new Doctor();
        doctor.setId(String.valueOf(1L));
        doctor.setEmail("test@example.com");
        doctor.setPassword("password");
        doctor.setCrm("12345");
    }

    @Test
    void testSaveUserSuccess() {
        // Arrange
        when(passwordEncoder.encode(doctor.getPassword())).thenReturn("encodedPassword");
        when(doctorDao.existsByCrm(doctor.getCrm())).thenReturn(false);
        when(CrmValidator.isValid(doctor.getCrm())).thenReturn(true);

        // Act
        doctorService.saveUser(doctor, Doctor.class);

        // Assert
        assertEquals(UserType.DOCTOR, doctor.getUserType());
        assertEquals("encodedPassword", doctor.getPassword());
        verify(doctorDao, times(1)).save(doctor);
    }

    @Test
    void testSaveUserCrmDuplicated() {
        // Arrange
        when(CrmValidator.isValid(doctor.getCrm())).thenReturn(true);
        when(doctorDao.existsByCrm(doctor.getCrm())).thenReturn(true);

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> doctorService.saveUser(doctor, Doctor.class));
        assertEquals(ErrorMessage.CRM_ALREADY_REGISTERED, exception.getMessage());
    }

    @Test
    void testSaveUserInvalidCrm() {
        // Arrange
        when(CrmValidator.isValid(doctor.getCrm())).thenReturn(false);

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> doctorService.saveUser(doctor, Doctor.class));
        assertEquals(ErrorMessage.INVALID_CRM_FORMAT, exception.getMessage());
    }

    @Test
    void testUpdateUserSuccess() {
        // Arrange
        when(CrmValidator.isValid(doctor.getCrm())).thenReturn(true);
        when(doctorDao.findById(doctor.getId(), Doctor.class)).thenReturn(Optional.of(doctor));
        when(doctorDao.existsByCrm(doctor.getCrm())).thenReturn(false);

        // Act
        doctorService.updateUser(doctor, Doctor.class);

        // Assert
        assertEquals(UserType.DOCTOR, doctor.getUserType());
        verify(doctorDao, times(1)).update(doctor);
    }

    @Test
    void testUpdateUserCrmDuplicated() {
        // Arrange
        when(CrmValidator.isValid(doctor.getCrm())).thenReturn(true);
        when(doctorDao.findById(doctor.getId(), Doctor.class)).thenReturn(Optional.of(doctor));
        when(doctorDao.existsByCrm(doctor.getCrm())).thenReturn(true);

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> doctorService.updateUser(doctor, Doctor.class));
        assertEquals(ErrorMessage.CRM_ALREADY_REGISTERED, exception.getMessage());
    }

    @Test
    void testUpdateUserInvalidCrm() {
        // Arrange
        when(CrmValidator.isValid(doctor.getCrm())).thenReturn(false);

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> doctorService.updateUser(doctor, Doctor.class));
        assertEquals(ErrorMessage.INVALID_CRM_FORMAT, exception.getMessage());
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        when(doctorDao.findByEmail(doctor.getEmail(), Doctor.class)).thenReturn(Optional.of(doctor));
        when(passwordEncoder.matches("password", doctor.getPassword())).thenReturn(true);

        // Act
        Doctor loggedInDoctor = doctorService.login(doctor.getEmail(), "password");

        // Assert
        assertNotNull(loggedInDoctor);
        assertEquals(doctor, loggedInDoctor);
    }

    @Test
    void testLoginUserNotFound() {
        // Arrange
        when(doctorDao.findByEmail(doctor.getEmail(), Doctor.class)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> doctorService.login(doctor.getEmail(), "password"));
        assertEquals(ErrorMessage.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testLoginInvalidPassword() {
        // Arrange
        when(doctorDao.findByEmail(doctor.getEmail(), Doctor.class)).thenReturn(Optional.of(doctor));
        when(passwordEncoder.matches("password", doctor.getPassword())).thenReturn(false);

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> doctorService.login(doctor.getEmail(), "password"));
        assertEquals(ErrorMessage.INVALID_PASSWORD, exception.getMessage());
    }

    @Test
    void testDeleteUserSuccess() {
        // Arrange
        when(doctorDao.findById(doctor.getId(), Doctor.class)).thenReturn(Optional.of(doctor));

        // Act
        doctorService.deleteUser(doctor.getId(), Doctor.class);

        // Assert
        verify(doctorDao, times(1)).deleteById(doctor.getId(), Doctor.class);
    }

    @Test
    void testDeleteUserNotFound() {
        // Arrange
        when(doctorDao.findById(doctor.getId(), Doctor.class)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> doctorService.deleteUser(doctor.getId(), Doctor.class));
        assertEquals(ErrorMessage.USER_NOT_FOUND, exception.getMessage());
    }
}