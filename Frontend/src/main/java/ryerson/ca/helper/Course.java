/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.helper;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @instructor student
 */
    @XmlRootElement(name = "course")
@XmlAccessorType(XmlAccessType.FIELD)
public class Course {

 @XmlTransient
    private boolean tobeWaitlisted;

    public boolean isTobeWaitlisted() {
        return tobeWaitlisted;
    }

    public void setTobeWaitlisted(boolean tobeWaitlisted) {
        this.tobeWaitlisted = tobeWaitlisted;
    }
    private String courseid; 
    private String coursename;
    private String spots;
    ArrayList<Instructor> instructor ;

    public Course(){
        
    }
    public Course(String courseid, String coursename, String spots, Instructor instructor) {
        this.courseid = courseid;
        this.coursename = coursename;
        this.spots = spots;
        this.instructor= new ArrayList<Instructor>();
        this.instructor.add(new Instructor(instructor.getFirstName(), instructor.getLastName()));
    }

    public String getCourseid() {
        return courseid;
    }

    public String getCoursename() {
        return coursename;
    }

    public String getSpots() {
        return spots;
    }
    public ArrayList<Instructor> getAuthors() {
        return instructor;
    }
     public void addAuthor(ArrayList<Instructor> instructors) {
         for(Instructor a:instructors){
        this.instructor.add(new Instructor(a.getFirstName(),a.getLastName()));
         }
    }
}
