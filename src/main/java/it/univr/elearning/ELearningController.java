package it.univr.elearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Controller
public class ELearningController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
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

    // TODO: mapping
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

    // TODO: mapping
    public void setCourseStudent(@RequestParam(name="id") Long idCourse, @RequestParam(name="student") Long idStudent){
        if (courseRepository.existsById(idCourse)){
            Course c = courseRepository.findById(idCourse).get();
            Student student = studentRepository.findById(idStudent).get();
            c.setStudent(student);
            student.setCourse(c);
        }
    }

    @RequestMapping("/")
    public String index(){
        return "grades";
    }


    @RequestMapping("/grades")
    public String grades(/*@PathVariable("courseId") Long id,*/ Model model){
        Course c = new Course();
        //if(courseRepository.findById(id).isPresent()) {
          //  c = courseRepository.findById(id).get();
        //}
        c = initTest();
        List<Student> students = c.getStudents();
        model.addAttribute("students", students);
        return "grades";
    }

    @RequestMapping("/addGrades")
    public String addGrades(@RequestParam(name="grade", required = true) String grade){


        return "redirect:/index";
    }

    @RequestMapping("/noticeBoard")
    public String viewNoticeBoard(){


        return "noticeBoard";
    }





    //TEST
    public Course initTest(){
        Student s1 = new Student("andrea", "rossetti");
        Student s2 = new Student("simone", "baldi");
        studentRepository.save(s1);
        studentRepository.save(s2);
        Course c = new Course("Fondamenti AI", "Farinelli");
        c.setStudent(s1);
        c.setStudent(s2);
        courseRepository.save(c);
        //courseRepository.findByCourseName("Fondamenti AI");
        return c;
    }




}
