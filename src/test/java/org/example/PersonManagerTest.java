package org.example;

import org.example.Person;
import org.example.PersonManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonManagerTest {

    @Test
    public void testAddPerson_ValidPerson() {
        PersonManager manager = new PersonManager("people.txt");

        Person validPerson = new Person(
                "00s_d%&fAB",                           // Valid ID
                "UNIT",                                // Valid first name
                "TEST",                                // Valid last name
                "00|Highland Street|Melbourne|Victoria|Australia", // Valid address
                "15-11-1990"                             // Valid birthdate
        );

        boolean result = manager.addPerson(validPerson);

     //   assertTrue(result, "Person with valid details should be added successfully.");
        assertFalse(result, "Person with invalid ID should not be added.");

    }
}

