package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonManagerTest {

    /*
    Test Case: ID Validation - The first two characters should be numbers between 2 and 9 | Verify the function with an ID where one of the first two digits is 0
     - Validates that person IDs starting with zero are rejected.
     - This ensures proper ID format compliance and prevents invalid ID generation.
     - Expected: addPerson() should return false for IDs starting with '0'
     */
    @Test
    public void testAddPerson_InvalidIDFirstDigitIsZero() {

        PersonManager manager = new PersonManager("people.txt");

        Person invalidID = new Person(
                "02%^cdeXYZ", // Invalid: starts with 0
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Victoria|Australia",
                "02-04-1999"
        );

        boolean result = manager.addPerson(invalidID);

        assertFalse(result);
    }


    /*
     Test Case: ID Validation - Must Contain Special Characters | Verify the func-tion with an ID without any special charac-ters 
     - Verifies that person IDs without special characters are rejected.
     - This enforces the ID format requirement that includes special characters for security.
     - Expected: addPerson() should return false for IDs lacking special characters
     */
    @Test
    public void testAddPerson_InvalidIDNoSpecialCharacter() {

        PersonManager manager = new PersonManager("people.txt");

        Person invalidID = new Person(
                "22abcdeXYZ", // Invalid: no special characters
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Victoria|Australia",
                "02-04-1999"
        );

        boolean result = manager.addPerson(invalidID);

        assertFalse(result);
    }

    /*
     Test Case: Address Validation - Format and Location Rules | Verify the func-tion with an address in the wrong format/state
     - Tests that addresses must follow specific formatting rules:
       1. Must use pipe (|) separators, not commas
       2. Must specify Victoria as the state (not Tasmania or other states)
     - This ensures address standardization and location-specific business rules.
     - Expected: addPerson() should return false for incorrectly formatted addresses
     */
    @Test
    public void testAddPerson_InvalidIAddressFormat() {

        PersonManager manager = new PersonManager("people.txt");
        // Test case 1: Wrong state
        Person invalidAddress1 = new Person(
                "22a%%deXYZ",
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Tasmania|Australia", // Invalid state
                "02-04-1999"
        );

        boolean result1 = manager.addPerson(invalidAddress1);
        assertFalse(result1);
        
        // Test case 2: Wrong separator
        Person invalidAddress2 = new Person(
                "22a%%deXYZ",
                "UNIT",
                "TEST",
                "80, Swanston Street, Melbourne, Victoria, Australia", // Invalid separator, using commas instead of pipes
                "02-04-1999"
        );


        boolean result2 = manager.addPerson(invalidAddress2);
        assertFalse(result2);
    }


    /*
     Test Case: Birthdate Validation - Minimum Length Requirement | Verify the function with a birthdate that is too short
     - Ensures that birthdates must be in full format (DD-MM-YYYY).
     - Abbreviated formats like "2-4-99" are rejected to maintain data consistency.
     - Expected: addPerson() should return false for shortened date formats
     */
    @Test
    public void testAddPerson_BirthdateTooShort() {

        PersonManager manager = new PersonManager("people.txt");

        Person invalidBirthdate = new Person(
                "22ab$%eXYZ",
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Victoria|Australia",
                "2-4-99" // Invalid: too short, should be DD-MM-YYYY
        );

        boolean result = manager.addPerson(invalidBirthdate);

        assertFalse(result);
    }

    /*
     Test Case: Birthdate Validation - Format Requirements | Verify the function with a birthdate in an incorrect for-mat
     - Validates that birthdates must use hyphen separators (DD-MM-YYYY).
     - Slash separators (DD/MM/YYYY) are not accepted to ensure format consistency.
     - Expected: addPerson() should return false for dates with wrong separators
     */
    @Test
    public void testAddPerson_BirthdateWrongFormat() {

        PersonManager manager = new PersonManager("people.txt");

        Person invalidBirthdate = new Person(
                "21ab$%eXYZ",
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Victoria|Australia",
                "02/04/1999" // Invalid: uses slashes instead of hyphens
        );

        boolean result = manager.addPerson(invalidBirthdate);

        assertFalse(result);
    }

    /*
     Test Case: Birthdate Validation - Date Existence Check | Verify the function with a birthdate that is not existent
     - Ensures that only valid calendar dates are accepted.
     - Tests rejection of impossible dates (month 13, day 00, etc.).
     - Expected: addPerson() should return false for non-existent dates
     */
    @Test
    public void testAddPerson_BirthdateNotExistent() {

        PersonManager manager = new PersonManager("people.txt");

        Person invalidBirthdate = new Person(
                "22ab$%eXYZ",
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Victoria|Australia",
                "00-13-1000" // Invalid: impossible date (day 00, month 13)
        );

        boolean result = manager.addPerson(invalidBirthdate);

        assertFalse(result);
    }

    @Test
    public void testUpdatePerson_Under18AddressChange() throws IOException {

        PersonManager manager = new PersonManager("people.txt");

        //Person person = new Person("")

        boolean result = manager.updatePersonalDetails("65ab$%eXYZ", "65ab$%eXYZ", "Tom", "Hanks",
                "85|Flinders Ln|Melbourne|Victoria|Australia", "11-11-2011");

        assertFalse(result);
    }

    @Test
    public void testUpdatePerson_Under18NoAddressChange() throws IOException {

        PersonManager manager = new PersonManager("people.txt");

        boolean result = manager.updatePersonalDetails("65ab$%eXYZ", "65ab$%eXYZ", "Tommy", "Hanks",
                "80|Swanston Street|Melbourne|Victoria|Australia", "11-11-2011");

        assertTrue(result);
    }

    @Test
    public void testUpdatePerson_Under18BirthdayUpdate() throws IOException {

        PersonManager manager = new PersonManager("people.txt");

        boolean result = manager.updatePersonalDetails("65ab$%eXYZ", "65ab$%eXYZ", "Thomas", "Hanks",
                "80|Swanston Street|Melbourne|Victoria|Australia", "11-11-2011");

        assertTrue(result);
    }


    @Test
    public void testUpdatePerson_IDCannotChangeIfStartsWithEvenNumber() throws IOException {

        PersonManager manager = new PersonManager("people.txt");

        boolean result = manager.updatePersonalDetails("29ab$%eXYZ", "11a%%deXYZ", "UNIT", "TEST",
                "80|Swanston Street|Melbourne|Victoria|Australia", "02-04-1999");

        assertFalse(result);
    }

    @Test
    public void testUpdatePerson_IDChangeWhenStartsWithOdd() throws IOException {

        PersonManager manager = new PersonManager("people.txt");

        boolean result = manager.updatePersonalDetails("56ab$%eXYZ", "56rm$%eXYZ", "UNIT", "TEST",
                "55|Flinders ln|Melbourne|Victoria|Australia", "02-04-2015");

        assertTrue(result);
    }


    @Test
    public void testAddDemeritPoints_InvalidPoints() throws IOException {
        PersonManager manager = new PersonManager("people.txt");

        String result = manager.addDemeritPoints("65ab$%eXYZ", "01-04-2024", 0);

        assertEquals("Failed", result);

        String result2 = manager.addDemeritPoints("65ab$%eXYZ", "01-04-2024", 7);

        assertEquals("Failed", result2);
    }

    @Test
    public void testAddDemeritPoints_FutureDate() throws IOException {

        PersonManager manager = new PersonManager("people.txt");

        String result = manager.addDemeritPoints("56rm$%eXYZ", "01-01-2030", 4);

        assertEquals("Failed", result);
    }

    @Test
    public void testAddDemeritPoints_ValidUnder21NoSuspension() throws IOException {

        PersonManager manager = new PersonManager("people.txt");

        // 20-year-old, only 3 points, should not be suspended
        String result = manager.addDemeritPoints("56rm$%eXYZ", "01-04-2024", 4);

        assertEquals("Success", result);
    }

    @Test
    public void testAddDemeritPoints_TriggerSuspensionUnder21() throws IOException {

        PersonManager manager = new PersonManager("people.txt");

        // we have already added 4 point in the test above -- now the total will be 7
        String result = manager.addDemeritPoints("56rm$%eXYZ", "01-05-2025", 3);

        assertEquals("Success", result);

    }

    @Test
    public void testAddDemeritPoints_TriggerSuspensionOver21() throws IOException {

        PersonManager manager = new PersonManager("people.txt");

        //previously added points
        String result = manager.addDemeritPoints("65ab$%eXYZ", "01-05-2025", 1);

        assertEquals("Success", result);

    }
}

