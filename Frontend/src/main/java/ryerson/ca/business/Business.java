/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.business;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.IOUtils;
 
import ryerson.ca.helper.Course;
import ryerson.ca.helper.CoursesXML;


public class Business {

    public static boolean isAuthenticated(String username, String passwrod) {
        return true;
    }

    public static CoursesXML getServices(String query, String token) throws IOException {

        Client searchclient = ClientBuilder.newClient();
        
         String searchService= System.getenv("searchService");
         String waitlistService= System.getenv("waitlistService");
        
        WebTarget searchwebTarget
                //= searchclient.target("http://localhost:8080/SearchCourses/webresources/search");
                      = searchclient.target("http://"+searchService+"/SearchCourse/webresources/search");
        InputStream is
                = searchwebTarget.path(query).request(MediaType.APPLICATION_XML).get(InputStream.class);
        String xml = IOUtils.toString(is, "utf-8");
        CoursesXML courses = coursexmltoObjects(xml);
        if (token != null) {
            Client holdclient = ClientBuilder.newClient();
            WebTarget holdwebTarget
                  //  = holdclient.target("http://localhost:8080/Lab4_V4/webresources/wait/isWaitlist/");
                      = holdclient.target("http://"+waitlistService+"/WaitlistCourse/webresources/waitlist/isOnWaitlist");
            for (Course course : courses.getCourses()) {

                InputStream holddata
                        = holdwebTarget.path(course.getCourseid()).queryParam("token", token).
                                request(MediaType.APPLICATION_XML).get(InputStream.class);
                try{
                    Course a=courseholdxmltoObjects(IOUtils.toString(holddata, "utf-8"));
                    if(a!=null)
                        course.setTobeWaitlisted(true);
                    else
                        course.setTobeWaitlisted(false);
                }
                catch(Exception e){
                    course.setTobeWaitlisted(false);
                }
                
                
            }
        }

        return (courses);

    }

    private static CoursesXML coursexmltoObjects(String xml) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(CoursesXML.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            CoursesXML courses = (CoursesXML) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return courses;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Course courseholdxmltoObjects(String xml) {
        if(xml.isEmpty())
            return null;
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Course.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Course course = (Course) jaxbUnmarshaller.unmarshal(new StringReader(xml));
            return course;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

}
