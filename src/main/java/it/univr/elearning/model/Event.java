package it.univr.elearning.model;

import javax.persistence.*;
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
    private String link;
    @Temporal(TemporalType.DATE)
    private Date eventDate;
    private boolean isHomework = false;

    public Event(String eventTitle, String eventDescription, String eventCourse , Date eventDate) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventCourse = eventCourse;
        this.eventDate = eventDate;
    }

    public Event() {

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public boolean isHomework() {
        return isHomework;
    }

    public void setHomework(boolean homework) {
        isHomework = homework;
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
