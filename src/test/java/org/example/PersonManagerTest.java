package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonManagerTest {


    @Test
    public void testAddPerson_InvalidIDFirstDigitIsZero() {

        PersonManager manager = new PersonManager("people.txt");

        Person invalidID = new Person(
                "02%^cdeXYZ",
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Victoria|Australia",
                "02-04-1999"
        );

        boolean result = manager.addPerson(invalidID);

        assertFalse(result);
    }

    @Test
    public void testAddPerson_InvalidIDNoSpecialCharacter() {

        PersonManager manager = new PersonManager("people.txt");

        Person invalidID = new Person(
                "22abcdeXYZ",
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Victoria|Australia",
                "02-04-1999"
        );

        boolean result = manager.addPerson(invalidID);

        assertFalse(result);
    }

    @Test
    public void testAddPerson_InvalidIAddressFormat() {

        PersonManager manager = new PersonManager("people.txt");

        Person invalidAddress1 = new Person(
                "22a%%deXYZ",
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Tasmania|Australia",
                "02-04-1999"
        );

        boolean result1 = manager.addPerson(invalidAddress1);
        assertFalse(result1);

        Person invalidAddress2 = new Person(
                "22a%%deXYZ",
                "UNIT",
                "TEST",
                "80, Swanston Street, Melbourne, Victoria, Australia",
                "02-04-1999"
        );


        boolean result2 = manager.addPerson(invalidAddress2);
        assertFalse(result2);
    }

    @Test
    public void testAddPerson_BirthdateTooShort() {

        PersonManager manager = new PersonManager("people.txt");

        Person invalidBirthdate = new Person(
                "22ab$%eXYZ",
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Victoria|Australia",
                "2-4-99"
        );

        boolean result = manager.addPerson(invalidBirthdate);

        assertFalse(result);
    }

    @Test
    public void testAddPerson_BirthdateWrongFormat() {

        PersonManager manager = new PersonManager("people.txt");

        Person invalidBirthdate = new Person(
                "21ab$%eXYZ",
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Victoria|Australia",
                "02/04/1999"
        );

        boolean result = manager.addPerson(invalidBirthdate);

        assertFalse(result);
    }

    @Test
    public void testAddPerson_BirthdateNotExistent() {

        PersonManager manager = new PersonManager("people.txt");

        Person invalidBirthdate = new Person(
                "22ab$%eXYZ",
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Victoria|Australia",
                "00-13-1000"
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

