# Student Attendance and Academic Performance Management System

Java 11 + JSP/Servlets (`javax.servlet`) + MySQL 8 + Tomcat 9 Maven WAR starter.

## Requirements
JDK 11, Tomcat 9, MySQL 8, MySQL Workbench, Maven 3.8+, Git. Docker Desktop/Kubernetes/Selenium are optional DevOps extensions.

## Database
Run `database/student_performance.sql` in MySQL Workbench. Create a faculty account with a BCrypt password hash; do not store plain-text passwords. Configure DB_URL, DB_USER and DB_PASSWORD environment variables.

## Build and run
```bash
mvn clean package
```
Deploy `target/StudentPerformance.war` to Tomcat and open:
`http://localhost:8080/StudentPerformance/`

## Modules
- Faculty login/logout
- Student registration and listing
- Daily attendance entry (unique student/date)
- Attendance percentage report
- Subject and internal marks entry
- Student performance report

This is an educational starter. Review the TODO items and validate institutional grading/working-day rules with your faculty guide.
