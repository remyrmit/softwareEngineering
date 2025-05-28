// in this class, we store a list of all Persons
// all the operations regarding Person are executed through this class

package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class PersonManager {

    // storing all persons in a list
    public List<Person> personList;

    private String filename;

    // getter for person list
    public List<Person> getPersonList() {
        return personList;
    }

    public PersonManager(String filename) {
        this.filename = filename;
        List<Person> loadedList = FileHandler.loadFromFile(filename);
        this.personList = (loadedList != null) ? loadedList : new ArrayList<>();
    }

    // a method to add a Person, validate the imputm and write to a file if everything is correct
    public boolean addPerson(Person person) {
        if (person == null) return false;

        if (!DataValidator.checkID(person.getPersonID()) ||
                !DataValidator.checkFirstName(person.getFirstName()) ||
                !DataValidator.checkLastName(person.getLastName()) ||
                !DataValidator.checkAddress(person.getAddress()) ||
                !DataValidator.checkBirthdate(person.getBirthdate())) {
            return false;
        }

        // preventing duplicate IDs
        if (containsPerson(person.getPersonID())) {
            System.out.println("ID already exists.");
            return false;
        }

        // appending to a file
        try {
            FileHandler.appendPersonToFile(person, filename);
            System.out.println("Writing to file: " + filename);

            System.out.println("Appending person to file: " + person);
            personList.add(person);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

//    public boolean addPerson(Person person) {
//        if (person == null) {
//            System.out.println("Person is null.");
//            return false;
//        }
//
//        if (!DataValidator.checkID(person.getPersonID())) {
//            System.out.println("Invalid ID.");
//            return false;
//        }
//
//        if (!DataValidator.checkFirstName(person.getFirstName())) {
//            System.out.println("Invalid first name.");
//            return false;
//        }
//
//        if (!DataValidator.checkLastName(person.getLastName())) {
//            System.out.println("Invalid last name.");
//            return false;
//        }
//
//        if (!DataValidator.checkAddress(person.getAddress())) {
//            System.out.println("Invalid address.");
//            return false;
//        }
//
//        if (!DataValidator.checkBirthdate(person.getBirthdate())) {
//            System.out.println("Invalid birthdate.");
//            return false;
//        }
//
//        if (containsPerson(person.getPersonID())) {
//            System.out.println("ID already exists");
//            return false;
//        }
//
//        try {
//            FileHandler.appendPersonToFile(person, filename);
//            System.out.println("Writing to file: " + filename);
//            System.out.println("Appending person to file: " + person);
//            personList.add(person);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }


    // lambda to check if the person is in the List
    private boolean containsPerson(String personID) {
        return personList.stream().anyMatch(p -> p.getPersonID().equals(personID));
    }

    // updating person's details
    public boolean updatePersonalDetails(String oldID, String newID, String newFirstName,
                                         String newLastName, String newAddress, String newBirthdate) throws IOException {
        for (Person person : personList) {

            if (person.getPersonID().equals(oldID)) {

                // copying existing values for comparison
                String currentID = person.getPersonID();
                String currentFirst = person.getFirstName();
                String currentLast = person.getLastName();
                String currentAddress = person.getAddress();
                String currentBirthdate = person.getBirthdate();

                boolean idChanged = !newID.equals(currentID);
                boolean nameChanged = !newFirstName.equals(currentFirst) || !newLastName.equals(currentLast);
                boolean addressChanged = !newAddress.equals(currentAddress);
                boolean birthdayChanged = !newBirthdate.equals(currentBirthdate);

                // If birthday changed, no other fields can be changed
                if (birthdayChanged && (idChanged || nameChanged || addressChanged)) {
                    System.out.println("You can only update your birthdate.");
                    return false;
                }

                // If person is under 18, address can't be changed
                if (DataValidator.isUnder18(currentBirthdate) && addressChanged) {
                    System.out.println("\nAddress cannot be changed due to being under 18.");
                    return false;
                }

                // If ID starts with even number, ID can't be changed
                char firstChar = currentID.charAt(0);
                if (Character.isDigit(firstChar) && (firstChar - '0') % 2 == 0 && idChanged) {
                    System.out.println("You cannot update your ID.");
                    return false;
                }

                // validating new data
                if (!DataValidator.checkID(newID)
                        || !DataValidator.checkFirstName(newFirstName)
                        || !DataValidator.checkLastName(newLastName)
                        || !DataValidator.checkAddress(newAddress)
                        || !DataValidator.checkBirthdate(newBirthdate)) {
                    return false;
                }

                // if al data is valid, then the details are updated
                person.setPersonID(newID);
                person.setFirstName(newFirstName);
                person.setLastName(newLastName);
                person.setAddress(newAddress);
                person.setBirthdate(newBirthdate);
                System.out.println("Details have been successfully updated!");

                // saving the updates to a file
                FileHandler.overwriteFileWithAllPeople(personList, filename);

                return true;
            }
        }
        // Person with oldID not found
        return false;
    }

    private static final String DEMERIT_FILE = "demerits.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public String addDemeritPoints(String id, String dateOfOffense, int points) {
        Person person = findPersonById(id);
        if (person == null) return "Failed";

        // vlidate date
        LocalDate offenseDate;
        try {
            offenseDate = LocalDate.parse(dateOfOffense, FORMATTER);
            if (offenseDate.isAfter(LocalDate.now())) return "Failed"; // No future dates
        } catch (DateTimeParseException e) {
            return "Failed";
        }

        // validate points
        if (points < 1 || points > 6) return "Failed";

        // calculate demerits within 2 years
        int totalPointsIn2Years = getDemeritPointsInTwoYears(id, offenseDate) + points;

        // determine suspension
        LocalDate birthdate = LocalDate.parse(person.getBirthdate(), FORMATTER);
        int age = Period.between(birthdate, LocalDate.now()).getYears();

        boolean suspend = (age < 21 && totalPointsIn2Years > 6) || (age >= 21 && totalPointsIn2Years > 12);

        if (suspend) person.setSuspended(true);

        // appending to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DEMERIT_FILE, true))) {
            writer.write(id + "," + dateOfOffense + "," + points);
            writer.newLine();
        } catch (IOException e) {
            return "Failed";
        }

        // saving the suspension state if changed
        try {
            FileHandler.overwriteFileWithAllPeople(personList, "people.txt");
        } catch (IOException e) {
            return "Failed";
        }

        return "Success";
    }


    private int getDemeritPointsInTwoYears(String id, LocalDate offenseDate) {
            int total = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(DEMERIT_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length != 3) continue;

                    String recordId = parts[0];
                    String dateStr = parts[1];
                    int pointVal = Integer.parseInt(parts[2]);

                    if (!recordId.equals(id)) continue;

                    LocalDate recordedDate = LocalDate.parse(dateStr, FORMATTER);
                    if (!recordedDate.isBefore(offenseDate.minusYears(2)) && !recordedDate.isAfter(offenseDate)) {
                        total += pointVal;
                    }

                }
            } catch (IOException | NumberFormatException | DateTimeParseException e) {
                // handle quietly, or log if needed
            }
            return total;
        }

        private Person findPersonById(String id) {
            for (Person p : personList) {
                if (p.getPersonID().equals(id)) return p;
            }
            return null;
        }


        // removing a person (use for cleaning the file)
    public boolean removePerson(String personID) {
        Person toRemove = null;
        for (Person p : personList) {
            if (p.getPersonID().equals(personID)) {
                toRemove = p;
                break;
            }
        }
        if (toRemove == null) return false;

        personList.remove(toRemove);

        try {
            FileHandler.overwriteFileWithAllPeople(personList, filename);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}


