// this class is responsible for checking if all the entered data meets the assigment criteria

package org.example;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DataValidator {

    // validating the ID: length, first 2 digits, special characters, uppercase letters
    public static boolean checkID(String personID) {

        System.out.println("Checking ID: " + personID);

        // checking if the entered ID is 0 or more than 10 characters
        if (personID == null || personID.length() != 10) {
            System.out.println("Incorrect ID length.");
            return false;
        }

        // checking the first 2 characters - must be a digit between 2 and 9
        char first = personID.charAt(0);
        char second = personID.charAt(1);
        if (!Character.isDigit(first) || !Character.isDigit(second)) {
            System.out.println("First 2 characters must be a digit..");
            return false;
        }
        if (first < '2' || first > '9' || second < '2' || second > '9') {
            System.out.println("First 2 characters must be between 2 and 9.");
            return false;
        }

        // checking for special characters - must be exactly 2
        int specialCharCount = 0;
        for (int i = 2; i <= 7; i++) {
            char ch = personID.charAt(i);
            if (!Character.isLetterOrDigit(ch)) {
                specialCharCount++;
            }
        }

        if (specialCharCount < 2) {
            System.out.println("No or less than 2 special characters.");
            return false;
        }

        // checking the last 2 characters - must be uppercase letters
        char last1 = personID.charAt(8);
        char last2 = personID.charAt(9);
        if (!Character.isUpperCase(last1) || !Character.isUpperCase(last2)) {
            System.out.println("Last 2 characters must be uppercase.");
            return false;
        }

        return true;
    }

    // validating the address: the correct length, street number and state
    public static boolean checkAddress(String address) {
        if (address == null || address.isEmpty()) {
            return false;
        }

        // checking if it's the correct format - must be seperated by "|"
        String[] parts = address.split("\\|");
        if (parts.length != 5) {
            System.out.println("Incorrect address format.");
            return false;
        }

        String streetNumber = parts[0].trim();
        String street = parts[1].trim();
        String city = parts[2].trim();
        String state = parts[3].trim();
        String country = parts[4].trim();

        if (!streetNumber.matches("\\d+")) {
            return false;
        }

        // checking the state - must be Victoria
        if (!state.equals("Victoria")) {
            System.out.println("Incorrect state.");
            return false;
        }

        return !(street.isEmpty() || city.isEmpty() || country.isEmpty());
    }

    // validating birthday: format, length, valid info
    public static boolean checkBirthdate(String birthdate) {
        if (birthdate == null || birthdate.isEmpty()) {
            System.out.println("Incorrect birthdate.");
            return false;
        }

        // checking the format - mist be seperated by "-"
        String[] parts = birthdate.split("-");
        if (parts.length != 3) {
            System.out.println("Incorrect date format.");
            return false;
        }

        String dayStr = parts[0];
        String monthStr = parts[1];
        String yearStr = parts[2];

        // must be in a dd/mm/yyyy format
        if (!dayStr.matches("\\d{2}") || !monthStr.matches("\\d{2}") || !yearStr.matches("\\d{4}")) {
            System.out.println("Incorrect date format.");
            return false;
        }

        int day = Integer.parseInt(dayStr);
        int month = Integer.parseInt(monthStr);
        int year = Integer.parseInt(yearStr);

        // must have existing day, month and year and be in the last 95 years
        if (month < 1 || month > 12 || day < 1 || day > 31) {
            System.out.println("Incorrect day/month.");
            return false;
        }

        if (year < 1920 || year > 2025) {
            System.out.println("Incorrect year.");
            return false;
        }

        return true;
    }

    // validating if the person is under 18
    public static boolean isUnder18(String birthdate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate birth = LocalDate.parse(birthdate, formatter);
            LocalDate today = LocalDate.now();
            return Period.between(birth, today).getYears() < 18;
        } catch (Exception e) {
            return false;
        }
    }

    // validating first name: only characters
    public static boolean checkFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty() || !firstName.matches("[A-Z][a-zA-Z]*")) {
            System.out.println("Incorrect first name.");
            return false;
        }
        return true;
    }

    // validating last name: only characters
    public static boolean checkLastName(String lastName) {
        return lastName != null && lastName.matches("[A-Z][a-zA-Z]*([-\\s][A-Z][a-zA-Z]*)?");
    }
}