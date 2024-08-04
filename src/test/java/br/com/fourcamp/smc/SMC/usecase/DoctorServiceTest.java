package br.com.fourcamp.smc.SMC.usecase;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.enums.Specialty;
import br.com.fourcamp.smc.SMC.enums.UserType;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Address;
import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.utils.CepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DoctorServiceTest {

    private IJdbcTemplateUserDao<Doctor> mockDoctorDao;
    private CepService mockCepService;
    private PasswordEncoder mockPasswordEncoder;
    private DoctorService doctorService;

    @BeforeEach
    public void setUp() {
        mockDoctorDao = mock(IJdbcTemplateUserDao.class);
        mockCepService = mock(CepService.class);
        mockPasswordEncoder = mock(PasswordEncoder.class);
        doctorService = new DoctorService(mockDoctorDao, mockCepService, mockPasswordEncoder);
    }

    @Test
    public void testSaveUser_Success() {
        Doctor doctor = createValidDoctor();
        Address address = createValidAddress();

        when(mockCepService.getAddressByCep(anyString())).thenReturn(address);
        when(mockCepService.isValidCep(anyString())).thenReturn(true);
        when(mockDoctorDao.existsByCpf(anyString(), any())).thenReturn(false);
        when(mockDoctorDao.existsByEmail(anyString(), any())).thenReturn(false);
        when(mockDoctorDao.existsByPhone(anyString(), any())).thenReturn(false);
        when(mockDoctorDao.existsByCrm(anyString())).thenReturn(false);
        when(mockPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");

        assertDoesNotThrow(() -> doctorService.saveUser(doctor, Doctor.class));

        ArgumentCaptor<Doctor> doctorCaptor = ArgumentCaptor.forClass(Doctor.class);
        verify(mockDoctorDao).save(doctorCaptor.capture(), eq(Doctor.class));

        Doctor savedDoctor = doctorCaptor.getValue();
        assertNotNull(savedDoctor.getId());
        assertEquals("encodedPassword", savedDoctor.getPassword());
        assertEquals(UserType.DOCTOR, savedDoctor.getUserType());
        assertEquals(Specialty.CARDIOLOGY.getDescription(), Specialty.fromCode(savedDoctor.getSpecialty()).getDescription());
    }

    @Test
    public void testUpdateUser_Success() {
        Doctor existingDoctor = createValidDoctor();
        existingDoctor.setId("D001");

        Doctor updatedDoctor = createValidDoctor();
        updatedDoctor.setId("D001");
        updatedDoctor.setPhone("21954778207");
        updatedDoctor.setSpecialty(Specialty.DERMATOLOGY.getCode());

        Address address = createValidAddress();

        when(mockDoctorDao.findById(anyString(), any())).thenReturn(Optional.of(existingDoctor));
        when(mockCepService.getAddressByCep(anyString())).thenReturn(address);
        when(mockCepService.isValidCep(anyString())).thenReturn(true);

        assertDoesNotThrow(() -> doctorService.updateUser(updatedDoctor, Doctor.class));

        ArgumentCaptor<Doctor> doctorCaptor = ArgumentCaptor.forClass(Doctor.class);
        verify(mockDoctorDao).update(doctorCaptor.capture(), eq(Doctor.class));

        Doctor savedDoctor = doctorCaptor.getValue();
        assertEquals("21954778207", savedDoctor.getPhone());
        assertEquals(UserType.DOCTOR, savedDoctor.getUserType());
        assertEquals(Specialty.DERMATOLOGY.getDescription(), Specialty.fromCode(savedDoctor.getSpecialty()).getDescription());
    }

    @Test
    public void testLogin_Success() {
        Doctor doctor = createValidDoctor();
        doctor.setPassword("encodedPassword");

        when(mockDoctorDao.findByEmail(anyString(), any())).thenReturn(Optional.of(doctor));
        when(mockPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        assertDoesNotThrow(() -> {
            Doctor loggedInDoctor = doctorService.login("john.doe@example.com", "password");
            assertEquals(doctor, loggedInDoctor);
        });
    }

    @Test
    public void testLogin_InvalidPassword() {
        Doctor doctor = createValidDoctor();
        doctor.setPassword("encodedPassword");

        when(mockDoctorDao.findByEmail(anyString(), any())).thenReturn(Optional.of(doctor));
        when(mockPasswordEncoder.matches(anyString(), anyString())).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            doctorService.login("john.doe@example.com", "wrongPassword");
        });

        assertEquals(ErrorMessage.INVALID_PASSWORD.getMessage(), exception.getMessage());
    }

    private Doctor createValidDoctor() {
        return new Doctor("D001", "Dr. John Doe", "john.doe@example.com", "securePass123",
                "514.933.118-08", "1990-01-01", createValidAddress(), "11987654321", "145689-SP",
                Specialty.CARDIOLOGY.getCode());
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