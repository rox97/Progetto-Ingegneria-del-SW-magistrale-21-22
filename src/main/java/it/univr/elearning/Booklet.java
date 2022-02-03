package it.univr.elearning;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BOOKLET")
public class Booklet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    private String courseName;
    private String examType;
    private String grade;
    @Temporal(TemporalType.DATE)
    private Date examDate;


    protected Booklet() {
    }

    public Booklet(String courseName, String grade) {
        this.courseName = courseName;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public Object getGrade() {
        return grade;
    }

    public Date getExamDate() {
        return examDate;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }


    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "STUDENT_ID")
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


}
