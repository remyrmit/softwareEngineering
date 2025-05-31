// this class only contains Person's attributes, getters and setters

package org.example;

import java.util.Date;
import java.util.HashMap;

public class Person {

    // class attributes
    private String personID;
    private String firstName;
    private String lastName;
    private String address; // Format: StreetNumber|Street|City|State|Country
    private String birthdate; // Format: DD-MM-YYYY
    private HashMap<Date, Integer> demeritPoints; // Date of offense -> points
    private boolean isSuspended;

    // constructor
    public Person(String personID, String firstName, String lastName,
                  String address, String birthdate) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;
        this.demeritPoints = new HashMap<>();
        this.isSuspended = false;
    }

    // getters and setters for all attributes
    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    @Override
    public String toString() {
        return personID + "," + firstName + "," + lastName + "," + address + "," + birthdate + "," + isSuspended;
    }
}