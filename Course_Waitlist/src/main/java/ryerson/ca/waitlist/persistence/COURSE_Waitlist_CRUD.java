/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.waitlist.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import ryerson.ca.waitlist.helper.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author student
 */
public class COURSE_Waitlist_CRUD {
    
    
    private static Connection getCon(){
    Connection con=null;
     try{
         
       Class.forName ("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Waitlist_CRS?autoReconnect=true&useSSL=false", "root", "student");
		System.out.println ("Connection Established.");
     }
     catch(Exception e){ System.out.println(e);}
     return con;
    }
    
    public static boolean addWaitlist(String courseid, String spots){
      
        try{
            Connection con= getCon();
           
            /*String q = "insert into WAITLIST "
                    + "(courseid, spots) values "
                    + "('"+courseid+"', "
                    +"'"+spots+"');";
            */
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             LocalDate date = LocalDate.now();
            String q = "insert into WAITLIST "
                    + "(courseid, spots, startdate) values "
                    + "('"+courseid+"', "
                    +"'"+spots+"', "
                    +"'"+date.format(formatter)+"');";
            
            
            Statement stmt = con.createStatement();   
            System.out.println(q);
            stmt.execute(q);
            
			con.close();
                        return true;

		}catch(Exception e){System.out.println(e);
                return false;
                }
    }
    
     public static Waitlist getWaitlist(String courseid){
        Waitlist course =null;
        try{
            Connection con= getCon();
            
            String q = "select * from WAITLIST "
                    + " WHERE "
                    
                    + "courseid = '"+courseid+"';";
            System.out.println(q);

			PreparedStatement ps=con.prepareStatement(q);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){

		
                                String spots=rs.getString("spots").toString();
                                String date=rs.getDate("startdate").toString();
                                course = new Waitlist(courseid,date,spots);
                             
                                }
			
			con.close();

		}catch(Exception e){System.out.println(e);}
            
    
        return course;
        
    }

   public static boolean addWaitlist(String courseid, String spots, String date) {
     return( addWaitlist(courseid, spots));
     }
    }
