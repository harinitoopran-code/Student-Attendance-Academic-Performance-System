package edu.student.performance;
import javax.servlet.annotation.WebServlet;import javax.servlet.http.*;import javax.servlet.*;import java.io.*;import java.sql.*;import java.util.*;
@WebServlet("/students") public class StudentServlet extends HttpServlet {
 protected void doGet(HttpServletRequest q,HttpServletResponse p)throws IOException,ServletException{
  if(Auth.facultyId(q)==null){p.sendRedirect("login.jsp");return;} List<Map<String,Object>> rows=new ArrayList<>();
  try(Connection c=DB.get();PreparedStatement s=c.prepareStatement("SELECT * FROM students WHERE active=1 ORDER BY roll_no");ResultSet r=s.executeQuery()){
   while(r.next()){Map<String,Object> m=new HashMap<>();m.put("id",r.getInt("student_id"));m.put("roll",r.getString("roll_no"));m.put("name",r.getString("student_name"));m.put("dept",r.getString("department"));m.put("year",r.getString("academic_year"));m.put("section",r.getString("section"));rows.add(m);}
   q.setAttribute("students",rows);q.getRequestDispatcher("/students.jsp").forward(q,p);
  }catch(SQLException x){throw new ServletException(x);}
 }
 protected void doPost(HttpServletRequest q,HttpServletResponse p)throws IOException,ServletException{
  if(Auth.facultyId(q)==null){p.sendRedirect("login.jsp");return;}
  String sql="INSERT INTO students(roll_no,student_name,department,academic_year,section) VALUES(?,?,?,?,?)";
  try(Connection c=DB.get();PreparedStatement s=c.prepareStatement(sql)){s.setString(1,q.getParameter("roll").trim());s.setString(2,q.getParameter("name").trim());s.setString(3,q.getParameter("department").trim());s.setString(4,q.getParameter("year").trim());s.setString(5,q.getParameter("section").trim());s.executeUpdate();p.sendRedirect("students");}
  catch(SQLException x){throw new ServletException(x);}
 }
}
