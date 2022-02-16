package it.univr.elearning.model;

import javax.persistence.*;

@Entity
@Table(name = "POLL")
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    private String pollName;
    private String coursePollName;
    private boolean isMandatory = false;

    public Poll() {
    }

    public Poll(String pollName){
        this.pollName=pollName;

    }

    public Long getId(){ return id; }

    public String getPollName() { return pollName; }

    public String getCoursePollName() {
        return coursePollName;
    }

    public void setId(Long id) { this.id = id; }

    public void setPollName(String pollName) { this.pollName = pollName; }

    public void setCoursePollName(String coursePollName) {
        this.coursePollName = coursePollName;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
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
