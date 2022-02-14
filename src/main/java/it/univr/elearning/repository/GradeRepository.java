package it.univr.elearning.repository;

import it.univr.elearning.model.Grade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GradeRepository  extends CrudRepository<Grade, Long> {
    @Transactional
    void deleteGradeById(Long id);
    boolean existsGradeByCourseNameAndStudent_StudentId(String courseName, String student_studentId);
    Grade findGradeByStudent_StudentId(String student_studentId);
    Grade findGradeByCourseNameAndStudent_StudentId(String courseName, String student_studentId);
    List<Grade> findByStudent_StudentId(String student_studentId);
}
