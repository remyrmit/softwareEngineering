package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataValidatorTest {

    @Test
    public void testCheckID_NoSpecialCharacters() {
        String idWithoutSpecialChars = "22aiideXYZ";

        boolean result = DataValidator.checkID(idWithoutSpecialChars);

        assertFalse(result, "Special characters");
        System.out.println();
    }
}
