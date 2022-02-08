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
    private String studentId;
    //QUESTION: la password va bene salvata così?
    private String password;

    protected Student(){}

    public Student(String firstName, String lastName, String studentId){
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
    }

    public Long  getId() {return id;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}

    public String getLastGrade() {
        return lastGrade;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getPassword() {
        return password;
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

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(mappedBy="students",cascade = {CascadeType.ALL}, targetEntity = Course.class)
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

    @OneToMany(mappedBy = "student" ,cascade = {CascadeType.MERGE}, targetEntity = Grade.class, fetch = FetchType.EAGER)
    private List<Grade> grades = new ArrayList<>();
    //public List<Grade> getBooklet() {
    //    return grades;
    //}
    //public Grade getGrade(){return grade;}
    public void setGrade(Grade grade) {
        this.grades.add(grade);
    }
}
