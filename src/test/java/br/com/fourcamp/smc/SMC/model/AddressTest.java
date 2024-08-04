package br.com.fourcamp.smc.SMC.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void testAddressBuilder() {
        Address address = Address.builder()
                .cep("05442-100")
                .street("Main St")
                .neighborhood("Downtown")
                .city("CityName")
                .state("StateName")
                .number("456")
                .build();

        assertEquals("05442-100", address.getCep());
        assertEquals("Main St", address.getStreet());
        assertEquals("Downtown", address.getNeighborhood());
        assertEquals("CityName", address.getCity());
        assertEquals("StateName", address.getState());
        assertEquals("456", address.getNumber());
    }

    // Adicionando um segundo teste para garantir consistência
    @Test
    public void testAddressBuilderWithDifferentValues() {
        Address address = Address.builder()
                .cep("12345-678")
                .street("Second St")
                .neighborhood("Uptown")
                .city("AnotherCity")
                .state("AnotherState")
                .number("789")
                .build();

        assertEquals("12345-678", address.getCep());
        assertEquals("Second St", address.getStreet());
        assertEquals("Uptown", address.getNeighborhood());
        assertEquals("AnotherCity", address.getCity());
        assertEquals("AnotherState", address.getState());
        assertEquals("789", address.getNumber());
    }
}