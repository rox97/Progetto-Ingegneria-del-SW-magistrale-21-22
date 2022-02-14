package it.univr.elearning.model;

import javax.persistence.*;

@Entity

public class Notice {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private long id;
    private String title;
    private String text;
    private String courseName;

    protected Notice(){}

    public Notice(String title, String text, String courseName){
        this.title = title;
        this.text = text;
        this.courseName = courseName;
    }

    public void setId(Long id){ this.id = id;}
    public void setTitle(String title){ this.title = title;}
    public void setText(String text) {
        this.text = text;
    }
    public void setCourseName(String courseName){ this.title = courseName;}


    public Long  getId() {return id;}
    public String getTitle() {return title; }
    public String getText(){ return text;}
    public String getCourseName(){ return courseName;}


}
