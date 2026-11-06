package edu.student.performance;
import javax.servlet.annotation.WebServlet;import javax.servlet.http.*;import javax.servlet.*;import java.io.*;import java.sql.*;import java.math.*;
@WebServlet("/marks") public class MarksServlet extends HttpServlet {
 protected void doPost(HttpServletRequest q,HttpServletResponse p)throws IOException,ServletException{
  Integer fid=Auth.facultyId(q);if(fid==null){p.sendRedirect("login.jsp");return;}
  int sid=Integer.parseInt(q.getParameter("studentId")),sub=Integer.parseInt(q.getParameter("subjectId"));
  BigDecimal marks=new BigDecimal(q.getParameter("marks"));
  try(Connection c=DB.get()){
   BigDecimal max;try(PreparedStatement z=c.prepareStatement("SELECT max_internal_marks FROM subjects WHERE subject_id=?")){z.setInt(1,sub);try(ResultSet r=z.executeQuery()){if(!r.next())throw new ServletException("Subject not found");max=r.getBigDecimal(1);}}
   if(marks.signum()<0||marks.compareTo(max)>0)throw new ServletException("Marks must be between 0 and "+max);
   String sql="INSERT INTO internal_marks(student_id,subject_id,assessment_name,marks_obtained,entered_by) VALUES(?,?,?,?,?) ON DUPLICATE KEY UPDATE marks_obtained=VALUES(marks_obtained),entered_by=VALUES(entered_by),entered_at=CURRENT_TIMESTAMP";
   try(PreparedStatement s=c.prepareStatement(sql)){s.setInt(1,sid);s.setInt(2,sub);s.setString(3,q.getParameter("assessment"));s.setBigDecimal(4,marks);s.setInt(5,fid);s.executeUpdate();}
   p.sendRedirect("marks.jsp?saved=1");
  }catch(SQLException x){throw new ServletException(x);}
 }
}
