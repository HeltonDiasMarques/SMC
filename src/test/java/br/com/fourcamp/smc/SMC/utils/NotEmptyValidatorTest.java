package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.enums.Specialty;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Address;
import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.model.Patient;
import br.com.fourcamp.smc.SMC.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class NotEmptyValidatorTest {

    @Test
    public void testValidate_ValidUser() {
        User user = createValidUser();
        assertDoesNotThrow(() -> NotEmptyValidator.validate(user));
    }

    @Test
    public void testValidate_EmptyName() {
        User user = createValidUser();
        user.setName("");
        CustomException exception = assertThrows(CustomException.class, () -> NotEmptyValidator.validate(user));
        assertEquals(ErrorMessage.NAME_CANNOT_BE_EMPTY.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidate_EmptyEmail() {
        User user = createValidUser();
        user.setEmail("");
        CustomException exception = assertThrows(CustomException.class, () -> NotEmptyValidator.validate(user));
        assertEquals(ErrorMessage.EMAIL_CANNOT_BE_EMPTY.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidate_EmptyPassword() {
        User user = createValidUser();
        user.setPassword("");
        CustomException exception = assertThrows(CustomException.class, () -> NotEmptyValidator.validate(user));
        assertEquals(ErrorMessage.PASSWORD_CANNOT_BE_EMPTY.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidate_EmptyCpf() {
        User user = createValidUser();
        user.setCpf("");
        CustomException exception = assertThrows(CustomException.class, () -> NotEmptyValidator.validate(user));
        assertEquals(ErrorMessage.CPF_CANNOT_BE_EMPTY.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidate_EmptyBirthdate() {
        User user = createValidUser();
        user.setDatebirth(null);
        CustomException exception = assertThrows(CustomException.class, () -> NotEmptyValidator.validate(user));
        assertEquals(ErrorMessage.BIRTHDATE_CANNOT_BE_EMPTY.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidate_EmptyCep() {
        User user = createValidUser();
        user.getAddress().setCep("");
        CustomException exception = assertThrows(CustomException.class, () -> NotEmptyValidator.validate(user));
        assertEquals(ErrorMessage.CEP_CANNOT_BE_EMPTY.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidate_EmptyAddressNumber() {
        User user = createValidUser();
        user.getAddress().setNumber("");
        CustomException exception = assertThrows(CustomException.class, () -> NotEmptyValidator.validate(user));
        assertEquals(ErrorMessage.ADDRESS_NUMBER_CANNOT_BE_EMPTY.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidate_EmptyPhone() {
        User user = createValidUser();
        user.setPhone("");
        CustomException exception = assertThrows(CustomException.class, () -> NotEmptyValidator.validate(user));
        assertEquals(ErrorMessage.PHONE_CANNOT_BE_EMPTY.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidate_EmptyPhoneSpare_Patient() {
        Patient patient = createValidPatient();
        patient.setPhoneSpare("");
        CustomException exception = assertThrows(CustomException.class, () -> NotEmptyValidator.validate(patient));
        assertEquals(ErrorMessage.PHONE_SPARE_CANNOT_BE_EMPTY.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidate_EmptyCrm_Doctor() {
        Doctor doctor = createValidDoctor();
        doctor.setCrm("");
        CustomException exception = assertThrows(CustomException.class, () -> NotEmptyValidator.validate(doctor));
        assertEquals(ErrorMessage.CRM_CANNOT_BE_EMPTY.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidate_EmptySpecialty_Doctor() {
        Doctor doctor = createValidDoctor();
        doctor.setSpecialty(-1);
        CustomException exception = assertThrows(CustomException.class, () -> NotEmptyValidator.validate(doctor));
        assertEquals(ErrorMessage.SPECIALTY_CANNOT_BE_EMPTY.getMessage(), exception.getMessage());
    }

    private User createValidUser() {
        Address address = Address.builder()
                .cep("12345678")
                .number("123")
                .street("Main Street")
                .neighborhood("Downtown")
                .city("City")
                .state("ST")
                .build();

        return User.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("password")
                .cpf("123.456.789-00")
                .datebirth(String.valueOf(LocalDate.of(1990, 1, 1)))
                .address(address)
                .phone("1234567890")
                .build();
    }

    private Patient createValidPatient() {
        Patient patient = new Patient();
        patient.setName("Jane Doe");
        patient.setEmail("jane.doe@example.com");
        patient.setPassword("password");
        patient.setCpf("987.654.321-00");
        patient.setDatebirth(String.valueOf(LocalDate.of(1995, 2, 2)));
        patient.setAddress(createValidUser().getAddress());
        patient.setPhone("0987654321");
        patient.setPhoneSpare("1231231234");
        return patient;
    }

    private Doctor createValidDoctor() {
        Doctor doctor = new Doctor();
        doctor.setName("Dr. Smith");
        doctor.setEmail("dr.smith@example.com");
        doctor.setPassword("password");
        doctor.setCpf("111.222.333-44");
        doctor.setDatebirth(String.valueOf(LocalDate.of(1980, 3, 3)));
        doctor.setAddress(createValidUser().getAddress());
        doctor.setPhone("1112223333");
        doctor.setCrm("123456-SP");
        doctor.setSpecialty(Specialty.CARDIOLOGY.getCode());
        return doctor;
    }
}