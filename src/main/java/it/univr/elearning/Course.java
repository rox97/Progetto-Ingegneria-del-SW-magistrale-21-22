package it.univr.elearning;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String courseName;
    private String coordinatorName;
    private List<Student> students;

    protected Course(){}

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public void setCoordinatorName(String coordinatorName){
        this.coordinatorName = coordinatorName;
    }


    public Long  getId() {return id;}

    public String getCourseName(){return courseName;}

    public String getCoordinatorName(){return coordinatorName;}

    //TODO: come funzionano i join? dobbiamo specificare i nomi? i warning sono importanti o si possono ignorare?
    @ManyToMany
    @JoinColumn(name = "ID")
    @Column(nullable = true)
    public List<Student> getStudents() {
        return students;
    }
    public void setStudents(List<Student> students){
        this.students = students;
    }





}
