/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.searchcourse.business;

import ryerson.ca.searchcourse.helper.CoursesXML;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import ryerson.ca.searchcourse.helper.Course;
import ryerson.ca.searchcourse.persistence.Course_CRUD;

/**
 *
 * @author student
 */
public class SearchBusiness {
    
    public  CoursesXML getCoursesByQuery(String query){
       Set<Course> courses = Course_CRUD.searchForCourses(query);
       Map<String ,Course> allInstuctorsCourses= new HashMap ();
           System.out.println("&&&&&&&&&&&&&&&&&&&&&&"+ courses.size());
        for(Course course : courses){
            if(allInstuctorsCourses.containsKey(course.getIsbn())){
                allInstuctorsCourses.get(course.getIsbn()).addInstuctor(course.getInstuctors());
            }
            else{
               
                allInstuctorsCourses.put(course.getIsbn(),course );
            }
        }
        System.out.println("**********************"+ allInstuctorsCourses.size());
        CoursesXML bs;
        bs = new CoursesXML();
        bs.setCourse(new ArrayList(allInstuctorsCourses.values()));
        return (bs);
    }
    
      
}
