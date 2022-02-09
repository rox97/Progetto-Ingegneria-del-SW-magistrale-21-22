package it.univr.elearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    //Variabile percorso cartella upload file
    private final String UPLOAD_DIR = "./testUPLOADFILES/";


    @RequestMapping("/login")
    public String login() {
        //REMOVE: se rimuovo la prima istanza di un voto nella repository, non riesco più a salvarne altri
        initTest();

        String examDate = "1970-01-01";
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(examDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Grade b = new Grade("test", "test", "test", date);
        Student s = new Student();
        //b.setStudent(s);
        //s.setGrade(b);
        gradeRepository.save(b);

        return "login";
    }

    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course) {
        courseRepository.save(course);
        return course;
    }

    @GetMapping("/courses/{courseId}")
    public Optional<Course> getCourse(@PathVariable("coursesId") Long id) {

        return courseRepository.findById(id);
    }

    // TODO: valutare se serve
    @PostMapping("/pCourses")
    public Professor addProfessor(@RequestBody Professor professor) {
        professorRepository.save(professor);
        return professor;
    }

    @GetMapping("/pCourses/{professorId}")
    public Optional<Professor> getProfessor(@PathVariable("professorsId") Long id) {

        return professorRepository.findById(id);
    }

    @PostMapping("/student")
    public Student addStudent(@RequestBody Student student) {
        studentRepository.save(student);
        return student;
    }

    @GetMapping("/student/{studentId}")
    public Optional<Student> getStudent(@PathVariable("studentId") Long id) {

        return studentRepository.findById(id);
    }

    // TODO: valutare se serve e nel caso mapping
    public void setCourseStudents(@RequestParam(name = "id") Long id, @RequestParam(name = "students") List<Student> students) {
        Course c = new Course();
        if (courseRepository.existsById(id)) {
            c = courseRepository.findById(id).get();
            c.setStudents(students);
        }

        for (Student s : students) {
            s.setCourse(c);
        }
    }

    // TODO: valutare se serve e nel caso mapping
    public void setCourseStudent(@RequestParam(name = "id") Long idCourse, @RequestParam(name = "student") Long idStudent) {
        if (courseRepository.existsById(idCourse)) {
            Course c = courseRepository.findById(idCourse).get();
            Student student = studentRepository.findById(idStudent).get();
            c.setStudent(student);
            student.setCourse(c);
        }
    }

    @GetMapping("/")
    public String index() {
        return "grades";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }


    @RequestMapping("/grades")
    public String grades(/*@PathVariable("courseId") Long id,*/ Model model) {
        //if(courseRepository.findById(id).isPresent()) {
        //  c = courseRepository.findById(id).get();
        //}

        Optional<Course> c = courseRepository.getCourseByCourseName("Fondamenti AI");
        //FIXME: quando torno sulla pagina non mi visualizza tutti i numeri di matricola
        if (c.isPresent()) {
            Course test = c.get();
            model.addAttribute("courseName", test.getCourseName());
            List<Student> students = test.getStudents();
            StudentForm studentForm = new StudentForm();
            studentForm.setStudents(students);
            model.addAttribute("studentForm", studentForm);
            model.addAttribute("students", students);
            return "grades";
        } else {
            return "notfound";
        }
    }

    @RequestMapping("/addGrades")
    public String addGrades(@ModelAttribute("courseName") String courseName, @ModelAttribute("examDate") String examDate, @ModelAttribute("examType") String examType, @ModelAttribute("studentForm") StudentForm studentForm, Model model) {
        List<Student> students = studentForm.getStudents();
        //TODO: ottimizzare questa parte del try catch. Non penso sia ottimo non gestire l'exception
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(examDate);
        } catch (ParseException ignored) {
        }

        List<Grade> courseBooklet = new ArrayList<>();
        //FIXME: nullPointerException probabilmente generato dal fatto che non inizializza la repository all'avvio.
        //Probabilmente si risolverà con la versione definitiva
        for (Student s : students) {
            if (!Objects.equals(s.getLastGrade(), "")) {
                Grade grade = new Grade();
                grade.setCourseName(courseName);
                grade.setExamDate(date);
                grade.setExamType(examType);
                grade.setGrade(s.getLastGrade());
                s.setLastGrade("");
                grade.setStudent(s);
                s.setGrade(grade);
                for(Grade gR : gradeRepository.findAll()){
                    if (Objects.equals(gR.getCourseName(), courseName) && Objects.equals(gR.getStudent().getStudentId(), s.getStudentId())) {
                        System.out.println("dentro all'if");
                        //bR.setGrade(booklet.getGrade());
                        gradeRepository.delete(gR);
                    }
                }
                gradeRepository.save(grade);
            }
        }

        return "redirect:/home";
    }

    @RequestMapping("/noticeBoard")
    public String viewNoticeBoard(){


        return "noticeBoard";
    }



    @RequestMapping("/booklet")
    public String getBooklet(Model model){
        //FIXME: se cambio il voto e torno nella pagina del libretto ho un NullPointerException
        //TEST
        Student s = studentRepository.getStudentByStudentId("VR1234");
        Iterable<Grade> grades = gradeRepository.findByStudent_StudentId("VR1234");
        System.out.println(s.getFirstName());
        model.addAttribute("firstName", s.getFirstName());
        model.addAttribute("lastName", s.getLastName());
        List<Grade> result = new ArrayList<>();
        for(Grade g : grades){
            System.out.println("Booklets: "+g.getId()+" "+g.getGrade()+" "+g.getExamType()+" "+g.getExamDate()+" "+g.getCourseName()+" "+g.getStudent());
            result.add(g);
        }

        model.addAttribute("booklet",result);


        return "booklet";
    }

    @GetMapping("/uploadFile")
    public String uploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // save the file on the local file system
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return success response
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/";
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

        try {

            Path path = Paths.get("./testUPLOADFILES/");

            //java.nio.file.Files;
            if(!Files.exists(path)){
                Files.createDirectories(path);
                System.out.println("Directory is created!");
            }else{
                System.out.println("Directory already exist,not created!");
            }




        } catch (IOException e) {

            System.err.println("Failed to create directory!" + e.getMessage());

        }
    }




}
