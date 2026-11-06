package edu.student.performance;
import javax.servlet.annotation.WebServlet;import javax.servlet.http.*;import javax.servlet.*;import java.io.*;import java.sql.*;import java.util.*;
@WebServlet("/report") public class ReportServlet extends HttpServlet {
 protected void doGet(HttpServletRequest q,HttpServletResponse p)throws IOException,ServletException{
  if(Auth.facultyId(q)==null){p.sendRedirect("login.jsp");return;}
  int id=Integer.parseInt(q.getParameter("studentId"));
  try(Connection c=DB.get();PreparedStatement s=c.prepareStatement("SELECT s.roll_no,s.student_name,COUNT(a.attendance_id) recorded,SUM(CASE WHEN a.status='PRESENT' THEN 1 ELSE 0 END) present,ROUND(100.0*SUM(CASE WHEN a.status='PRESENT' THEN 1 ELSE 0 END)/NULLIF(COUNT(a.attendance_id),0),2) pct FROM students s LEFT JOIN attendance a ON a.student_id=s.student_id WHERE s.student_id=? GROUP BY s.student_id,s.roll_no,s.student_name")){
   s.setInt(1,id);try(ResultSet r=s.executeQuery()){if(!r.next()){p.sendError(404);return;}q.setAttribute("roll",r.getString("roll_no"));q.setAttribute("name",r.getString("student_name"));q.setAttribute("recorded",r.getInt("recorded"));q.setAttribute("present",r.getInt("present"));q.setAttribute("pct",r.getBigDecimal("pct"));}
   q.getRequestDispatcher("/report.jsp").forward(q,p);
  }catch(SQLException x){throw new ServletException(x);}
 }
}
