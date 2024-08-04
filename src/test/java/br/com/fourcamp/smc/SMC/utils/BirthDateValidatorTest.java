package br.com.fourcamp.smc.SMC.utils;

import br.com.fourcamp.smc.SMC.enums.ErrorMessage;
import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BirthDateValidatorTest {

    @Test
    public void testNormalizeAndValidate_ValidDateFormat1() {
        String date = "2024-08-01";
        String expected = "20240801";
        assertEquals(expected, BirthDateValidator.normalizeAndValidate(date));
    }

    @Test
    public void testNormalizeAndValidate_ValidDateFormat2() {
        String date = "2024/08/01";
        String expected = "20240801";
        assertEquals(expected, BirthDateValidator.normalizeAndValidate(date));
    }

    @Test
    public void testNormalizeAndValidate_ValidDateFormat3() {
        String date = "20240801";
        String expected = "20240801";
        assertEquals(expected, BirthDateValidator.normalizeAndValidate(date));
    }

    @Test
    public void testNormalizeAndValidate_InvalidDateFormat() {
        String date = "01-08-2024";
        Exception exception = assertThrows(CustomException.class, () -> {
            BirthDateValidator.normalizeAndValidate(date);
        });

        String expectedMessage = ErrorMessage.INVALID_DATE_FORMAT.getMessage();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testIsValid_ValidDate() {
        assertTrue(BirthDateValidator.isValid("20240801"));
    }

    @Test
    public void testIsValid_InvalidDate() {
        assertFalse(BirthDateValidator.isValid("2024-08-01"));
    }

    @Test
    public void testIsAdult_ValidAdult() {
        assertTrue(BirthDateValidator.isAdult("20000801"));
    }

    @Test
    public void testIsAdult_InvalidAdult() {
        String date = "20240801";
        Exception exception = assertThrows(CustomException.class, () -> {
            BirthDateValidator.isAdult(date);
        });

        String expectedMessage = ErrorMessage.INVALID_AGE.getMessage();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}