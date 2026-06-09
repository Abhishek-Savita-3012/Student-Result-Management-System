import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ResultDAO {

    public void addResult() {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Roll Number: ");
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

            System.out.print("Enter Marks for Subject 1: ");
            int s1 = sc.nextInt();

            System.out.print("Enter Marks for Subject 2: ");
            int s2 = sc.nextInt();

            System.out.print("Enter Marks for Subject 3: ");
            int s3 = sc.nextInt();

            System.out.print("Enter Marks for Subject 4: ");
            int s4 = sc.nextInt();

            System.out.print("Enter Marks for Subject 5: ");
            int s5 = sc.nextInt();

            int total = s1 + s2 + s3 + s4 + s5;
            double percentage = total / 5.0;
            String grade = calculateGrade(percentage);

            String query = "INSERT INTO results (student_id, subject1, subject2, subject3, subject4, subject5, total_marks, percentage, grade) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, studentId);
            ps.setInt(2, s1);
            ps.setInt(3, s2);
            ps.setInt(4, s3);
            ps.setInt(5, s4);
            ps.setInt(6, s5);
            ps.setInt(7, total);
            ps.setDouble(8, percentage);
            ps.setString(9, grade);

            ps.executeUpdate();

            System.out.println("Result added successfully!");
            System.out.println("Total Marks: " + total);
            System.out.println("Percentage: " + percentage);
            System.out.println("Grade: " + grade);

        } catch (Exception e) {
            System.out.println("Error while adding result.");
            e.printStackTrace();
        }
    }

    public void viewAllResults() {
        try {
            Connection con = DBConnection.getConnection();

            String query = """
                    SELECT s.roll_no, s.name, s.class_name, s.section,
                           r.subject1, r.subject2, r.subject3, r.subject4, r.subject5,
                           r.total_marks, r.percentage, r.grade
                    FROM students s
                    JOIN results r ON s.student_id = r.student_id
                    """;

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- All Results ---");

            while (rs.next()) {
                System.out.println(
                        "Roll No: " + rs.getString("roll_no") +
                                ", Name: " + rs.getString("name") +
                                ", Class: " + rs.getString("class_name") +
                                ", Section: " + rs.getString("section") +
                                ", Total: " + rs.getInt("total_marks") +
                                ", Percentage: " + rs.getDouble("percentage") +
                                ", Grade: " + rs.getString("grade")
                );
            }

        } catch (Exception e) {
            System.out.println("Error while viewing results.");
            e.printStackTrace();
        }
    }

    private String calculateGrade(double percentage) {
        if (percentage >= 90) {
            return "A+";
        } else if (percentage >= 80) {
            return "A";
        } else if (percentage >= 70) {
            return "B";
        } else if (percentage >= 60) {
            return "C";
        } else if (percentage >= 50) {
            return "D";
        } else {
            return "Fail";
        }
    }

    public void searchResultByRollNo() {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Roll Number: ");
            String rollNo = sc.nextLine();

            String query = """
                SELECT s.roll_no, s.name, s.class_name, s.section,
                       r.subject1, r.subject2, r.subject3, r.subject4, r.subject5,
                       r.total_marks, r.percentage, r.grade
                FROM students s
                JOIN results r ON s.student_id = r.student_id
                WHERE s.roll_no = ?
                """;

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, rollNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\n--- Student Result ---");
                System.out.println("Roll No: " + rs.getString("roll_no"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Class: " + rs.getString("class_name"));
                System.out.println("Section: " + rs.getString("section"));
                System.out.println("Subject 1: " + rs.getInt("subject1"));
                System.out.println("Subject 2: " + rs.getInt("subject2"));
                System.out.println("Subject 3: " + rs.getInt("subject3"));
                System.out.println("Subject 4: " + rs.getInt("subject4"));
                System.out.println("Subject 5: " + rs.getInt("subject5"));
                System.out.println("Total Marks: " + rs.getInt("total_marks"));
                System.out.println("Percentage: " + rs.getDouble("percentage"));
                System.out.println("Grade: " + rs.getString("grade"));
            } else {
                System.out.println("Result not found for this roll number!");
            }

        } catch (Exception e) {
            System.out.println("Error while searching result.");
            e.printStackTrace();
        }
    }

    public void updateResult() {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Roll Number of student result to update: ");
            String rollNo = sc.nextLine();

            String findStudent = """
                SELECT s.student_id, r.result_id
                FROM students s
                JOIN results r ON s.student_id = r.student_id
                WHERE s.roll_no = ?
                """;

            PreparedStatement findPs = con.prepareStatement(findStudent);
            findPs.setString(1, rollNo);

            ResultSet rs = findPs.executeQuery();

            if (!rs.next()) {
                System.out.println("Result not found for this roll number!");
                return;
            }

            int resultId = rs.getInt("result_id");

            System.out.print("Enter New Marks for Subject 1: ");
            int s1 = sc.nextInt();

            System.out.print("Enter New Marks for Subject 2: ");
            int s2 = sc.nextInt();

            System.out.print("Enter New Marks for Subject 3: ");
            int s3 = sc.nextInt();

            System.out.print("Enter New Marks for Subject 4: ");
            int s4 = sc.nextInt();

            System.out.print("Enter New Marks for Subject 5: ");
            int s5 = sc.nextInt();

            int total = s1 + s2 + s3 + s4 + s5;
            double percentage = total / 5.0;
            String grade = calculateGrade(percentage);

            String updateQuery = """
                UPDATE results
                SET subject1 = ?, subject2 = ?, subject3 = ?, subject4 = ?, subject5 = ?,
                    total_marks = ?, percentage = ?, grade = ?
                WHERE result_id = ?
                """;

            PreparedStatement ps = con.prepareStatement(updateQuery);

            ps.setInt(1, s1);
            ps.setInt(2, s2);
            ps.setInt(3, s3);
            ps.setInt(4, s4);
            ps.setInt(5, s5);
            ps.setInt(6, total);
            ps.setDouble(7, percentage);
            ps.setString(8, grade);
            ps.setInt(9, resultId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Result updated successfully!");
                System.out.println("Updated Total Marks: " + total);
                System.out.println("Updated Percentage: " + percentage);
                System.out.println("Updated Grade: " + grade);
            } else {
                System.out.println("Result update failed!");
            }

        } catch (Exception e) {
            System.out.println("Error while updating result.");
            e.printStackTrace();
        }
    }

    public void viewStatistics() {
        try {
            Connection con = DBConnection.getConnection();

            // Total Students
            String totalStudentsQuery = "SELECT COUNT(*) AS total_students FROM students";
            PreparedStatement totalPs = con.prepareStatement(totalStudentsQuery);
            ResultSet totalRs = totalPs.executeQuery();

            int totalStudents = 0;
            if (totalRs.next()) {
                totalStudents = totalRs.getInt("total_students");
            }

            // Average Percentage
            String avgQuery = "SELECT AVG(percentage) AS avg_percentage FROM results";
            PreparedStatement avgPs = con.prepareStatement(avgQuery);
            ResultSet avgRs = avgPs.executeQuery();

            double avgPercentage = 0;
            if (avgRs.next()) {
                avgPercentage = avgRs.getDouble("avg_percentage");
            }

            // Topper
            String topperQuery = """
                SELECT s.roll_no, s.name, r.percentage
                FROM students s
                JOIN results r ON s.student_id = r.student_id
                ORDER BY r.percentage DESC
                LIMIT 1
                """;

            PreparedStatement topperPs = con.prepareStatement(topperQuery);
            ResultSet topperRs = topperPs.executeQuery();

            // Lowest Scorer
            String lowestQuery = """
                SELECT s.roll_no, s.name, r.percentage
                FROM students s
                JOIN results r ON s.student_id = r.student_id
                ORDER BY r.percentage ASC
                LIMIT 1
                """;

            PreparedStatement lowestPs = con.prepareStatement(lowestQuery);
            ResultSet lowestRs = lowestPs.executeQuery();

            // Passed Students
            String passQuery = "SELECT COUNT(*) AS passed FROM results WHERE grade != 'Fail'";
            PreparedStatement passPs = con.prepareStatement(passQuery);
            ResultSet passRs = passPs.executeQuery();

            int passed = 0;
            if (passRs.next()) {
                passed = passRs.getInt("passed");
            }

            // Failed Students
            String failQuery = "SELECT COUNT(*) AS failed FROM results WHERE grade = 'Fail'";
            PreparedStatement failPs = con.prepareStatement(failQuery);
            ResultSet failRs = failPs.executeQuery();

            int failed = 0;
            if (failRs.next()) {
                failed = failRs.getInt("failed");
            }

            System.out.println("\n===== STUDENT STATISTICS =====");
            System.out.println("Total Students     : " + totalStudents);
            System.out.printf("Average Percentage : %.2f%n", avgPercentage);

            if (topperRs.next()) {
                System.out.println("\nTopper:");
                System.out.println("Roll No            : " + topperRs.getString("roll_no"));
                System.out.println("Name               : " + topperRs.getString("name"));
                System.out.println("Percentage         : " + topperRs.getDouble("percentage"));
            }

            if (lowestRs.next()) {
                System.out.println("\nLowest Scorer:");
                System.out.println("Roll No            : " + lowestRs.getString("roll_no"));
                System.out.println("Name               : " + lowestRs.getString("name"));
                System.out.println("Percentage         : " + lowestRs.getDouble("percentage"));
            }

            System.out.println("\nPassed Students    : " + passed);
            System.out.println("Failed Students    : " + failed);

        } catch (Exception e) {
            System.out.println("Error while viewing statistics.");
            e.printStackTrace();
        }
    }

    public void generateReportCard() {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter Roll Number: ");
            String rollNo = sc.nextLine();

            String query = """
                SELECT s.roll_no, s.name, s.class_name, s.section,
                       r.subject1, r.subject2, r.subject3, r.subject4, r.subject5,
                       r.total_marks, r.percentage, r.grade
                FROM students s
                JOIN results r ON s.student_id = r.student_id
                WHERE s.roll_no = ?
                """;

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, rollNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String fileName = "ReportCards/" + rollNo + "_Report.txt";

                String reportCard =
                        "=================================\n" +
                                "        STUDENT REPORT CARD       \n" +
                                "=================================\n\n" +
                                "Roll No    : " + rs.getString("roll_no") + "\n" +
                                "Name       : " + rs.getString("name") + "\n" +
                                "Class      : " + rs.getString("class_name") + "\n" +
                                "Section    : " + rs.getString("section") + "\n\n" +
                                "Subject 1  : " + rs.getInt("subject1") + "\n" +
                                "Subject 2  : " + rs.getInt("subject2") + "\n" +
                                "Subject 3  : " + rs.getInt("subject3") + "\n" +
                                "Subject 4  : " + rs.getInt("subject4") + "\n" +
                                "Subject 5  : " + rs.getInt("subject5") + "\n\n" +
                                "Total      : " + rs.getInt("total_marks") + "\n" +
                                "Percentage : " + String.format("%.2f", rs.getDouble("percentage")) + "\n" +
                                "Grade      : " + rs.getString("grade") + "\n\n" +
                                "=================================\n";

                System.out.println("\n" + reportCard);

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    writer.write(reportCard);
                }

                System.out.println("Report card generated successfully!");
                System.out.println("File Name: " + fileName);

            } else {
                System.out.println("Result not found for this roll number!");
            }

        } catch (IOException e) {
            System.out.println("Error while writing report card file.");
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println("Error while generating report card.");
            e.printStackTrace();
        }
    }
}