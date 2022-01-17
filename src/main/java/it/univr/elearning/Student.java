package it.univr.elearning;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//TODO: serve il nome della tabella?
@Table(name = "STUDENT")
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private Long id;
    private String firstName;
    private String lastName;

    protected Student(){}

    public Student(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long  getId() {return id;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    @ManyToMany(mappedBy="students")
    private List<Course> courses = new ArrayList<>();
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses){
        this.courses = courses;
    }

    public void setCourse(Course course){
        this.courses.add(course);
    }

}
