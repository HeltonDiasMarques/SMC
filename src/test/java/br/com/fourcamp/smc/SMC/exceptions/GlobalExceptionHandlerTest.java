package br.com.fourcamp.smc.SMC.exceptions;

import br.com.fourcamp.smc.SMC.config.TestDatabaseConfig;
import br.com.fourcamp.smc.SMC.config.security.WebSecurityConfig;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.utils.JwtTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({WebSecurityConfig.class, TestDatabaseConfig.class})
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleCustomException() throws Exception {
        mockMvc.perform(get("/test/customException")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().string(ErrorMessage.ACCESS_DENIED.getMessage()));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testHandleException() throws Exception {
        mockMvc.perform(get("/test/generalException"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testHandleExceptionWithJwt() throws Exception {
        String validJwtToken = JwtTestUtil.generateToken("user");

        mockMvc.perform(get("/test/generalException")
                        .header("Authorization", "Bearer " + validJwtToken))
                .andExpect(status().isInternalServerError());
    }
}