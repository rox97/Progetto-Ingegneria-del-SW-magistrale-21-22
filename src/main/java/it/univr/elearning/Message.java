package it.univr.elearning;

import javax.persistence.*;

@Entity
@Table(name = "MESSAGE")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    private String title;
    private String text;
    private String courseName;


    protected Message() {
    }

    public Message(String title, String text, String courseName) {
        this.title = title;
        this.text = text;
        this.courseName = courseName;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getCourseName(){ return courseName; }


    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCourseName(String courseName){ this.courseName = courseName; }

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
