package it.univr.elearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ELearningController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private BookletRepository bookletRepository;

    @RequestMapping("/login")
    public String login(){
        //REMOVE: se rimuovo la prima istanza di un voto nella repository, non riesco più a salvarne altri
        initTest();
        String examDate = "1970-01-01";
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(examDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Booklet b = new Booklet("test","test","test",date);
        bookletRepository.save(b);

        return "login";
    }

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

    // TODO: valutare se serve e nel caso mapping
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

    // TODO: valutare se serve e nel caso mapping
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

    @RequestMapping("/home")
    public String home(){return "home";}


    @RequestMapping("/grades")
    public String grades(/*@PathVariable("courseId") Long id,*/ Model model){
        //if(courseRepository.findById(id).isPresent()) {
          //  c = courseRepository.findById(id).get();
        //}
        //QUESTION: Optional?
        Course c = courseRepository.getCourseByCourseName("Fondamenti AI");
        List<Student> students = c.getStudents();
        StudentForm studentForm = new StudentForm();
        studentForm.setStudents(students);
        model.addAttribute("studentForm", studentForm);
        model.addAttribute("students", students);
        model.addAttribute("courseName", c.getCourseName());
        return "grades";
    }

    @RequestMapping("/addGrades")
    public String addGrades(@ModelAttribute("courseName") String courseName,@ModelAttribute("examDate") String examDate, @ModelAttribute("examType") String examType,@ModelAttribute("studentForm") StudentForm studentForm, Model model){
        List<Student> students = studentForm.getStudents();
        System.out.println(examDate);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(examDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(courseName);
        System.out.println(date + " " + examType);
        List<Booklet> courseBooklets = new ArrayList<>();
        for(Student s : students){
            System.out.println(s.getId() + " " + s.getFirstName() + " " + s.getLastName() + " " + s.getLastGrade());
            Booklet booklet = new Booklet();
            booklet.setCourseName(courseName);
            booklet.setExamDate(date);
            booklet.setExamType(examType);
            booklet.setGrade(s.getLastGrade());
            booklet.setStudent(s);
            if (!Objects.equals(s.getLastGrade(), "")){
                s.setLastGrade("");
                for(Booklet bR : bookletRepository.findAll()){
                    if (Objects.equals(bR.getCourseName(), booklet.getCourseName()) && Objects.equals(bR.getStudent().getFirstName(), booklet.getStudent().getFirstName())) {
                        //bR.setGrade(booklet.getGrade());
                        bookletRepository.delete(bR);
                        bookletRepository.save(booklet);
                    } else {
                        bookletRepository.save(booklet);

                    }
                }
            }
        }

        Iterable<Booklet> booklets = bookletRepository.findAll();
        for(Booklet b : booklets){
            System.out.println("Final Booklets: "+b.getId()+" "+b.getGrade()+" "+b.getExamType()+" "+b.getExamDate()+" "+b.getCourseName()+" "+b.getStudent());
        }
        return "redirect:/home";
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
