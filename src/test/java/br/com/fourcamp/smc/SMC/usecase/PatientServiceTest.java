package br.com.fourcamp.smc.SMC.usecase;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.UserType;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Patient;
import br.com.fourcamp.smc.SMC.utils.CepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PatientServiceTest {
        //Mock não passa de uma espécie de simulação
        @Mock
        private IJdbcTemplateUserDao<Patient> patientDao;

        @Mock
        private CepService cepService;

        @Mock
        private PasswordEncoder passwordEncoder;

        @InjectMocks
        private PatientService patientService;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testLoginSuccess() {
            // Mock the patient and setup mock behaviors
            Patient mockPatient = new Patient();
            mockPatient.setPassword("encodedPassword");

            when(patientDao.findByEmail(anyString(), eq(Patient.class))).thenReturn(Optional.of(mockPatient));
            when(passwordEncoder.matches(anyString(), eq("encodedPassword"))).thenReturn(true);

            // Call the method to test
            Patient result = patientService.login("email@test.com", "rawPassword");

            // Assertions
            assertNotNull(result);
            verify(patientDao, times(1)).findByEmail(anyString(), eq(Patient.class));
            verify(passwordEncoder, times(1)).matches(anyString(), eq("encodedPassword"));
        }

        @Test
        void testLoginFailure_InvalidPassword() {
            // Mock the patient and setup mock behaviors
            Patient mockPatient = new Patient();
            mockPatient.setPassword("encodedPassword");

            when(patientDao.findByEmail(anyString(), eq(Patient.class))).thenReturn(Optional.of(mockPatient));
            when(passwordEncoder.matches(anyString(), eq("encodedPassword"))).thenReturn(false);

            // Call the method and expect exception
            assertThrows(CustomException.class, () -> patientService.login("email@test.com", "wrongPassword"));
        }

        @Test
        void testSaveUser() {
            Patient mockPatient = new Patient();
            mockPatient.setPassword("rawPassword");

            when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

            // Call the method to test
            patientService.saveUser(mockPatient, Patient.class);

            // Assertions
            assertEquals("encodedPassword", mockPatient.getPassword());
            assertEquals(UserType.PATIENT, mockPatient.getUserType());
            verify(patientDao, times(1)).save(any(Patient.class), eq(Patient.class));
        }
}

