package edu.student.performance;
import javax.servlet.annotation.WebServlet;import javax.servlet.http.*;import javax.servlet.*;import java.io.*;import java.sql.*;
@WebServlet("/attendance") public class AttendanceServlet extends HttpServlet {
 protected void doPost(HttpServletRequest q,HttpServletResponse p)throws IOException,ServletException{
  Integer fid=Auth.facultyId(q);if(fid==null){p.sendRedirect("login.jsp");return;}
  String sql="INSERT INTO attendance(student_id,attendance_date,status,marked_by) VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE status=VALUES(status),marked_by=VALUES(marked_by)";
  try(Connection c=DB.get();PreparedStatement s=c.prepareStatement(sql)){
   s.setInt(1,Integer.parseInt(q.getParameter("studentId")));s.setDate(2,Date.valueOf(q.getParameter("date")));
   String status=q.getParameter("status");if(!"PRESENT".equals(status)&&!"ABSENT".equals(status))throw new IllegalArgumentException("Invalid status");
   s.setString(3,status);s.setInt(4,fid);s.executeUpdate();p.sendRedirect("attendance.jsp?saved=1");
  }catch(SQLException x){throw new ServletException(x);}
 }
}
