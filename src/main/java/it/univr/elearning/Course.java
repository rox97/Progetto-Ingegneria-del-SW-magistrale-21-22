package it.univr.elearning;

import javax.persistence.*;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COURSE")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;
    private String courseName;
    private String coordinatorName;

    protected Course(){}

    public Course(String courseName, String coordinatorName){
        this.courseName = courseName;
        this.coordinatorName = coordinatorName;
    }

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
    @JoinTable(name = "STUDENT_COURSE", joinColumns={@JoinColumn(name="COURSE_ID")})
    @Column(nullable = true)
    private List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }
    public void setStudents(List<Student> students){
        this.students = students;
    }
    public void setStudent(Student student){
        this.students.add(student);
    }






}
