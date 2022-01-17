package it.univr.elearning;

import javax.persistence.*;
import java.util.List;

@Entity
//TODO: serve il nome della tabella?
//@Table(name = "STUDENT")
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private Long id;
    private String firstName;
    private String lastName;
    private List<Course> courses;

    protected Student(){}

    public Student(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long  getId() {return id;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}

    @ManyToMany(mappedBy="students")
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses){
        this.courses = courses;
    }

}
