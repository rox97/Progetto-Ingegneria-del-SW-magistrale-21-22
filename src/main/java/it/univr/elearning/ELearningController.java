package it.univr.elearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
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

    @RequestMapping("/init")
    public String init() {
        initTest();
        return "redirect:login";
    }


    @RequestMapping("/login")
    public String login() {
        //REMOVE: se rimuovo la prima istanza di un voto nella repository, non riesco più a salvarne altri
        return "login";
    }

    @RequestMapping("/courses")
    public String showCourses(Model model, @RequestParam("userName") String username, @RequestParam("password") String password){
        if(professorRepository.existsByUserName(username)){
            Professor p = professorRepository.findByUserName(username);
            model.addAttribute("courses",p.getCourses());
            return "courses";
        }
        else if(studentRepository.existsByStudentId(username)){
            Student s = studentRepository.findStudentByStudentId(username);
            model.addAttribute("courses",s.getCourses());
            return "courses";
        }
        else
            return "/notfound";

    }


    /*
    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course) {
        courseRepository.save(course);
        return course;
    }
    */

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

        Optional<Course> c = courseRepository.findCourseByCourseName("Fondamenti AI");
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

        Iterable<Notice> notices = noticeRepository.findAll();
        model.addAttribute("notices",notices);

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
        //TEST
        Student s = studentRepository.findStudentByStudentId("VR1234");
        Iterable<Grade> grades = gradeRepository.findByStudent_StudentId("VR1234");
        model.addAttribute("firstName", s.getFirstName());
        model.addAttribute("lastName", s.getLastName());
        model.addAttribute("booklet",grades);
        return "booklet";
    }

    @GetMapping("/uploadFile") //visualizza la pagina html upload
    public String uploadPage(Model model) {
        FileListing fL= new FileListing();
        model.addAttribute("files", fL.getFileStringListing()); //popola la tabella con i file caricati
        return "upload";
    }

    @PostMapping("/upload") //Upload dei file lato docente
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        // controlla che il file non sia vuoto
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/uploadFile";
        }

        // normalizza la path del file
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Salva il file nel file system locale
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ritorna una risposta di successo
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/uploadFile";
    }

    @PostMapping("/delete") // elimina il file selezionato
    public String deleteFile(@RequestParam("file") String fileName) {

        FileListing fL=new FileListing();
        File[]files=fL.getFilePathListing(); //Prendo la lista dei file

        for (File file : files) {
            if(file.getName().equals(fileName)){ //cicla la lista dei file ed elimina quello scelto
                file.delete();
            }
        }

        return "redirect:/uploadFile";
    }






    //TEST
    public void initTest(){
        Student s1 = new Student("andrea", "rossetti","VR1234");
        Student s2 = new Student("simone", "baldi","VR9876");
        studentRepository.save(s1);
        studentRepository.save(s2);
        Professor p1 = new Professor("Alessandro", "Farinelli", "qwerty");
        Course c = new Course("Fondamenti AI", "Farinelli","2021/22");
        c.setStudent(s1);
        c.setStudent(s2);
        c.setProfessor(p1);
        p1.setCourse(c);
        courseRepository.save(c);
        professorRepository.save(p1);

        Course c2 = new Course("Fondamenti di Ingegneria del SW", "Ceccato","2021/22");
        Professor p2 = new Professor("Mariano", "Ceccato", "asdfgh");
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
