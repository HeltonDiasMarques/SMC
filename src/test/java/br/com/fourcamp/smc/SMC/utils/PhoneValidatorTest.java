package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Address;
import br.com.fourcamp.smc.SMC.model.Patient;
import br.com.fourcamp.smc.SMC.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneValidatorTest {

    @Test
    public void testNormalizeAndValidate_ValidPhone() {
        String phone = "11954778207";
        String normalizedPhone = PhoneValidator.normalizeAndValidate(phone);
        assertEquals("11954778207", normalizedPhone);
    }

    @Test
    public void testNormalizeAndValidate_ValidPhoneWithFormat() {
        String phone = "(11) 95477-8207";
        String normalizedPhone = PhoneValidator.normalizeAndValidate(phone);
        assertEquals("11954778207", normalizedPhone);
    }

    @Test
    public void testNormalizeAndValidate_InvalidPhoneFormat() {
        String phone = "95477-8207";
        CustomException exception = assertThrows(CustomException.class, () -> PhoneValidator.normalizeAndValidate(phone));
        assertEquals(ErrorMessage.INVALID_PHONE_FORMAT.getMessage(), exception.getMessage());
    }

    @Test
    public void testIsValid_ValidPhone() {
        assertTrue(PhoneValidator.isValid("11954778207"));
    }

    @Test
    public void testIsValid_InvalidPhone() {
        assertFalse(PhoneValidator.isValid("954778207"));
    }

    @Test
    public void testValidatePhone_ValidPhone() {
        Patient patient = createValidPatient("11954778208"); // Número que não está no mock
        assertDoesNotThrow(() -> PhoneValidator.validatePhone(patient, true, new MockUserDao()));
    }

    @Test
    public void testValidatePhone_InvalidPhone() {
        Patient patient = createValidPatient("954778207");
        CustomException exception = assertThrows(CustomException.class, () -> PhoneValidator.validatePhone(patient, true, new MockUserDao()));
        assertEquals(ErrorMessage.INVALID_PHONE_FORMAT.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidatePhone_PhoneAlreadyExists() {
        Patient patient = createValidPatient("11954778207"); // Número já existente no mock
        CustomException exception = assertThrows(CustomException.class, () -> PhoneValidator.validatePhone(patient, true, new MockUserDao()));
        assertEquals(ErrorMessage.PHONE_ALREADY_EXISTS.getMessage(), exception.getMessage());
    }

    private Patient createValidPatient(String phone) {
        Patient patient = new Patient();
        patient.setName("Jane Doe");
        patient.setEmail("jane.doe@example.com");
        patient.setPassword("password");
        patient.setCpf("98765432100");
        patient.setDatebirth(String.valueOf(LocalDate.of(1995, 2, 2)));
        patient.setAddress(Address.builder()
                .cep("12345678")
                .number("123")
                .street("Main Street")
                .neighborhood("Downtown")
                .city("City")
                .state("ST")
                .build());
        patient.setPhone(phone);
        return patient;
    }

    private static class MockUserDao implements IJdbcTemplateUserDao<Patient> {

        @Override
        public void save(Patient user, Class<Patient> clazz) {
            // Mock implementation
        }

        @Override
        public void update(Patient user, Class<Patient> clazz) {
            // Mock implementation
        }

        @Override
        public Optional<Patient> findById(String id, Class<Patient> clazz) {
            return Optional.empty(); // Mock implementation
        }

        @Override
        public Optional<Patient> findByEmail(String email, Class<Patient> clazz) {
            return Optional.empty(); // Mock implementation
        }

        @Override
        public List<Patient> findAll(Class<Patient> clazz) {
            return List.of(); // Mock implementation
        }

        @Override
        public boolean existsByCpf(String cpf, Class<Patient> clazz) {
            return false; // Mock implementation
        }

        @Override
        public boolean existsByEmail(String email, Class<Patient> clazz) {
            return false; // Mock implementation
        }

        @Override
        public boolean existsByPhone(String phone, Class<Patient> clazz) {
            return "11954778207".equals(phone); // Retorna true apenas para o número específico
        }

        @Override
        public boolean existsByCrm(String crm) {
            return false; // Mock implementation
        }
    }
}