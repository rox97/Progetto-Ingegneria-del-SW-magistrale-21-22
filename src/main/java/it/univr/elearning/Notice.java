package it.univr.elearning;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Notice {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private long id;
    private String title;
    private String text;
    private String courseName;
    //QUESTION: la password va bene salvata così?
    private String password;

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
    public void setcourseName(String courseName){ this.title = courseName;}

    public void setPassword(String password) {
        this.password = password;
    }

    public Long  getId() {return id;}
    public String getTitle() {return title; }
    public String getText(){ return text;}
    public String getCourseName(){ return courseName;}

    public String getPassword() {
        return password;
    }

}
