/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.searchcourse.helper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @instructor student
 */
@XmlRootElement(name = "course")
@XmlAccessorType(XmlAccessType.FIELD)
public class Course {
    
    private String isbn; 
    private String title;
    private String barcode;
    ArrayList<Instructor> instructor ;

    public Course(){
        
    }
    public Course(String isbn, String title, String barcode, Instructor instructor) {
        this.isbn = isbn;
        this.title = title;
        this.barcode = barcode;
        this.instructor= new ArrayList<Instructor>();
        this.instructor.add(new Instructor(instructor.getFirstName(), instructor.getLastName()));
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getBarcode() {
        return barcode;
    }
    public ArrayList<Instructor> getInstuctors() {
        return instructor;
    }
     public void addInstuctor(ArrayList<Instructor> instructors) {
         for(Instructor a:instructors){
        this.instructor.add(new Instructor(a.getFirstName(),a.getLastName()));
         }
    }
 
    
}
