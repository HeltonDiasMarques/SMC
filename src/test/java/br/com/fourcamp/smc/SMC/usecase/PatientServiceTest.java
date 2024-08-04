package br.com.fourcamp.smc.SMC.usecase;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.enums.UserType;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Address;
import br.com.fourcamp.smc.SMC.model.Patient;
import br.com.fourcamp.smc.SMC.utils.CepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    private IJdbcTemplateUserDao<Patient> mockPatientDao;
    private CepService mockCepService;
    private PasswordEncoder mockPasswordEncoder;
    private PatientService patientService;

    @BeforeEach
    public void setUp() {
        mockPatientDao = mock(IJdbcTemplateUserDao.class);
        mockCepService = mock(CepService.class);
        mockPasswordEncoder = mock(PasswordEncoder.class);
        patientService = new PatientService(mockPatientDao, mockCepService, mockPasswordEncoder);
    }

    @Test
    public void testSaveUser_Success() {
        Patient patient = createValidPatient();
        Address address = createValidAddress();

        when(mockCepService.getAddressByCep(anyString())).thenReturn(address);
        when(mockCepService.isValidCep(anyString())).thenReturn(true);
        when(mockPatientDao.existsByCpf(anyString(), any())).thenReturn(false);
        when(mockPatientDao.existsByEmail(anyString(), any())).thenReturn(false);
        when(mockPatientDao.existsByPhone(anyString(), any())).thenReturn(false);
        when(mockPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");

        assertDoesNotThrow(() -> patientService.saveUser(patient, Patient.class));

        ArgumentCaptor<Patient> patientCaptor = ArgumentCaptor.forClass(Patient.class);
        verify(mockPatientDao).save(patientCaptor.capture(), eq(Patient.class));

        Patient savedPatient = patientCaptor.getValue();
        assertNotNull(savedPatient.getId());
        assertEquals("encodedPassword", savedPatient.getPassword());
        assertEquals(UserType.PATIENT, savedPatient.getUserType());
    }

    @Test
    public void testUpdateUser_Success() {
        Patient existingPatient = createValidPatient();
        existingPatient.setId("P001");

        Patient updatedPatient = createValidPatient();
        updatedPatient.setId("P001");
        updatedPatient.setPhone("21954778207");

        Address address = createValidAddress();

        when(mockPatientDao.findById(anyString(), any())).thenReturn(Optional.of(existingPatient));
        when(mockCepService.getAddressByCep(anyString())).thenReturn(address);
        when(mockCepService.isValidCep(anyString())).thenReturn(true);

        assertDoesNotThrow(() -> patientService.updateUser(updatedPatient, Patient.class));

        ArgumentCaptor<Patient> patientCaptor = ArgumentCaptor.forClass(Patient.class);
        verify(mockPatientDao).update(patientCaptor.capture(), eq(Patient.class));

        Patient savedPatient = patientCaptor.getValue();
        assertEquals("21954778207", savedPatient.getPhone());
        assertEquals(UserType.PATIENT, savedPatient.getUserType());
    }

    @Test
    public void testLogin_Success() {
        Patient patient = createValidPatient();
        patient.setPassword("encodedPassword");

        when(mockPatientDao.findByEmail(anyString(), any())).thenReturn(Optional.of(patient));
        when(mockPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        assertDoesNotThrow(() -> {
            Patient loggedInPatient = patientService.login("john.doe@example.com", "password");
            assertEquals(patient, loggedInPatient);
        });
    }

    @Test
    public void testLogin_InvalidPassword() {
        Patient patient = createValidPatient();
        patient.setPassword("encodedPassword");

        when(mockPatientDao.findByEmail(anyString(), any())).thenReturn(Optional.of(patient));
        when(mockPasswordEncoder.matches(anyString(), anyString())).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            patientService.login("john.doe@example.com", "wrongPassword");
        });

        assertEquals(ErrorMessage.INVALID_PASSWORD.getMessage(), exception.getMessage());
    }

    private Patient createValidPatient() {
        Patient patient = new Patient();
        patient.setName("John Doe");
        patient.setEmail("john.doe@example.com");
        patient.setPassword("password");
        patient.setCpf("12345678909");
        patient.setDatebirth(String.valueOf(LocalDate.of(1990, 1, 1)));
        patient.setAddress(createValidAddress());
        patient.setPhone("11954778207");
        return patient;
    }

    private Address createValidAddress() {
        return Address.builder()
                .cep("12345678")
                .number("123")
                .street("Main Street")
                .neighborhood("Downtown")
                .city("City")
                .state("ST")
                .build();
    }
}