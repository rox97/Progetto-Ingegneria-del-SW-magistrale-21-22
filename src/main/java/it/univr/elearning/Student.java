package it.univr.elearning;

import javax.persistence.*;
import java.util.*;

@Entity
//QUESTION: serve il nome della tabella?
@Table(name = "STUDENT")
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private Long id;
    private String firstName;
    private String lastName;
    private String lastGrade;

    protected Student(){}

    public Student(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long  getId() {return id;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}

    public String getLastGrade() {
        return lastGrade;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setLastGrade(String lastGrade) {
        this.lastGrade = lastGrade;
    }

    @ManyToMany(mappedBy="students",cascade = {CascadeType.ALL})
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

    @OneToMany(mappedBy = "student" ,cascade = {CascadeType.MERGE})
    private List<Booklet> booklet = new ArrayList<>();
    public List<Booklet> getBooklet() {
        return booklet;
    }
    public void setBooklet(Booklet booklet) {
        this.booklet.add(booklet);
    }
}
