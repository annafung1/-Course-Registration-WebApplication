
package ryerson.ca.enroll.persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import ryerson.ca.enroll.helper.CourseEnroll;

public class COURSE_Waitlist_CRUD {
 public static Connection getCon() throws ClassNotFoundException, SQLException{
       Connection con=null;
     try{
         Class.forName("com.mysql.jdbc.Driver");
        String connection=System.getenv("DB_URL");
        //String connection ="localhost:3306";
         con=DriverManager.getConnection("jdbc:mysql://"+connection+"/Enroll_CRS?allowPublicKeyRetrieval=true&useSSL=false", "root", "student" );
        
         
         System.out.println("Connection established.");
     }
     catch(Exception e){ System.out.println(e);}
     return con;
     
    }
    
    public static boolean isOnWaitlist(String courseid){
       boolean result;
        try{
            Connection con= getCon();
            
        
            
            String q = "select * from Course_Waitlist "
                    + " WHERE courseid LIKE '"+courseid+"'"+";";

			PreparedStatement ps=con.prepareStatement(q);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){

			result=true;
                                
                                }
                        else
                            result=false;
			
			con.close();

		}catch(Exception e){return false;}
            return result;
    }
    
    
    public static Set<CourseEnroll>  getWaitlists(){
        Set<CourseEnroll> courses= new HashSet<CourseEnroll>();
        

        try{
            Connection con= getCon();
            
        
            
            String q = "select * from Course_Waitlist "
                    +";";
                        System.out.println(q);
			PreparedStatement ps=con.prepareStatement(q);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				String courseid=rs.getString("courseid");
                                CourseEnroll course = new CourseEnroll(courseid,null, null);
                                courses.add(course);
                                
                                }
			
			
			con.close();

		}catch(Exception e){return courses;}
            return courses;
    }
    
    public static void addWaitlist(String courseid) throws ClassNotFoundException, SQLException{
      
        
            Connection con= getCon();
          
            String q = "insert into Course_Waitlist "
                    + "(courseid) values "
                    + "('"+courseid+"');";
            Statement stmt = con.createStatement(); 
           
            stmt.execute(q);
			con.close();
                        

		 
 
        
    }
    
    public static void addWaitlist(String courseid, String spots, String date) throws ClassNotFoundException, SQLException{
      
        
            Connection con= getCon();
          
            String q = "insert into Course_Waitlist "
                    + "(courseid, spots, date) values "
                    + "("+
                    "'" +courseid+"'"+ ","
                    +"'"+spots+"'" + ","
                    +"'"+date+"'"
                    +"');";
            Statement stmt = con.createStatement(); 
           
            stmt.execute(q);
			con.close();
                        

		 
 
        
    }
    
}

