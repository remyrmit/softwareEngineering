// this class loads the menu options and takes user input

package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // adding a scanner to read user input, using the file with records of people
        // displaying a menu and using a switch-case to go to a function that a user chooses
        Scanner scanner = new Scanner(System.in);
        PersonManager pm = new PersonManager("people.txt");

        while (true) {
            System.out.println("\n--- Person Management ---");
            System.out.println("1. Add person");
            System.out.println("2. Update Personal Details");
            System.out.println("3. Add Demerit Points");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addPersonFromInput(scanner, pm);
                    break;
                case "2":
                    updatePersonFromInput(scanner, pm);
                    break;
                case "3":
                    addDemeritPointsFromInput(scanner, pm);
                    break;
                case "4":
                    System.out.println("Good bye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // asking for all the necessary info to add a person
    private static void addPersonFromInput(Scanner scanner, PersonManager pm) {

        System.out.print("Enter Person ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Address (Street Number|Street|City|State|Country): ");
        String address = scanner.nextLine();

        System.out.print("Enter Birthdate (DD-MM-YYYY): ");
        String birthdate = scanner.nextLine();

        Person person = new Person(id, firstName, lastName, address, birthdate);
        boolean success = pm.addPerson(person);

        if (success) {
            System.out.println("Person added successfully!");
        } else {
            System.out.println("Failed to add person. Invalid data.");
        }
    }

    // asking for all the necessary info to update a person
    private static void updatePersonFromInput(Scanner scanner, PersonManager pm) {

        System.out.print("Enter existing Person ID: ");
        String oldID = scanner.nextLine();

        System.out.print("Enter new Person ID: ");
        String newID = scanner.nextLine();

        System.out.print("Enter new First Name: ");
        String newFirstName = scanner.nextLine();

        System.out.print("Enter new Last Name: ");
        String newLastName = scanner.nextLine();

        System.out.print("Enter new Address (format: Street Number|Street|City|Victoria|Country): ");
        String newAddress = scanner.nextLine();

        System.out.print("Enter new Birthdate (format: DD-MM-YYYY): ");
        String newBirthdate = scanner.nextLine();

        try {
            boolean success = pm.updatePersonalDetails(
                    oldID, newID, newFirstName, newLastName, newAddress, newBirthdate);

            if (success) {
                System.out.println("Person details updated successfully.");
            } else {
                System.out.println("Failed to update person. Check rules and input formats.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the update.");
            e.printStackTrace();
        }
    }

    // asking for all the necessary info to demerit points
    private static void addDemeritPointsFromInput(Scanner scanner, PersonManager pm) {
        System.out.print("Enter Person ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter date of offense (DD-MM-YYYY): ");
        String date = scanner.nextLine();

        System.out.print("Enter number of demerit points (1 to 6): ");
        int points;
        try {
            points = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Try again.");
            return;
        }

        String result = pm.addDemeritPoints(id, date, points);
        if (result.equals("Success")) {
           return;
        } else {
            System.out.println("Failed to add demerit points. Check the inputs.");
        }
    }
}