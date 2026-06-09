import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentDAO studentDAO = new StudentDAO();
        ResultDAO resultDAO = new ResultDAO();

        while (true) {
            System.out.println("\n===== Student Result Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Add Result");
            System.out.println("4. View All Results");
            System.out.println("5. Search Student by Roll Number");
            System.out.println("6. Search Result by Roll Number");
            System.out.println("7. Update Student");
            System.out.println("8. Update Result");
            System.out.println("9. Delete Student");
            System.out.println("10. View Statistics");
            System.out.println("11. Generate Report Card");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    studentDAO.addStudent();
                    break;

                case 2:
                    studentDAO.viewStudents();
                    break;

                case 3:
                    resultDAO.addResult();
                    break;

                case 4:
                    resultDAO.viewAllResults();
                    break;

                case 5:
                    studentDAO.searchStudentByRollNo();
                    break;

                case 6:
                    resultDAO.searchResultByRollNo();
                    break;

                case 7:
                    studentDAO.updateStudent();
                    break;

                case 8:
                    resultDAO.updateResult();
                    break;

                case 9:
                    studentDAO.deleteStudent();
                    break;

                case 10:
                    resultDAO.viewStatistics();
                    break;

                case 11:
                    resultDAO.generateReportCard();
                    break;

                case 12:
                    System.out.println("Thank you for using the system!");
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}