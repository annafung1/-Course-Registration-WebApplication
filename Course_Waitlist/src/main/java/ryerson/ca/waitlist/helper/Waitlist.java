/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.waitlist.helper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author student
 */
@XmlRootElement(name = "course")// will use class name default if not spec/ name part remoevd
@XmlAccessorType(XmlAccessType.FIELD)
public class Waitlist {
    
    private String courseid, spots;
     private String date;
    
      public Waitlist(String courseid,String date, String spots){
   // public Waitlist(String courseid, String spots){
        this.courseid = courseid;
        this.spots = spots;
        this.date=date;
    }
    
    public Waitlist(){
        this.courseid = "";
        this.spots = "";
       this.date= "";
    }
    
    public String getCourseID() {
        return courseid;
    }
    

    public String getSpots(){
        return spots;
    }
    
    public String getDate(){
       return date;
    }
    
}
