package it.univr.elearning.repository;

import it.univr.elearning.model.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface CourseRepository extends CrudRepository<Course,Long> {

    Optional<Course> findCourseByCourseName(String courseName);
    List<Course> findByCoordinatorName(String coordinatorName);
    List<Course> findByProfessor_UserName(String username);
}
