/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.waitlist.endpoint;

import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import ryerson.ca.waitlist.business.*;
import ryerson.ca.waitlist.helper.*;


import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;

/**
 *
 * @author student
 */
 @Path("waitlist") //main path
public class WaitlistResource {
   
    @Context
    private UriInfo context;
    
    public WaitlistResource(){
        
    }
    
    @GET
    @Path("isWaitlist/{courseid}") //branch path 1 (depending on what user inputs/how user interacts with sys
    @Produces(MediaType.APPLICATION_XML)
    public String isWaitlist(@PathParam("courseid") String courseid){ //grabs these user inputs from GUI/html page
           WaitlistBusiness waitlist = new WaitlistBusiness();
        Waitlist course = waitlist.getCourse(courseid); 
        if (course == null) {
            return("null");
        }
        JAXBContext jaxbContext;
       try {
            jaxbContext = JAXBContext.newInstance(Waitlist.class);
            
            //marshall creates a XML object from java object! 
            //we want to return a to string representation of our converted xml object using to string and string writer

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(course, sw);

            return (sw.toString());

        } catch (JAXBException ex) {
            Logger.getLogger(WaitlistResource.class.getName()).log(Level.SEVERE, null, ex);
            return ("Error");
       }
    }

    
    
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("update")  //branch path 2 (depending on what user inputs/how user interacts with sys
    public String updateWaitlist(@FormParam("courseid") String courseid, @FormParam("spots") String spots){ //grab from userinput via html page 
  
        WaitlistBusiness waitlist = new WaitlistBusiness();
        
        boolean cs; 
       try {
            cs = waitlist.waitlist (courseid, spots);
            return ("Inserted");
        } catch (ClassNotFoundException | SQLException | ServerAddressNotSuppliedException | IOException | InterruptedException ex ) {
            Logger.getLogger(WaitlistResource.class.getName()).log(Level.SEVERE, null, ex);
            return (ex.getMessage());
        }
    }
}
