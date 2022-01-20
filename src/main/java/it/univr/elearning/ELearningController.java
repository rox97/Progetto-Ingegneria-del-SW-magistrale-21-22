package it.univr.elearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ELearningController {

    @Autowired
    private CourseRepository courseRepository;
    private StudentRepository studentRepository;

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course){
        courseRepository.save(course);
        return course;
    }

    @PostMapping("/student")
    public Student addStudent(@RequestBody Student student){
        studentRepository.save(student);
        return student;
    }

    public void setCourseStudents(@RequestParam(name="id") Long id, @RequestParam(name="students") List<Student> students){
        Course c = new Course();
        if (courseRepository.existsById(id)){
            c = courseRepository.findById(id).get();
            c.setStudents(students);
        }

        for(Student s : students){
            s.setCourse(c);
        }
    }

    public void setCourseStudent(@RequestParam(name="id") Long idCourse, @RequestParam(name="student") Long idStudent){
        if (courseRepository.existsById(idCourse)){
            Course c = courseRepository.findById(idCourse).get();
            Student student = studentRepository.findById(idStudent).get();
            c.setStudent(student);
            student.setCourse(c);
        }
    }

    @PostMapping("/grades")
    public void addGrades(@RequestParam(name = "id") Long id){

    }

    public void findGradesByStudent(@RequestParam(name = "id") Long id){
        Student student = studentRepository.findById(id).get();

        Booklet booklet = student.getBooklet();

    }



}
