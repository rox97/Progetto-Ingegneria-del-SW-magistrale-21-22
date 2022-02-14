package it.univr.elearning.model;

import java.util.List;

public class StudentForm{
  private List<Student> students;

  public void addStudent(Student student){
      this.students.add(student);
  }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
