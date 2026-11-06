package edu.student.performance;
import javax.servlet.http.*;
public class Auth {
 public static Integer facultyId(HttpServletRequest q){Object x=q.getSession(false)==null?null:q.getSession(false).getAttribute("facultyId");return x instanceof Integer?(Integer)x:null;}
}
