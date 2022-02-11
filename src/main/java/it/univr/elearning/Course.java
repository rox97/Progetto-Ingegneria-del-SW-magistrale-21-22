package it.univr.elearning;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COURSE")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    private String courseName;
    private String coordinatorName;
    private String academicYear;

    protected Course() {
    }

    public Course(String courseName, String coordinatorName, String academicYear) {
        this.courseName = courseName;
        this.coordinatorName = coordinatorName;
        this.academicYear = academicYear;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCoordinatorName(String coordinatorName) {
        this.coordinatorName = coordinatorName;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Long getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCoordinatorName() {
        return coordinatorName;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "PROFESSOR_ID")
    private Professor professor;

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "STUDENT_ID")
    private List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setStudent(Student student) {
        this.students.add(student);
    }

    @OneToMany(mappedBy="course",cascade = {CascadeType.MERGE})
    private List<Event> events = new ArrayList<>();
    public List<Event> getEvents() {
        return events;
    }
    public void setCourses(List<Event> events){
        this.events = events;
    }
    public void setCourse(Event event){
        this.events.add(event);
    }

}
