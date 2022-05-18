
package ryerson.ca.enroll.helper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class CourseEnroll {
    private String courseid; 

    public CourseEnroll(String courseid, String enrollDate, String spots) {
        this.courseid = courseid;
        this.enrollDate = enrollDate;
        this.spots = spots;
    }
    public CourseEnroll() {
        this.courseid = null;
        this.enrollDate = null;
        this.spots = null;
    }
    private String enrollDate;
    private String spots;

    public String getIsbn() {
        return courseid;
    }

    public String getEnrollDate() {
        return enrollDate;
    }

    public String getUsername() {
        return spots;
    }


   
    
}
