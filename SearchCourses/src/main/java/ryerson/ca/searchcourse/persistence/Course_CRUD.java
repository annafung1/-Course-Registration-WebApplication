/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.searchcourse.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import ryerson.ca.searchcourse.helper.Instructor;
import ryerson.ca.searchcourse.helper.Course;

/**
 *
 * @instructor student
 */
public class Course_CRUD {
    
    private static Connection getCon(){
    Connection con=null;
     try{
         Class.forName("com.mysql.jdbc.Driver");
        String connection=System.getenv("DB_URL");
         //con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Waitlist_CRS?autoReconnect=true&useSSL=false", "root", "student");
         con=DriverManager.getConnection("jdbc:mysql://"+connection+"/Course_CRS?allowPublicKeyRetrieval=true&useSSL=false", "root", "student" );
         System.out.println("Connection established.");
     }
     catch(Exception e){ System.out.println(e);}
     return con;
    }
    
    public static Set<Course> searchForCourses(String query){
        Set<Course> courses= new HashSet<Course>();
        try{
            Connection con= getCon();
                  
                  String q = "select * from COURSE "
                   + "WHERE courseid LIKE '%"+query+"%'"
                   + " OR firstName LIKE '%"+
                  query+"%' OR lastName LIKE '%"+
                   query+"%';";
                  
                  
                    System.out.println(q);
			PreparedStatement ps=con.prepareStatement(q);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				//been= new UserInfo();
				String courseid=rs.getString("courseid");
				String coursename=rs.getString("coursename");
                                String spots=rs.getString("spots");
                                String firstname=rs.getString("firstName");
                                String lastname=rs.getString("lastName");
                                Instructor instructor= new Instructor(firstname, lastname);
                           
                                Course course = new Course(courseid,coursename,spots, instructor);
                                courses.add(course);
                                
                                }
			
			con.close();

		}catch(Exception e){System.out.println(e);}
            
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>"+courses.size());
        return courses;
        
    }
}
