/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.user.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author 91771
 */
public class StudentDbUtil {
    private DataSource dataSource;
    
    public StudentDbUtil(DataSource theDataSource)
    {
        dataSource = theDataSource;
    }
    
    public List<Student> getStudents() throws Exception 
    {
        List<Student> students=new ArrayList<>();
        
        Connection myConn=null;
        Statement myStmt=null;
        ResultSet Rs=null;
        
        
        try{
        //GET A CONNECTION
        myConn=dataSource.getConnection();
        
        //Create SQL Statement
        String sql="select*from student order by last_name";
        
        myStmt=myConn.createStatement();
        
        //Execute Query
        Rs=myStmt.executeQuery(sql);
        
        //Process Result Set
        while(Rs.next())   //next() method returns true if the ResultSet has a next record, and moves the ResultSet to point to the next record
        {
            //retrieve data from result set row
            int id=Rs.getInt("id");
            String firstName=Rs.getString("first_name");
            String lastName=Rs.getString("last_name");
            String email=Rs.getString("email");
            
            //create new student object
            Student tempStudent=new Student(id, firstName, lastName, email);
            
            //add it to list of students
            students.add(tempStudent);
        }
        
        //Close JDBC Objects
        
        return students;
           }
        finally
        {
            close(myConn,myStmt,Rs);
        }
    
    }
    
    
    public void addStudents(Student theStudent) throws Exception
   
    {
         Connection myConn=null;
        PreparedStatement myStmt=null;
        
        try{
            //Get DB Connection
            myConn=dataSource.getConnection();
            
        //Create SQL for Insert
        String sql="insert into student(first_name,last_name,email) values(?,?,?)";
        myStmt=myConn.prepareStatement(sql);
        
        //Set Param Values For The Students
        myStmt.setString(1, theStudent.getFirstName());
        myStmt.setString(2, theStudent.getLastName());
        myStmt.setString(3, theStudent.getEmail());
        
        //Execute SQL Query
        myStmt.execute();
        }
        finally
        {
            close(myConn,myStmt,null);
        }
    }
    private void close(Connection myConn, Statement myStmt, ResultSet Rs) {
       
        try{
            if(Rs!=null)
            {
                Rs.close();
            }
            if(myStmt!=null)
            {
                myStmt.close();
            }
            if(myConn!=null)
            {
                myConn.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

       public Student getStudents(String theStudentId) throws Exception {
           Student theStudent=null;
           Connection myConn=null;
        PreparedStatement myStmt=null;
        ResultSet Rs=null;
        int StudentId;
        
        try
        {
            //Convert Student Id into Int
            StudentId=Integer.parseInt(theStudentId);
            
            //Get Connection From Database
            myConn=dataSource.getConnection();
            
            //Create SQL Query to get select Studnet
            String sql="select*from student where id=?";
            
            //Create Prepare Statement
            myStmt=myConn.prepareStatement(sql);
            
            //Set Params
            myStmt.setInt(1,StudentId);
            
            //Execute Query
            Rs=myStmt.executeQuery();
            
            //Retrieve Data From ResultSet Row
            if(Rs.next())
            {
                String firstName=Rs.getString("first_name");
                String lastName=Rs.getString("last_name");
                String email=Rs.getString("email");
            
            
            //Use studentId During Construction
            theStudent=new Student(StudentId,firstName,lastName,email);
        }
        else{
                throw new Exception("Could not find student id:"+StudentId);
            }
                
            return theStudent;
        }
        finally{
            close(myConn,myStmt,Rs);
        }
       }

    void updateStudents(Student theStudent) throws Exception{
        
        Connection myConn=null;
        PreparedStatement myStmt=null;
        
        try{
        //Get DB Connection 
        myConn=dataSource.getConnection();
        
        //Create SQL query
        String sql="update student "
                    + "set first_name=?, last_name=?, email=? "
                    + "where id=?";
        
        //Prepare Statement
        myStmt=myConn.prepareStatement(sql);
        
        //Set Params
        myStmt.setString(1,theStudent.getFirstName());
        myStmt.setString(2,theStudent.getLastName());
        myStmt.setString(3,theStudent.getEmail());
        myStmt.setInt(4,theStudent.getId());
        
        //Execute SQL Queries
        myStmt.execute();
        }
        finally
        {
            close(myConn,myStmt,null);
        }
    }
    
    void deleteStudents(String theStudentId) throws Exception{
        
        Connection myConn=null;
        PreparedStatement myStmt=null;
        
        try{
         //Convert Student Id into Int
         int StudentId=Integer.parseInt(theStudentId);
         
        //Get DB Connection 
        myConn=dataSource.getConnection();
        
        //Create SQL query
        String sql="delete from student where id=?";
                   
        
        //Prepare Statement
        myStmt=myConn.prepareStatement(sql);
        
        //Set Params
    
        myStmt.setInt(1,StudentId);
        
        //Execute SQL Queries
        myStmt.execute();
        }
        finally
        {
            close(myConn,myStmt,null);
        }
    }
}
