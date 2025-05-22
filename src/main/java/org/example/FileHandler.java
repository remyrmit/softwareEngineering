package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

  //  private static final String FILE_PATH = "people.txt";

//    public static void appendPersonToFile(Person person) throws IOException {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
//            writer.write(formatPerson(person));
//            writer.newLine();
//        }
//    }

//    public static void overwriteFileWithAllPeople(List<Person> people) throws IOException {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
//            for (Person person : people) {
//                writer.write(formatPerson(person));
//                writer.newLine();
//            }
//        }
//    }

//    public static List<Person> loadFromFile() {
//        List<Person> people = new ArrayList<>();
//        File file = new File(FILE_PATH);
//
//        if (!file.exists()) {
//            try {
//                file.getParentFile().mkdirs(); // Create folders if missing
//                file.createNewFile();          // Create file if missing
//            } catch (IOException e) {
//                e.printStackTrace();
//                return people;
//            }
//        }
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length == 5) {
//                    people.add(new Person(parts[0], parts[1], parts[2], parts[3], parts[4]));
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return people;
//    }

//    public static List<Person> loadFromFile() {
//        List<Person> people = new ArrayList<>();
//        File file = new File(FILE_PATH);
//
//        if (!file.exists()) {
//            try {
//                file.getParentFile().mkdirs();
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//                return people;
//            }
//        }
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length == 6) {
//                    Person p = new Person(parts[0], parts[1], parts[2], parts[3], parts[4]);
//                    p.setSuspended(Boolean.parseBoolean(parts[5]));
//                    people.add(p);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return people;
//    }


    // loading data from file // creating a new file if one doesn't exist
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

//
//    public static boolean removePersonById(String id, String filename) throws IOException {
//        List<Person> people = loadFromFile(filename);
//        boolean removed = people.removeIf(p -> p.getPersonID().equals(id));
//        if (removed) {
//            overwriteFileWithAllPeople(people, filename);
//        }
//        return removed;
//    }

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

