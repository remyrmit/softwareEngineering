package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

//        PersonManager pm = new PersonManager("people.txt");
//
//        // Create a valid Person object
//        Person person = new Person(
//                "434ABC00XY",    // valid personID (10 chars, starts with digits 2-9)
//                "Remy",
//                "Martin",
//                "104|New St|Melbourne|Victoria|Australia",
//                "02-04-1999"
//        );
//
//
//        pm.addPerson(person);


  //for deleting the tests i ran
        PersonManager manager2 = new PersonManager("people.txt");
        manager2.removePerson("78s_d%&fAB");

    }
}

