package it.univr.elearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ELearningController {

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course){

        courseRepository.save(course);
        return course;
    }

    @PostMapping("/grades")
    public void addGrades(){

    }

}
