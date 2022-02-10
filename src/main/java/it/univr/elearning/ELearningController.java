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
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private NoticeRepository noticeRepository;

    //Variabile percorso cartella upload file
    private final String UPLOAD_DIR = "./testUPLOADFILES/";
    private String username = "";
    private String studentId = "";

    @RequestMapping("/init")
    public String init() {
        initTest();
        return "redirect:login";
    }


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/init";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/courses")
    public String showCourses(@RequestParam("userName") String username, @RequestParam("password") String password, Model model){
        this.username = "";
        if(professorRepository.existsByUserNameAndPassword(username,password)){
            Professor p = professorRepository.findByUserNameAndPassword(username,password);
            model.addAttribute("courses",p.getCourses());
            this.username = username;
            return "pCourses";
        }
        else if(studentRepository.existsByStudentIdAndPassword(username,password)){
            Student s = studentRepository.findStudentByStudentIdAndPassword(username,password);
            //model.addAttribute("student", s);
            model.addAttribute("courses",s.getCourses());
            this.studentId = username;
            return "sCourses";
        }
        else
            return "/loginError";

    }

    @RequestMapping("/retCourses")
    public String returnToCourses(Model model){
        if(professorRepository.existsByUserName(username)){
            Professor p = professorRepository.findByUserName(username);
            model.addAttribute("courses",p.getCourses());
            return "/pCourses";
        }
        else if(studentRepository.existsByStudentId(studentId)){
            Student s = studentRepository.findStudentByStudentId(studentId);
            //model.addAttribute("student", s);
            model.addAttribute("courses",s.getCourses());
            return "/sCourses";
        }
        else
            return "/notfound";
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

    @RequestMapping("/calendar")
    public String showCalendar(Model model){
        Student student = studentRepository.findStudentByStudentId(studentId);
        List<Course> courses = student.getCourses();
        List<Event> events = new ArrayList<>();
        for(Course c : courses){
            events.addAll(c.getEvents());
        }
        model.addAttribute("events",events);
        model.addAttribute("student", student);
    return "/calendar";
    }

    public void addEvent(){

    }

    public void showEvent(){

    }


    @RequestMapping("/grades")
    public String grades(/*@PathVariable("courseId") Long id,*/ Model model) {
        //TEST
        Optional<Course> c = courseRepository.findCourseByCourseName("Fondamenti AI");
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
    public String addGrades(@ModelAttribute("courseName") String courseName, @ModelAttribute("examDate") String examDate, @ModelAttribute("examType") String examType, @ModelAttribute("studentForm") StudentForm studentForm) {
        List<Student> students = studentForm.getStudents();
        //TODO: ottimizzare questa parte del try catch. Non penso sia ottimo non gestire l'exception
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(examDate);
        } catch (ParseException ignored) {
        }
        //FIXME: nullPointerException ogni tanto, probabilmente generato dal fatto che non inizializza la repository all'avvio.
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
                        gradeRepository.delete(gR);
                    }
                }
                gradeRepository.save(grade);
            }
        }

        return "redirect:/home";
    }

    @RequestMapping("/noticeBoard")
    public String notice(Model model){
        Student s = studentRepository.findStudentByStudentId(studentId);
        Iterable<Notice> notices = noticeRepository.findAll();
        model.addAttribute("notices",notices);
        //model.addAttribute("student", s);

        return "noticeBoard";

    }

    @RequestMapping("/createNotice")
    public String input(){
        return "createNotice";
    }

    @RequestMapping("/inputNotice")
    public String createNewNotice(@RequestParam(name="title", required=true) String title,
                                  @RequestParam(name="text", required=true) String text,
                                  @RequestParam(name="courseName", required=true) String courseName){
        noticeRepository.save(new Notice(title,text,courseName));
        return "redirect:/noticeBoard";
    }



    @RequestMapping("/booklet")
    public String getBooklet(Model model){
        Iterable<Grade> grades = gradeRepository.findByStudent_StudentId(studentId);
        model.addAttribute("booklet",grades);
        // Serve solo a visualizzare il nome. Nel caso non importasse si può togliere
        Student s = studentRepository.findStudentByStudentId(studentId);
        model.addAttribute("student", s);
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
        s1.setPassword("andrea");
        s2.setPassword("simone");
        studentRepository.save(s1);
        studentRepository.save(s2);
        Professor p1 = new Professor("Alessandro", "Farinelli", "qwerty");
        p1.setPassword("alessandro");
        Course c = new Course("Fondamenti AI", "Farinelli","2021/22");
        c.setStudent(s1);
        c.setStudent(s2);
        c.setProfessor(p1);
        p1.setCourse(c);
        courseRepository.save(c);
        professorRepository.save(p1);

        Course c2 = new Course("Fondamenti di Ingegneria del SW", "Ceccato","2021/22");
        Professor p2 = new Professor("Mariano", "Ceccato", "asdfgh");
        p2.setPassword("mariano");
        c2.setStudent(s1);
        c2.setProfessor(p2);
        p2.setCourse(c2);
        courseRepository.save(c2);
        professorRepository.save(p2);


        //REMOVE: se rimuovo la prima istanza di un voto nella repository, non riesco più a salvarne altri
        String examDate = "1970-01-01";
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(examDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Grade g = new Grade("test", "test", "test", date);
        Student s = new Student();
        gradeRepository.save(g);

        //inizializzazione avvisi
        Notice a = new Notice ("titolo","testo","Fondamenti AI");
        Notice b = new Notice ("gianni","mefisto","Fondamenti AI");
        noticeRepository.save(a);
        noticeRepository.save(b);

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
