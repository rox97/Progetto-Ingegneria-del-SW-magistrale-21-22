package it.univr.elearning;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CourseRepository extends CrudRepository<Course,Long> {

    Optional<Course> findByCourseName(String courseName);
}
