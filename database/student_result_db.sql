CREATE DATABASE student_result_db;
USE student_result_db;

CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    roll_no VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    class_name VARCHAR(20),
    section VARCHAR(10)
);

CREATE TABLE results (
    result_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    subject1 INT,
    subject2 INT,
    subject3 INT,
    subject4 INT,
    subject5 INT,
    total_marks INT,
    percentage DECIMAL(5,2),
    grade VARCHAR(10),
    FOREIGN KEY (student_id) REFERENCES students(student_id)
);