package br.com.fourcamp.smc.SMC.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.fourcamp.smc.SMC.model.Address;
import br.com.fourcamp.smc.SMC.model.Doctor;
import org.junit.jupiter.api.Test;

public class DoctorTest {

    @Test
    public void testDoctorCreation() {
        Address address = Address.builder()
                .cep("08420-630")
                .number("456")
                .build();

        Doctor doctor = new Doctor("1",
                "Dr. Helton Dias",
                "dias@example.com",
                "securePass456",
                "514.933.118-08",
                "2002/02/06",
                address,
                "(11) 95477-8207",
                "123456-SP",
                1);

        assertEquals("Dr. Helton Dias", doctor.getName());
        assertEquals("dias@example.com", doctor.getEmail());
        assertEquals("securePass456", doctor.getPassword());
        assertEquals("514.933.118-08", doctor.getCpf());
        assertEquals("2002/02/06", doctor.getDatebirth());
        assertEquals("08420-630", doctor.getAddress().getCep());
        assertEquals("456", doctor.getAddress().getNumber());
        assertEquals("(11) 95477-8207", doctor.getPhone());
        assertEquals("123456-SP", doctor.getCrm());
        assertEquals(1, doctor.getSpecialty());
    }
}