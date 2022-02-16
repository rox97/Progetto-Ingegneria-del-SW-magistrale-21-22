package it.univr.elearning.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PROFESSOR")

public class Professor {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private long id;
    private String professorName;
    private String professorSurname;
    private String userName;
    private String password;

    protected Professor(){}

    public Professor(String professorName, String professorSurname, String userName){
        this.professorName = professorName;
        this.professorSurname = professorSurname;
        this.userName = userName;
    }

    public void setId(Long id){ this.id = id;}
    public void setProfessorName(String professorName){ this.professorName = professorName;}
    public void setProfessorSurname(String professorSurname){ this.professorSurname = professorSurname;}

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long  getId() {return id;}
    public String getProfessorName(){ return professorName;}
    public String getProfessorSurname(){ return professorSurname;}

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @OneToMany(mappedBy="professor",cascade = {CascadeType.ALL})
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
