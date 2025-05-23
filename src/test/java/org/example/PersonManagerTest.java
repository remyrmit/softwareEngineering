package org.example;

import org.example.Person;
import org.example.PersonManager;
import org.junit.jupiter.api.Test;
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

        assertFalse(result, "Person with invalid ID should not be added.");
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

        assertFalse(result, "Person with invalid ID should not be added.");
    }

    @Test
    public void testAddPerson_BirthdateTooShort(){

        PersonManager manager = new PersonManager("people.txt");

        Person invalidBirthdate = new Person(
                "22ab$%eXYZ",
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Victoria|Australia",
                "2-4-99"
        );

        boolean result = manager.addPerson(invalidBirthdate);

        assertFalse(result, "Person with invalid birthdate format should not be added.");
    }

    @Test
    public void testAddPerson_BirthdateWrongFormat(){

        PersonManager manager = new PersonManager("people.txt");

        Person invalidBirthdate = new Person(
                "22ab$%eXYZ",
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Victoria|Australia",
                "02/04/1999"
        );

        boolean result = manager.addPerson(invalidBirthdate);

        assertFalse(result, "Person with invalid birthdate format should not be added.");
    }

    @Test
    public void testAddPerson_BirthdateNotExistent(){

        PersonManager manager = new PersonManager("people.txt");

        Person invalidBirthdate = new Person(
                "22ab$%eXYZ",
                "UNIT",
                "TEST",
                "80|Swanston Street|Melbourne|Victoria|Australia",
                "00-13-1000"
        );

        boolean result = manager.addPerson(invalidBirthdate);

        assertFalse(result, "Person with invalid birthdate format should not be added.");
    }

}

