package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Address;
import br.com.fourcamp.smc.SMC.model.Patient;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneSpareValidatorTest {

    @Test
    public void testValidatePhoneSpare_Valid() {
        Patient patient = createValidPatient();
        assertDoesNotThrow(() -> PhoneSpareValidator.validatePhoneSpare(patient));
    }

    @Test
    public void testValidatePhoneSpare_SpareEqualToPhone() {
        Patient patient = createValidPatient();
        patient.setPhoneSpare("11954778207"); // mesmo número do principal para forçar a falha
        CustomException exception = assertThrows(CustomException.class, () -> PhoneSpareValidator.validatePhoneSpare(patient));
        assertEquals(ErrorMessage.PHONE_SPARE_EQUAL_TO_PHONE.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidatePhoneSpare_InvalidPhoneSpare() {
        Patient patient = createValidPatient();
        patient.setPhoneSpare("invalid_phone");
        CustomException exception = assertThrows(CustomException.class, () -> PhoneSpareValidator.validatePhoneSpare(patient));
        assertEquals(ErrorMessage.INVALID_PHONE_SPARE.getMessage(), exception.getMessage());
    }

    private Patient createValidPatient() {
        Patient patient = new Patient();
        patient.setName("Jane Doe");
        patient.setEmail("jane.doe@example.com");
        patient.setPassword("password");
        patient.setCpf("987.654.321-00");
        patient.setDatebirth(String.valueOf(LocalDate.of(1995, 2, 2)));
        patient.setAddress(Address.builder()
                .cep("12345678")
                .number("123")
                .street("Main Street")
                .neighborhood("Downtown")
                .city("City")
                .state("ST")
                .build());
        patient.setPhone("11954778207");
        patient.setPhoneSpare("11987654321");
        return patient;
    }
}