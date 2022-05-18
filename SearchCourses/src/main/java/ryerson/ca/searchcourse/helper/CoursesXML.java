/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.searchcourse.helper;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import ryerson.ca.searchcourse.helper.Course;

 @XmlRootElement(name = "courses")
@XmlAccessorType (XmlAccessType.FIELD)
       public class CoursesXML{
     @XmlElement(name="course")
           private ArrayList<Course> courses;
           
           
           public List<Course>getCourses(){
               return courses;
               
           }
          public  CoursesXML(){
               
               
           }
           public void setCourse(ArrayList<Course> bs){
               courses=bs;
               
           }
           
       }