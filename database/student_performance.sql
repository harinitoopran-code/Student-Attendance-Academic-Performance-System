CREATE DATABASE IF NOT EXISTS student_performance;
USE student_performance;
CREATE TABLE IF NOT EXISTS faculty (
 faculty_id INT AUTO_INCREMENT PRIMARY KEY, faculty_name VARCHAR(100) NOT NULL,
 email VARCHAR(120) NOT NULL UNIQUE, password_hash VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS students (
 student_id INT AUTO_INCREMENT PRIMARY KEY, roll_no VARCHAR(30) NOT NULL UNIQUE,
 student_name VARCHAR(100) NOT NULL, department VARCHAR(80) NOT NULL,
 academic_year VARCHAR(20) NOT NULL, section VARCHAR(10) NOT NULL,
 active BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE IF NOT EXISTS attendance (
 attendance_id INT AUTO_INCREMENT PRIMARY KEY, student_id INT NOT NULL,
 attendance_date DATE NOT NULL, status ENUM('PRESENT','ABSENT') NOT NULL,
 marked_by INT NOT NULL, UNIQUE(student_id,attendance_date),
 FOREIGN KEY(student_id) REFERENCES students(student_id),
 FOREIGN KEY(marked_by) REFERENCES faculty(faculty_id)
);
CREATE TABLE IF NOT EXISTS subjects (
 subject_id INT AUTO_INCREMENT PRIMARY KEY, subject_code VARCHAR(20) NOT NULL UNIQUE,
 subject_name VARCHAR(100) NOT NULL, max_internal_marks DECIMAL(5,2) NOT NULL DEFAULT 20
);
CREATE TABLE IF NOT EXISTS internal_marks (
 mark_id INT AUTO_INCREMENT PRIMARY KEY, student_id INT NOT NULL, subject_id INT NOT NULL,
 assessment_name VARCHAR(50) NOT NULL, marks_obtained DECIMAL(5,2) NOT NULL,
 entered_by INT NOT NULL, entered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 UNIQUE(student_id,subject_id,assessment_name),
 FOREIGN KEY(student_id) REFERENCES students(student_id),
 FOREIGN KEY(subject_id) REFERENCES subjects(subject_id),
 FOREIGN KEY(entered_by) REFERENCES faculty(faculty_id)
);
INSERT INTO subjects(subject_code,subject_name,max_internal_marks)
SELECT 'JAVA','Java Programming',20 WHERE NOT EXISTS(SELECT 1 FROM subjects WHERE subject_code='JAVA');
INSERT INTO subjects(subject_code,subject_name,max_internal_marks)
SELECT 'DBMS','Database Management Systems',20 WHERE NOT EXISTS(SELECT 1 FROM subjects WHERE subject_code='DBMS');
