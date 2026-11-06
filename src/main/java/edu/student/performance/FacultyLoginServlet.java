package edu.student.performance;
import javax.servlet.annotation.WebServlet;import javax.servlet.http.*;import javax.servlet.*;import java.io.*;import java.sql.*;import org.mindrot.jbcrypt.BCrypt;
@WebServlet("/faculty-login") public class FacultyLoginServlet extends HttpServlet {
 protected void doPost(HttpServletRequest q,HttpServletResponse p)throws IOException,ServletException{
  try(Connection c=DB.get();PreparedStatement s=c.prepareStatement("SELECT faculty_id,faculty_name,password_hash FROM faculty WHERE email=?")){
   s.setString(1,q.getParameter("email").trim().toLowerCase());
   try(ResultSet r=s.executeQuery()){if(r.next()&&BCrypt.checkpw(q.getParameter("password"),r.getString("password_hash"))){HttpSession h=q.getSession(true);h.setAttribute("facultyId",r.getInt(1));h.setAttribute("facultyName",r.getString(2));p.sendRedirect("dashboard.jsp");return;}}
   p.sendRedirect("login.jsp?error=1");
  }catch(SQLException x){throw new ServletException(x);}
 }
}
