package com.wipro.hr.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.wipro.hr.ems.student;

public class StudentDir {

    static ArrayList<student> studentList = new ArrayList<>();
    static final String FILE_NAME = "student.ser";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Deserialize from second run onwards
        deserializeStudents();

        String choice;
        do {
            System.out.println("\n----- STUDENT DIRECTORY -----");
            System.out.println("1. Add Student");
            System.out.println("2. Search Student by Roll Number");
            System.out.println("3. Search Student by Name");
            System.out.println("4. Delete Student by Roll Number");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextLine();

            switch (choice) {

                case "1":
                    System.out.print("Enter Roll Number: ");
                    int roll = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    studentList.add(new student(roll, name));
                    System.out.println("Student added successfully.");
                    break;

                case "2":
                    System.out.print("Enter Roll Number to search: ");
                    int searchRoll = Integer.parseInt(sc.nextLine());
                    boolean foundRoll = false;

                    for (student s : studentList) {
                        if (s.getRollNo() == searchRoll) {
                            System.out.println(s);
                            foundRoll = true;
                            break;
                        }
                    }
                    if (!foundRoll)
                        System.out.println("Student not found.");
                    break;

                case "3":
                    System.out.print("Enter Name to search: ");
                    String searchName = sc.nextLine();
                    boolean foundName = false;

                    for (student s : studentList) {
                        if (s.getName().equalsIgnoreCase(searchName)) {
                            System.out.println(s);
                            foundName = true;
                        }
                    }
                    if (!foundName)
                        System.out.println("Student not found.");
                    break;

                case "4":
                    System.out.print("Enter Roll Number to delete: ");
                    int deleteRoll = Integer.parseInt(sc.nextLine());
                    boolean removed =
                            studentList.removeIf(s -> s.getRollNo() == deleteRoll);

                    if (removed)
                        System.out.println("Student deleted successfully.");
                    else
                        System.out.println("Student not found.");
                    break;

                case "5":
                    if (studentList.isEmpty()) {
                        System.out.println("No students available.");
                    } else {
                        System.out.println("---- STUDENT LIST ----");
                        for (student s : studentList) {
                            System.out.println(s);
                        }
                    }
                    break;

                case "6":
                    serializeStudents();
                    System.out.println("Exiting application. Data saved.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (!choice.equals("6"));

        sc.close();
    }

    // Serialize
    private static void serializeStudents() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(studentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Deserialize
    @SuppressWarnings("unchecked")
    private static void deserializeStudents() {
        File file = new File(FILE_NAME);

        if (file.exists()) {
            try (ObjectInputStream ois =
                         new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                studentList = (ArrayList<student>) ois.readObject();
                System.out.println("Student data loaded from file.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}