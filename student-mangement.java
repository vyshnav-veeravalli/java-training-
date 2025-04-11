import java.io.*;
import java.util.*;

class Student {
    String id;
    String name;
    int age;

    public Student(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + age;
    }

    public static Student fromString(String line) {
        String[] parts = line.split(",");
        return new Student(parts[0], parts[1], Integer.parseInt(parts[2]));
    }
}

public class StudentManagementSystem {
    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Delete Student");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    deleteStudent(scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void addStudent(Scanner scanner) throws IOException {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        Student student = new Student(id, name, age);
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
        writer.write(student.toString());
        writer.newLine();
        writer.close();

        System.out.println("Student added successfully!");
    }

    static void viewStudents() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        String line;
        System.out.println("\n--- Student List ---");
        while ((line = reader.readLine()) != null) {
            Student student = Student.fromString(line);
            System.out.println("ID: " + student.id + ", Name: " + student.name + ", Age: " + student.age);
        }
        reader.close();
    }

    static void deleteStudent(Scanner scanner) throws IOException {
        System.out.print("Enter Student ID to delete: ");
        String deleteId = scanner.nextLine();
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        boolean found = false;
        while ((line = reader.readLine()) != null) {
            Student student = Student.fromString(line);
            if (!student.id.equals(deleteId)) {
                writer.write(student.toString());
                writer.newLine();
            } else {
                found = true;
            }
        }

        writer.close();
        reader.close();

        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            System.out.println("Error updating file.");
            return;
        }

        if (found) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student ID not found.");
        }
    }
}
