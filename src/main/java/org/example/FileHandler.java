// this class handles the files: loading, appending and overwriting

package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    // loading data from file and creating a new file if one doesn't exist
    public static List<Person> loadFromFile(String filename) {
        List<Person> people = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) {
            try {
                if (file.getParentFile() != null)
                    file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return people;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    Person p = new Person(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    p.setSuspended(Boolean.parseBoolean(parts[5]));
                    people.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return people;
    }

    // adding person to a file
    public static void appendPersonToFile(Person person, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(formatPerson(person));
            writer.newLine();
        }
    }

    // editing the file info: updating/deleting
    public static void overwriteFileWithAllPeople(List<Person> people, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Person person : people) {
                writer.write(formatPerson(person));
                writer.newLine();
            }
        }
    }

    // setting format for Person data
    private static String formatPerson(Person person) {
        return person.getPersonID() + "," +
                person.getFirstName() + "," +
                person.getLastName() + "," +
                person.getAddress() + "," +
                person.getBirthdate() + "," +
                person.isSuspended();
    }
}