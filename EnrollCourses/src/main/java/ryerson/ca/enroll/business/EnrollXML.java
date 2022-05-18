
package ryerson.ca.enroll.business;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import ryerson.ca.enroll.helper.CourseEnroll;

 @XmlRootElement
public class EnrollXML {
    
private ArrayList<CourseEnroll> courses;
           @XmlElementWrapper
           @XmlElement(name="courseEnrolled")
           public List<CourseEnroll>getCourses(){
               return courses;
               
           }
          EnrollXML(){
               
               
           }
           public void setCourse(ArrayList<CourseEnroll> cs){
               courses=cs;
               
           }
           
       }