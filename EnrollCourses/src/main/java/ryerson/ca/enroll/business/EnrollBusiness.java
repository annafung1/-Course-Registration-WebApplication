
package ryerson.ca.enroll.business;

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
import org.apache.commons.codec.binary.Base64;
import ryerson.ca.enroll.helper.CourseEnroll;
import ryerson.ca.enroll.persistence.COURSE_Waitlist_CRUD;
import ryerson.ca.enroll.persistence.User_Course_Enroll_CRUD;


public class EnrollBusiness {
    
     public  EnrollXML getCoursesByQuery(String username){
       Set<CourseEnroll> courses = User_Course_Enroll_CRUD.getEnrolledCourses(username);
       
       
        EnrollXML cs;
        cs = new EnrollXML();
        cs.setCourse(new ArrayList(courses));
        return (cs);
    }
    
    
    
    public  EnrollXML getWaitlists(){
        Set<CourseEnroll> holds = COURSE_Waitlist_CRUD.getWaitlists();
       
       EnrollXML cs;
        cs = new EnrollXML();
        cs.setCourse(new ArrayList(holds));
        return (cs);
    }
    
    
    
    
    
}
