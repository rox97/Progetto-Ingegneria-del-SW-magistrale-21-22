package it.univr.elearning;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BOOKLET")
public class Booklet {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private Long id;
    private String examName;
    private String grade;
    @Temporal(TemporalType.DATE)
    private Date examDate;


    protected Booklet(){}

    public Booklet(String examName, String grade){
        this.examName = examName;
        this.grade =grade;
    }

    public Long getId(){  return id;}
    public String getExamName(){ return examName;}
    public Object getGrade(){ return grade;}
    public Date getExamDate() {return examDate;}

    public void setExamName(String examName){ this.examName = examName;}
    public void setGrade(String grade){ this.grade = grade;}
    public void setExamDate(Date examDate) {this.examDate = examDate;}


    private Student student;

    @OneToOne(mappedBy="booklet")
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }

}
