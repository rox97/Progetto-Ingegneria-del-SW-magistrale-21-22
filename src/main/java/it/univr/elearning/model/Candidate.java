package it.univr.elearning.model;

import javax.persistence.*;

@Entity

public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    private String name;
    private String surname;
    private String list;
    private int numberVote;

    protected Candidate() {
    }

    public Candidate(String name, String surname, String list, int numberVote) {
        this.name = name;
        this.surname = surname;
        this.list = list;
        this.numberVote = numberVote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public int getNumberVote() {
        return numberVote;
    }

    public void setNumberVote(int numberVote) {
        this.numberVote = numberVote;
    }

    public void addVote() {
        this.numberVote++;
    }
}
