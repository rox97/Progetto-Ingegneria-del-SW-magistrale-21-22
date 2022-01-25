package it.univr.elearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ELearningController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ProfessorRepository professorRepository;

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course){
        courseRepository.save(course);
        return course;
    }

    @GetMapping("/courses/{courseId}")
    public Optional<Course> getCourse(@PathVariable("coursesId") Long id){

        return courseRepository.findById(id);
    }

    // TODO: valutare se serve
    @PostMapping("/pCourses")
    public Professor addProfessor(@RequestBody Professor professor){
        professorRepository.save(professor);
        return professor;
    }

    @GetMapping("/pCourses/{professorId}")
    public Optional<Professor> getProfessor(@PathVariable("professorsId") Long id){

        return professorRepository.findById(id);
    }

    @PostMapping("/student")
    public Student addStudent(@RequestBody Student student){
        studentRepository.save(student);
        return student;
    }

    @GetMapping("/student/{studentId}")
    public Optional<Student> getStudent(@PathVariable("studentId") Long id){

        return studentRepository.findById(id);
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
        StudentForm studentForm = new StudentForm();
        studentForm.setStudents(students);
        model.addAttribute("studentForm", studentForm);
        model.addAttribute("students", students);
        return "grades";
    }

    @RequestMapping("/addGrades")
    public String addGrades(@ModelAttribute("studentForm") StudentForm studentForm, Model model){
        List<Student> students = studentForm.getStudents();
        for(Student s : students){
            System.out.println(s.getId() + " " + s.getFirstName() + " " + s.getLastName() + " " + s.getLastGrade());
        }
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
