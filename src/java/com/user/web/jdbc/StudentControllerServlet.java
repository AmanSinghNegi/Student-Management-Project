/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.user.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author 91771
 */
@WebServlet(name = "StudentControllerServlet", urlPatterns = {"/StudentControllerServlet"})
public class StudentControllerServlet extends HttpServlet {

    private StudentDbUtil studentDbUtil;
    
    @Resource(name="jdbc/student_web_tracker")
    private DataSource dataSource;
    
    public void init() throws ServletException
    {
        super.init();
        
        try
        {
            studentDbUtil=new StudentDbUtil(dataSource);
        }
        catch(Exception e)
        {
            throw new ServletException(e);
        }
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try {
            
            String theCommand=request.getParameter("command");
            
            if(theCommand==null)
            {
                theCommand="LIST";
            }
            
            switch(theCommand)
            {
                case "LIST":
                    listStudents(request,response);
                    break;
                    
                case "ADD":
                    addStudents(request,response);
                    break;
                    
                case "LOAD":
                    loadStudents(request,response);
                    break;
                    
                case "UPDATE":
                    updateStudents(request,response);
                    break;
                    
                case "DELETE":
                    deleteStudents(request,response);
                    break;
                    
                default:
                    listStudents(request,response);
            }
                    
            listStudents(request,response);
        } catch (Exception ex) {
            ex.printStackTrace();
             }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        //Get Students From DbUtil
//       List<Student> students=studentDbUtil.getStudents();
       
       //Add Students To The Request
//       request.setAttribute("Student_List", students);
//       
//       //Send Request To The JSP (VIEW)
//       RequestDispatcher dispatcher =request.getRequestDispatcher("/list-students.jsp");
//       dispatcher.forward(request, response);
       
            PrintWriter out=response.getWriter();
//            
                int sPageid=1;
                if(request.getParameter("page")!=null)
            sPageid=Integer.parseInt(request.getParameter("page"));
//            out.print("page:"+sPageid);
            int total=5;
 
            if(sPageid==1){}
            else{
                sPageid=sPageid-1;
                sPageid=sPageid*total+1;
            }
//            
            List<Student> list=studentDbUtil.getStudents(sPageid,total);
//           out.print(pageId);
//            out.print("<h1>Page No."+sPageid+"</h1>");
//            out.print("<table border='1' cellpadding='4' width='60%'>");
//            
//            out.print("<tr><th>Id</th><th>First_Name</th><th>Last_Name</th><th>Email</th>");
//            for(Student s:list)
//            {
//                out.print("<tr><td>"+s.getId()
//                        +"</td><td>"+s.getFirstName()
//                                +"</td><td>"+s.getLastName()
//                                +"</td><td>"+s.getEmail()+"</td></tr>");
//            }
//            out.print("</table>");
//            
//                        out.print("<a href='StudentControllerServlet?page=1'>1</a>");
//                        out.print("<a href='StudentControllerServlet?page=2'>2</a>");
//                       out.print("<a href='StudentControllerServlet?page=3'>3</a>");
            
//            Add Students To The Request
       request.setAttribute("Student_List", list);
       
       //Send Request To The JSP (VIEW)
       RequestDispatcher dispatcher =request.getRequestDispatcher("/list-students.jsp");
       dispatcher.forward(request, response);
    }   

    private void addStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String first_name=request.getParameter("fname");
                String last_name=request.getParameter("lname");
                String email=request.getParameter("uemail");
        
                 Student theStudent=new Student(first_name,last_name,email);
                
                
                 studentDbUtil.addStudents(theStudent);
                 
                 listStudents(request,response);
                               
    }

    private void loadStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String theStudentId=request.getParameter("studentId");
        
        //Get Student From Database (Db Util)
        Student theStudent=studentDbUtil.getStudents(theStudentId);
        
        //Place Student in a Request Attributes
        request.setAttribute("THE_STUDENT", theStudent);
        
        //Send to jsp page
        
        
        RequestDispatcher dispatcher=request.getRequestDispatcher("/update-student-form.jsp");
        dispatcher.forward(request, response);
    }

    private void updateStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{

        
        //Read Student info from the data
       int id= Integer.parseInt(request.getParameter("studentId"));
       String FirstName=request.getParameter("fname");
       String LastName=request.getParameter("lname");
       String Email=request.getParameter("uemail");
       
       //Create New Student Object
        Student student=new Student(id,FirstName,LastName,Email);
        
        //Perfrom Update On Database
        studentDbUtil.updateStudents(student);
        
        //Send Them Back To List Student Page
        listStudents(request,response);
    }

    private void deleteStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String theStudentId= request.getParameter("studentId");
       
      
       studentDbUtil.deleteStudents(theStudentId);
       
       listStudents(request,response);
    }
}
