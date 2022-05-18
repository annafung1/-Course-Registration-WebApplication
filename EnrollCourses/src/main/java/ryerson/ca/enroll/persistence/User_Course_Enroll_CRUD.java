
package ryerson.ca.enroll.persistence;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import ryerson.ca.enroll.helper.CourseEnroll;

public class User_Course_Enroll_CRUD {
   public static Connection getCon() throws ClassNotFoundException{
       Connection con=null;
     try{
         
         
     
         Class.forName("com.mysql.jdbc.Driver");
        String connection=System.getenv("DB_URL");
         //con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Waitlist_CRS?autoReconnect=true&useSSL=false", "root", "student");
         con=DriverManager.getConnection("jdbc:mysql://"+connection+"/Enroll_CRS?allowPublicKeyRetrieval=true&useSSL=false", "root", "student" );
        System.out.println ("Connection Established.");
         
         System.out.println("Connection established.");
     }
     catch(Exception e){ System.out.println(e);}
     return con;
     
    }
    
    public static Set<CourseEnroll> getEnrolledCourses(String spots){
        Set<CourseEnroll> courses= new HashSet<CourseEnroll>();
        try{
            Connection con= getCon();
            String q;
            if(spots.isEmpty()){
               q="select * from USER_COURSE_Enroll "
                 +";";
            }
            else
             q = "select * from USER_COURSE_Enroll "
                    + " WHERE spots LIKE '"+spots+"'"+";";

			PreparedStatement ps=con.prepareStatement(q);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){

				String courseid=rs.getString("courseid");
                                String date=rs.getDate("enrollDate").toString();
                                CourseEnroll course = new CourseEnroll(courseid,date, spots);
                                courses.add(course);
                                
                                }
			
			con.close();

		}catch(Exception e){System.out.println(e);}
           
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>"+courses.size());
        return courses;
        
    }
    public static CourseEnroll getEnrolledCourses(String spots, String courseid){
        CourseEnroll course=null;
        try{
            Connection con= getCon();
            
            String q = "select * from USER_COURSE_Enroll "
                    + " WHERE spots LIKE '"+spots+"'"+" and "
                    + "courseid LIKE '"+courseid+"';";

			PreparedStatement ps=con.prepareStatement(q);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
                                String date=rs.getDate("enrollDate").toString();
                                 course = new CourseEnroll(courseid,date,spots);
                     
                                }
			
			con.close();

		}catch(Exception e){System.out.println(e);}
       
        return course;
        
    }
    
    
    public static boolean enroll(String spots, String courseid){
      
        try{
            Connection con= getCon();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
             LocalDate date = LocalDate.now();
            System.out.println(date.format(formatter));
            String q = "insert into USER_COURSE_Enroll "
                    + "(courseid, spots, enrollDate) values "
                    + "('"+courseid+"', '"+spots+"', '"+date.format(formatter)+"');";
            Statement stmt = con.createStatement(); 
            stmt.execute(q);
            
			con.close();
                        return true;

		}catch(Exception e){System.out.println(e);
                return false;
                }
 
        
    }  
}
