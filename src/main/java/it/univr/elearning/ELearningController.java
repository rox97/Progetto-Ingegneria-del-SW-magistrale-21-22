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

import static java.lang.Thread.sleep;

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
    @Autowired
    private CandidateRepository candidateRepository;

    //Variabile percorso cartella upload file
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
        this.studentId = "";
        if(professorRepository.existsByUserNameAndPassword(username,password)){
            Professor p = professorRepository.findByUserNameAndPassword(username,password);
            model.addAttribute("courses",p.getCourses());
            this.username = username;
            return "pCourses";
        }
        else if(studentRepository.existsByStudentIdAndPassword(username,password)){
            Student s = studentRepository.findStudentByStudentIdAndPassword(username,password);
            model.addAttribute("courses",s.getCourses());
            this.studentId = username;
            return "sCourses";
        }
        else if(username.equals("elezioni") && password.equals("elezioni")){
            return "voteManager";
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
            model.addAttribute("courses",s.getCourses());
            return "/sCourses";
        }
        else
            return "/notfound";
    }

    @RequestMapping("/retCourse")
    public String returnToCourse(@RequestParam("courseId") Long courseId, Model model){
        Optional<Course> c = courseRepository.findById(courseId);
        if(c.isPresent()){
            model.addAttribute(c.get());
            return "/pCourse";
        }
        else
            return "/notfound";
    }

    @RequestMapping("/showCourse")
    public String showCourse(@RequestParam("courseId") Long courseId, Model model){
        Optional<Course> oCourse = courseRepository.findById(courseId);
        if(oCourse.isPresent()){
            Course c = oCourse.get();
            model.addAttribute("course",c);
            if(!Objects.equals(studentId, ""))
                return "sCourse";
            else
                return "pCourse";
        }
        else
            return "/notfound";

    }

    @RequestMapping("/sCalendar")
    public String showSCalendar(Model model){
        Student student = studentRepository.findStudentByStudentId(studentId);
        List<Course> courses = student.getCourses();
        List<Event> events = new ArrayList<>();
        for(Course c : courses){
            events.addAll(c.getEvents());
        }
        model.addAttribute("events",events);
        model.addAttribute("student", student);
    return "sCalendar";
    }

    @RequestMapping("/pCalendar")
    public String showPCalendar(Model model){
        Professor professor = professorRepository.findByUserName(username);
        List<Course> courses = professor.getCourses();
        List<Event> events = new ArrayList<>();
        for(Course c : courses){
            events.addAll(c.getEvents());
        }
        model.addAttribute("events",events);
        model.addAttribute("professor", professor);
        return "pCalendar";
    }

    @RequestMapping("/addEvent")
    public String addEvent(@RequestParam("courseId") Long courseId, Model model){
            model.addAttribute("courseId", courseId);
            return "/addEvent";
    }

    @RequestMapping("/newEvent")
    public String newEvent(@RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam(name="link") String link,
                           @RequestParam("date") String eventDate,
                           @RequestParam("courseId") Long courseId,
                           @RequestParam("homework") boolean homework,
                           Model model){

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(eventDate);
        } catch (ParseException e) {
            System.out.println("DATE PARSING ERROR");
        }
        Optional<Course> oCourse = courseRepository.findById(courseId);
        if(oCourse.isPresent()) {
            Course c = oCourse.get();
            Event event = new Event(title, description, date);
            event.setEventCourse(c.getCourseName());
            event.setCourse(c);
            if(!link.equals("")){
               event.setLink(link);
            }
            if(homework){
                event.setHomework(true);
            }
            eventRepository.save(event);
            model.addAttribute(c);
            return "/pCourse";
        }
        else
            return "/notfound";
    }
    @RequestMapping("/editEvent")
    public String editEvent(@RequestParam("eventId") Long eventId, Model model) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()){
            model.addAttribute("courseId", event.get().getCourse().getId());
            eventRepository.delete(event.get());
            return "/addEvent";
        }
        else
            return "/notfound";
    }

    @RequestMapping("/deleteEvent")
    public String deleteEvent(@RequestParam("eventId") Long eventId){
        if (eventRepository.existsById(eventId)){
            eventRepository.deleteById(eventId);
            return "redirect:/pCalendar";
        }
        else
            return "/notfound";
    }
    @RequestMapping("/showEvent")
    public String showEvent(@RequestParam("eventId") Long eventId, Model model){
        Optional<Event> result = eventRepository.findById(eventId);
        if (result.isPresent()) {
            model.addAttribute("event", result.get());
            return "/showEvent";
        }
        else{
            return "notfound";
        }
    }


    @RequestMapping("/grades")
    public String grades(@RequestParam("courseId") Long id, Model model) {
        Optional<Course> c = courseRepository.findById(id);
        if (c.isPresent()) {
            Course test = c.get();
            model.addAttribute("courseName", test.getCourseName());
            model.addAttribute("courseId", id);
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
    public String addGrades(@ModelAttribute("courseId") Long courseId,
                            @ModelAttribute("courseName") String courseName,
                            @ModelAttribute("examDate") String examDate,
                            @ModelAttribute("examType") String examType,
                            @ModelAttribute("studentForm") StudentForm studentForm,
                            Model model) {
        List<Student> students = studentForm.getStudents();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(examDate);
        } catch (ParseException e) {
            System.out.println("DATE PARSING ERROR");
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
        Optional<Course> c = courseRepository.findById(courseId);
        if(c.isPresent()){
            model.addAttribute(c.get());
            return "/pCourse";
        }
        else
            return "/notfound";
    }

    //--------------------CONTROLLER BACHECA AVVISI--------------------
    @RequestMapping("/noticeBoard")
    public String notice(Model model){
        Student s = studentRepository.findStudentByStudentId(studentId);
        Iterable<Notice> notices = noticeRepository.findAll();
        model.addAttribute("notices",notices);
        //model.addAttribute("student", s);

        return "noticeBoard";

    }

    //FIXME: non dovrebbe più servire
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

    //--------------------CONTROLLER ELEZIONI--------------------
    @RequestMapping("/studentVote")
    public String showElection(Model model){
        //TODO: booleano in student per il controllo del voto già effettuato che ritorna un allert
        Iterable<Candidate> candidates = candidateRepository.findAll();
        model.addAttribute("candidates", candidates);

        return "studentVote";
    }

    @RequestMapping("/newVote")
    public String newVote(@RequestParam("candidateId") String id){
        Optional<Candidate> c = candidateRepository.findById(Long.valueOf(id));
        if(c.isPresent()){
            Candidate a = c.get();
            candidateRepository.delete(a);
            a.addVote();
            candidateRepository.save(a);
            //FIXME: togliere test
            Iterable<Candidate> b = candidateRepository.findAll();
            for(Candidate s: b){
                System.out.println(s.getName() + s.getNumberVote());
            }
            return "sCourses";
        } else{
            return "notfound";
        }
    }

    @RequestMapping("/voteManager")
    public String voteManager(){
        return "voteManager";
    }

    @RequestMapping("/createCandidate")
    public String createCandidate(){
        return "createCandidate";
    }

    @RequestMapping("/newCandidate")
    public String newCandidate(@RequestParam(name="name", required=true) String name,
                               @RequestParam(name="surname", required=true) String surname,
                               @RequestParam(name="list", required=true) String list){
        candidateRepository.save(new Candidate(name,surname,list,0));
        return "voteManager";
    }

    /*@RequestMapping("/electionResult")
    public String electionResult(){
        return "electionResult";
    }*/

    @RequestMapping("/electionResult")
    public String showElectionResult(Model model){
        Iterable<Candidate> candidates = candidateRepository.findAll();
        model.addAttribute("candidates",candidates);
        return "electionResult";
    }

    @RequestMapping("/retVoteManager")
    public String returnToVoteManager(){
        return "voteManager";
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



    @GetMapping("/uploadFile") //visualizza la pagina html upload
    public String uploadPage(Model model) {
        FileListing fL= new FileListing();
        model.addAttribute("files", fL.getFileStringListing()); //popola la tabella con i file caricati
        return "uploadFileDocente";
    }

    @PostMapping("/upload") //Upload dei file lato docente
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        FileListing fL= new FileListing();
        // controlla che il file non sia vuoto
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/uploadFile";
        }

        // normalizza la path del file
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        //Mi prendo l'estensione del file
        String extensionFile="";

        int index= fileName.lastIndexOf('.');
        if(index > 0) {
            String extension = fileName.substring(index + 1);
            System.out.println("File extension is " + extension);
            extensionFile=extension;
        }
        // Salva il file nel file system locale
        if(extensionFile.equals("pdf")){
            try {
                Path path = Paths.get(fL.getUploadDir() + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // ritorna una risposta di successo
            attributes.addFlashAttribute("message", "File caricato con successo ==> " + fileName + '!');
        }else{
            attributes.addFlashAttribute("message", "Upload fallito ==> "+fileName+" Estensione file non supportata" + '!');
        }


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

    @GetMapping("/poll") //visualizza la pagina html upload
    public String poll() {

        return "poll";
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

        System.out.println(c.getId());
        System.out.println(c2.getId());

        //INIZIALIZZAZIONE CANDIDATI
        Candidate cand1 = new Candidate("Simone","Baldi","Lista 1",0);
        Candidate cand2 = new Candidate("Gianni","Caliari","Lista 2",0);
        Candidate cand3 = new Candidate("Raudo","Mefisto","Lista 3",0);
        candidateRepository.save(cand1);
        candidateRepository.save(cand2);
        candidateRepository.save(cand3);

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

    //QUESTION: Servono ancora? si possono eliminare tutti?

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





}
