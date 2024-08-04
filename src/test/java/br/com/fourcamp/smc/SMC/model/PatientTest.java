package br.com.fourcamp.smc.SMC.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.fourcamp.smc.SMC.model.Address;
import br.com.fourcamp.smc.SMC.model.Patient;
import org.junit.jupiter.api.Test;

public class PatientTest {

    @Test
    public void testPatientCreation() {
        Address address = Address.builder()
                .cep("08420-630")
                .number("456")
                .build();

        Patient patient = new Patient("1",
                "Helton Dias",
                "dias@example.com",
                "securePass456",
                "514.933.118-08",
                "2002/02/06",
                address,
                "(11) 95477-8207",
                "11954778208",
                "Peanuts",
                "Some notes",
                "Transcript");

        assertEquals("Helton Dias", patient.getName());
        assertEquals("dias@example.com", patient.getEmail());
        assertEquals("securePass456", patient.getPassword());
        assertEquals("514.933.118-08", patient.getCpf());
        assertEquals("2002/02/06", patient.getDatebirth());
        assertEquals("08420-630", patient.getAddress().getCep());
        assertEquals("456", patient.getAddress().getNumber());
        assertEquals("(11) 95477-8207", patient.getPhone());
        assertEquals("11954778208", patient.getPhoneSpare());
        assertEquals("Peanuts", patient.getAllergies());
        assertEquals("Some notes", patient.getNotes());
        assertEquals("Transcript", patient.getTranscript());
    }
}