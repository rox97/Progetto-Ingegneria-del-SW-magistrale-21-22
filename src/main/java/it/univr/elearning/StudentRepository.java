package it.univr.elearning;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository  extends CrudRepository<Student, Long> {

    Student getStudentByStudentId(String studentId);

}
