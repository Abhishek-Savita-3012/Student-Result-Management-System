import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class StudentDAO {

    public void addStudent() {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Roll Number: ");
            String rollNo = sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Class: ");
            String className = sc.nextLine();

            System.out.print("Enter Section: ");
            String section = sc.nextLine();

            String query = "INSERT INTO students (roll_no, name, class_name, section) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, rollNo);
            ps.setString(2, name);
            ps.setString(3, className);
            ps.setString(4, section);

            ps.executeUpdate();

            System.out.println("Student added successfully!");

        } catch (Exception e) {
            System.out.println("Error while adding student.");
            e.printStackTrace();
        }
    }

    public void viewStudents() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM students";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Student List ---");

            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("student_id") +
                                ", Roll No: " + rs.getString("roll_no") +
                                ", Name: " + rs.getString("name") +
                                ", Class: " + rs.getString("class_name") +
                                ", Section: " + rs.getString("section")
                );
            }

        } catch (Exception e) {
            System.out.println("Error while viewing students.");
            e.printStackTrace();
        }
    }

    public void searchStudentByRollNo() {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Roll Number: ");
            String rollNo = sc.nextLine();

            String query = "SELECT * FROM students WHERE roll_no = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, rollNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\n--- Student Details ---");
                System.out.println("Student ID: " + rs.getInt("student_id"));
                System.out.println("Roll No: " + rs.getString("roll_no"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Class: " + rs.getString("class_name"));
                System.out.println("Section: " + rs.getString("section"));
            } else {
                System.out.println("Student not found!");
            }

        } catch (Exception e) {
            System.out.println("Error while searching student.");
            e.printStackTrace();
        }
    }

    public void updateStudent() {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Roll Number of student to update: ");
            String rollNo = sc.nextLine();

            String checkQuery = "SELECT * FROM students WHERE roll_no = ?";
            PreparedStatement checkPs = con.prepareStatement(checkQuery);
            checkPs.setString(1, rollNo);

            ResultSet rs = checkPs.executeQuery();

            if (!rs.next()) {
                System.out.println("Student not found!");
                return;
            }

            System.out.print("Enter New Name: ");
            String name = sc.nextLine();

            System.out.print("Enter New Class: ");
            String className = sc.nextLine();

            System.out.print("Enter New Section: ");
            String section = sc.nextLine();

            String updateQuery = "UPDATE students SET name = ?, class_name = ?, section = ? WHERE roll_no = ?";
            PreparedStatement ps = con.prepareStatement(updateQuery);

            ps.setString(1, name);
            ps.setString(2, className);
            ps.setString(3, section);
            ps.setString(4, rollNo);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student update failed!");
            }

        } catch (Exception e) {
            System.out.println("Error while updating student.");
            e.printStackTrace();
        }
    }

    public void deleteStudent() {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Roll Number of student to delete: ");
            String rollNo = sc.nextLine();

            String findStudent = "SELECT student_id FROM students WHERE roll_no = ?";
            PreparedStatement findPs = con.prepareStatement(findStudent);
            findPs.setString(1, rollNo);

            ResultSet rs = findPs.executeQuery();

            if (!rs.next()) {
                System.out.println("Student not found!");
                return;
            }

            int studentId = rs.getInt("student_id");

            System.out.print("Are you sure you want to delete this student? (Y/N): ");
            String confirm = sc.nextLine();

            if (!confirm.equalsIgnoreCase("Y")) {
                System.out.println("Delete cancelled.");
                return;
            }

            String deleteResult = "DELETE FROM results WHERE student_id = ?";
            PreparedStatement resultPs = con.prepareStatement(deleteResult);
            resultPs.setInt(1, studentId);
            resultPs.executeUpdate();

            String deleteStudent = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement studentPs = con.prepareStatement(deleteStudent);
            studentPs.setInt(1, studentId);

            int rows = studentPs.executeUpdate();

            if (rows > 0) {
                System.out.println("Student and related result deleted successfully!");
            } else {
                System.out.println("Student delete failed!");
            }

        } catch (Exception e) {
            System.out.println("Error while deleting student.");
            e.printStackTrace();
        }
    }
}