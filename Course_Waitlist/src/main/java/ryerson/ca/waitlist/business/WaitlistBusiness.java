
package ryerson.ca.waitlist.business;

import ryerson.ca.waitlist.helper.*;
import ryerson.ca.waitlist.persistence.*;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WaitlistBusiness {
    
    public Waitlist getCourse(String courseid) {
        Waitlist cs = COURSE_Waitlist_CRUD.getWaitlist(courseid);

        return (cs);
    }

/*public boolean wait(String courseid, String spots) {
        return (Waitlist_CRUD.addWait(courseid, spots));
    }
*/
       public boolean waitlist(String courseid, String spots) throws ClassNotFoundException, SQLException, ServerAddressNotSuppliedException, IOException, InterruptedException {
        boolean success = false;
        
            success = COURSE_Waitlist_CRUD.addWaitlist(courseid, spots);
            if(success){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             LocalDate date = LocalDate.now();
        
            LocalDate exDate = date.plusDays(14);

            Messaging.sendmessage("WAITLIST:"+courseid+":"+spots+":"+exDate.format(formatter));
            }
            
        
        return success;
    }


public boolean Waitadv(String courseid, String coursename, String spots) {
       
        return (COURSE_Waitlist_CRUD.addWaitlist(courseid, coursename, spots));
    }
}
