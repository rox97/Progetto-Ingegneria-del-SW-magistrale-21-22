package it.univr.elearning;

import javax.persistence.*;

@Entity
public class Course {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String courseName;
    private String coordinatorName;
    //TODO: come usare persistence e le repository
    //Servono per il json e le tabelle del database: non può essere una repository perché non può essere una colonna
    //private StudentRepository studentRepository;

    protected Course(){}

    public Course(String courseName, String coordinatorName){
        this.courseName = courseName;
        this.coordinatorName = coordinatorName;
    }


    public Long  getId() {return id;}

    public String getCourseName(){return courseName;}

    public String getCoordinatorName(){return coordinatorName;}





}
