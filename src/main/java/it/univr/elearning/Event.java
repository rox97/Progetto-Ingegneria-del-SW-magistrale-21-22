package it.univr.elearning;

import javax.persistence.*;
import java.net.URI;
import java.util.Date;

@Entity
@Table(name = "EVENT")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    private String eventTitle;
    private String eventDescription;
    private String eventCourse;
    private URI link;
    @Temporal(TemporalType.DATE)
    private Date eventDate;
    //QUESTION: per usare un solo tipo di evento con consegna, posso riciclarlo e renderlo di doppia utilità?
    private boolean isHomework;

    public Event(String eventTitle, String eventDescription, Date eventDate) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
    }

    protected Event() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventCourse() {
        return eventCourse;
    }

    public void setEventCourse(String eventCourse) {
        this.eventCourse = eventCourse;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

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
