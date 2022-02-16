package it.univr.elearning.repository;

import it.univr.elearning.model.Student;
import org.springframework.data.repository.CrudRepository;


public interface StudentRepository  extends CrudRepository<Student, Long> {

    Student findStudentByStudentId(String studentId);
    Student findStudentByStudentIdAndPassword(String studentId, String password);
    boolean existsByStudentId(String studentId);
    boolean existsByStudentIdAndPassword(String studentId, String password);



}
