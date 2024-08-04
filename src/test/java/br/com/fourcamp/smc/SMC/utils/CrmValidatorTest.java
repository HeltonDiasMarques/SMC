package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.dao.IJdbcTemplateUserDao;
import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.model.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CrmValidatorTest {

    @Mock
    private IJdbcTemplateUserDao<Doctor> doctorDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testNormalizeAndValidate_ValidCrm() {
        String crm = "123456-SP";
        assertEquals("123456-SP", CrmValidator.normalizeAndValidate(crm));
    }

    @Test
    public void testNormalizeAndValidate_InvalidCrm() {
        String crm = "123456-12";
        CustomException exception = assertThrows(CustomException.class, () -> {
            CrmValidator.normalizeAndValidate(crm);
        });
        assertEquals(ErrorMessage.INVALID_CRM_FORMAT.getMessage(), exception.getMessage());
    }

    @Test
    public void testIsValid_ValidCrm() {
        assertTrue(CrmValidator.isValid("123456-SP"));
    }

    @Test
    public void testIsValid_InvalidCrm() {
        assertFalse(CrmValidator.isValid("123456-12"));
    }

    @Test
    public void testValidateCrm_NewValidCrm() {
        Doctor doctor = new Doctor();
        doctor.setCrm("123456-SP");
        when(doctorDao.existsByCrm("123456-SP")).thenReturn(false);

        assertDoesNotThrow(() -> CrmValidator.validateCrm(doctor, true, doctorDao));
    }

    @Test
    public void testValidateCrm_NewCrmAlreadyExists() {
        Doctor doctor = new Doctor();
        doctor.setCrm("123456-SP");
        when(doctorDao.existsByCrm("123456-SP")).thenReturn(true);

        CustomException exception = assertThrows(CustomException.class, () -> {
            CrmValidator.validateCrm(doctor, true, doctorDao);
        });
        assertEquals(ErrorMessage.CRM_ALREADY_REGISTERED.getMessage(), exception.getMessage());
    }

    @Test
    public void testValidateCrm_ExistingValidCrm() {
        Doctor doctor = new Doctor();
        doctor.setCrm("123456-SP");

        assertDoesNotThrow(() -> CrmValidator.validateCrm(doctor, false, doctorDao));
    }

    @Test
    public void testValidateCrm_InvalidCrm() {
        Doctor doctor = new Doctor();
        doctor.setCrm("123456-12");

        CustomException exception = assertThrows(CustomException.class, () -> {
            CrmValidator.validateCrm(doctor, false, doctorDao);
        });
        assertEquals(ErrorMessage.INVALID_CRM_FORMAT.getMessage(), exception.getMessage());
    }
}