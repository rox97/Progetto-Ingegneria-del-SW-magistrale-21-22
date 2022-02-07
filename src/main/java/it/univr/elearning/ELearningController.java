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
    private GradeRepository gradeRepository;

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
        Grade b = new Grade("test","test","test",date);
        gradeRepository.save(b);

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
        Optional<Course> c = courseRepository.getCourseByCourseName("Fondamenti AI");
        if(c.isPresent()) {
            Course test = c.get();
            List<Student> students = test.getStudents();
            StudentForm studentForm = new StudentForm();
            studentForm.setStudents(students);
            model.addAttribute("studentForm", studentForm);
            model.addAttribute("students", students);
            model.addAttribute("courseName", test.getCourseName());
            return "grades";
        }
        else{
            return "notfound";
        }
    }

    @RequestMapping("/addGrades")
    public String addGrades(@ModelAttribute("courseName") String courseName,@ModelAttribute("examDate") String examDate, @ModelAttribute("examType") String examType,@ModelAttribute("studentForm") StudentForm studentForm, Model model){
        List<Student> students = studentForm.getStudents();
        //TODO: ottimizzare questa parte del try catch. Non penso sia ottimo non gestire l'exception
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(examDate);
        } catch (ParseException ignored){}

        List<Grade> courseBooklets = new ArrayList<>();
        //FIXME: nullPointerException probabilmente generato dal fatto che non inizializza la repository all'avvio.
        //Probabilmente si risolverà con la versione definitiva
        for(Student s : students){
            Grade grade = new Grade();
            grade.setCourseName(courseName);
            grade.setExamDate(date);
            grade.setExamType(examType);
            grade.setGrade(s.getLastGrade());
            grade.setStudent(s);
            if (!Objects.equals(s.getLastGrade(), "")){
                s.setLastGrade("");
                for(Grade gR : gradeRepository.findAll()){
                    if (Objects.equals(gR.getCourseName(), grade.getCourseName()) && Objects.equals(gR.getStudent().getFirstName(), grade.getStudent().getFirstName())) {
                        //bR.setGrade(booklet.getGrade());
                        gradeRepository.delete(gR);
                    }
                    gradeRepository.save(grade);
                }
            }
        }
/*
        Iterable<Booklet> booklets = bookletRepository.findAll();
        for(Booklet b : booklets){
            System.out.println("Final Booklets: "+b.getId()+" "+b.getGrade()+" "+b.getExamType()+" "+b.getExamDate()+" "+b.getCourseName()+" "+b.getStudent());
        }

 */
        return "redirect:/home";
    }

    @RequestMapping("/noticeBoard")
    public String viewNoticeBoard(){


        return "noticeBoard";
    }



    @RequestMapping("/booklet")
    public String getBooklet(Model model){
        //TEST
        Student s = studentRepository.getStudentByStudentId("VR1234");
        System.out.println(s.getFirstName());
        model.addAttribute("firstName", s.getFirstName());
        model.addAttribute("lastName", s.getLastName());
        Iterable<Grade> grades = gradeRepository.findAll();
        for(Grade g : grades){
            if(Objects.equals(g.getStudent().getStudentId(), s.getStudentId())){
                model.addAttribute("booklet",g);
            }
        }

        return "booklet";
    }

    //TEST
    public void initTest(){
        Student s1 = new Student("andrea", "rossetti","VR1234");
        Student s2 = new Student("simone", "baldi","VR9876");
        studentRepository.save(s1);
        studentRepository.save(s2);
        Course c = new Course("Fondamenti AI", "Farinelli","2021/22");
        c.setStudent(s1);
        c.setStudent(s2);
        courseRepository.save(c);
    }




}
