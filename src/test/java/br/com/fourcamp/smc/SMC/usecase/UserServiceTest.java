package br.com.fourcamp.smc.SMC.usecase;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Address;
import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.model.Patient;
import br.com.fourcamp.smc.SMC.utils.CepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private IJdbcTemplateUserDao<Patient> mockPatientDao;
    private IJdbcTemplateUserDao<Doctor> mockDoctorDao;
    private CepService mockCepService;
    private UserService<Patient> patientService;
    private UserService<Doctor> doctorService;

    @BeforeEach
    public void setUp() {
        mockPatientDao = mock(IJdbcTemplateUserDao.class);
        mockDoctorDao = mock(IJdbcTemplateUserDao.class);
        mockCepService = mock(CepService.class);

        patientService = new PatientService(mockPatientDao, mockCepService);
        doctorService = new DoctorService(mockDoctorDao, mockCepService);
    }

    @Test
    public void testSavePatient_Success() {
        Patient patient = createValidPatient();
        Address address = createValidAddress();

        // Mocking CepService to return a valid address and valid CEP
        when(mockCepService.getAddressByCep(anyString())).thenReturn(address);
        when(mockCepService.isValidCep(anyString())).thenReturn(true);
        when(mockPatientDao.existsByCpf(anyString(), any())).thenReturn(false);
        when(mockPatientDao.existsByEmail(anyString(), any())).thenReturn(false);
        when(mockPatientDao.existsByPhone(anyString(), any())).thenReturn(false);

        assertDoesNotThrow(() -> patientService.saveUser(patient, Patient.class));

        ArgumentCaptor<Patient> patientCaptor = ArgumentCaptor.forClass(Patient.class);
        verify(mockPatientDao).save(patientCaptor.capture(), eq(Patient.class));

        Patient savedPatient = patientCaptor.getValue();
        assertNotNull(savedPatient.getId());
        assertEquals("11954778207", savedPatient.getPhone());
    }

    @Test
    public void testUpdatePatient_Success() {
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
    }

    @Test
    public void testSavePatient_DatabaseError() {
        Patient patient = createValidPatient();
        Address address = createValidAddress();

        when(mockCepService.getAddressByCep(anyString())).thenReturn(address);
        when(mockCepService.isValidCep(anyString())).thenReturn(true);
        doThrow(new RuntimeException("Database error")).when(mockPatientDao).save(any(Patient.class), any());

        CustomException exception = assertThrows(CustomException.class, () -> patientService.saveUser(patient, Patient.class));
        assertEquals(ErrorMessage.DATABASE_ERROR.getMessage(), exception.getMessage());
    }

    @Test
    public void testUpdatePatient_UserNotFound() {
        Patient patient = createValidPatient();
        patient.setId("P001");

        when(mockPatientDao.findById(anyString(), any())).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> patientService.updateUser(patient, Patient.class));
        assertEquals(ErrorMessage.USER_NOT_FOUND.getMessage(), exception.getMessage());
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

    // Subclasses concretas para teste
    private static class PatientService extends UserService<Patient> {
        public PatientService(IJdbcTemplateUserDao<Patient> iJdbcTemplateUserDao, CepService cepService) {
            super(iJdbcTemplateUserDao, cepService);
        }
    }

    private static class DoctorService extends UserService<Doctor> {
        public DoctorService(IJdbcTemplateUserDao<Doctor> iJdbcTemplateUserDao, CepService cepService) {
            super(iJdbcTemplateUserDao, cepService);
        }
    }
}