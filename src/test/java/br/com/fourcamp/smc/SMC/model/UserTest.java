package br.com.fourcamp.smc.SMC.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testUserGettersAndSetters() {
        Address address = Address.builder()
                .cep("08420-630")
                .number("456")
                .build();

        User user = new User();
        user.setId("1");
        user.setName("Helton Dias");
        user.setEmail("dias@example.com");
        user.setPassword("securePass456");
        user.setCpf("514.933.118-08");
        user.setDatebirth("2002/02/06");
        user.setAddress(address);
        user.setPhone("(11) 95477-8207");

        assertEquals("1", user.getId());
        assertEquals("Helton Dias", user.getName());
        assertEquals("dias@example.com", user.getEmail());
        assertEquals("securePass456", user.getPassword());
        assertEquals("514.933.118-08", user.getCpf());
        assertEquals("2002/02/06", user.getDatebirth());
        assertEquals(address, user.getAddress());
        assertEquals("(11) 95477-8207", user.getPhone());
    }
}