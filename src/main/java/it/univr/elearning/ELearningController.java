package it.univr.elearning;

import it.univr.elearning.model.*;
import it.univr.elearning.repository.*;
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
    private PollRepository pollRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private MessageRepository messageRepository;

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
    public String showCourses(@RequestParam("userName") String username,
                              @RequestParam("password") String password,
                              Model model){
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
            if(!username.equals(""))
                return "/pCourse";
            else
                return "/sCourse";
        }
        else
            return "/notfound";
    }

    @RequestMapping("/showCourse")
    public String showCourse(@RequestParam("courseId") Long courseId, Model model){
        FileListing fL = new FileListing();
        String courseName = "";
        Optional<Course> oCourse = courseRepository.findById(courseId);
        if(oCourse.isPresent()){
            Course c = oCourse.get();
            courseName = c.getCourseName() + " " + c.getAcademicYear();
            fL.setUploadDir(courseName);
            model.addAttribute("courseId", courseId);
            model.addAttribute("userName", courseName);
            model.addAttribute("files", fL.getFilePathListing(courseName));
            model.addAttribute("pathFile", fL.getFilePathNameListing(courseName));
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
            List<Event> genericEvents = c.getEvents();
            for(Event e : genericEvents)
                if(!e.isHomework())
                    events.add(e);
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
            List<Event> genericEvents = c.getEvents();
            for(Event e : genericEvents)
                if(!e.isHomework())
                    events.add(e);
        }
        model.addAttribute("events",events);
        model.addAttribute("professor", professor);
        return "pCalendar";
    }

    @RequestMapping("/retCalendar")
    public String returnToCalendar(Model model){
        if(username.equals(""))
            return "redirect:/sCalendar";
        else if (studentId.equals(""))
            return "redirect:/pCalendar";
        else
            return "/notfound";
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
                           @RequestParam(name = "homework", required = false) boolean homework,
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
            Event event = new Event(title, description,c.getCourseName(), date);
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
    public String deleteEvent(@RequestParam("eventId") Long eventId,Model model){
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            boolean homework = event.get().isHomework();
            eventRepository.deleteById(eventId);
            if (homework){
                model.addAttribute("courseId", event.get().getCourse().getId());
                returnToCourse(event.get().getCourse().getId(),model);
                return "/pCourse";
            }
            else
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
            if(result.get().isHomework()) {
                uploadHomework(eventId, model);
                return "/showSHomework";
            }
            else
                return "/showEvent";
        }
        else{
            return "notfound";
        }
    }


    public void uploadHomework(Long eventId, Model model){
        FileListing fL = new FileListing();
        Optional<Event> e = eventRepository.findById(eventId);
        Event event = new Event();
        if(e.isPresent())
            event = e.get();
        Course course = event.getCourse();
        String nameDirHomeworks = "Homeworks/" + course.getCourseName() + " " + course.getAcademicYear() + "/" + studentId;
        System.out.println("nome directory <> " + nameDirHomeworks);
        fL.setUploadDir(nameDirHomeworks);
        model.addAttribute("courseId", course.getId());
        model.addAttribute("userName", nameDirHomeworks);
    }

    @RequestMapping("/courseHomeworks")
    public String courseHomeworks(Model model, @RequestParam("courseId") Long courseId){
        Iterable<Event> events = eventRepository.findAll();
        List<Event> courseEvents = new ArrayList<>();
        for(Event event : events){
            if(event.isHomework() && event.getCourse().getId().equals(courseId))
                courseEvents.add(event);
        }
        model.addAttribute("events",courseEvents);
        model.addAttribute("courseId", courseId);
        if(!username.equals(""))
            return "pCourseHomeworks";
        else
            model.addAttribute("userName", studentId);
            return "sCourseHomeworks";

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



    @RequestMapping("/booklet")
    public String getBooklet(Model model){
        Iterable<Grade> grades = gradeRepository.findByStudent_StudentId(studentId);
        model.addAttribute("booklet",grades);
        Student s = studentRepository.findStudentByStudentId(studentId);
        model.addAttribute("student", s);
        return "booklet";
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
    public String newVote(@RequestParam("candidateId") Long id){
        Optional<Candidate> c = candidateRepository.findById(id);
        if(c.isPresent()){
            Candidate a = c.get();
            candidateRepository.delete(a);
            a.addVote();
            candidateRepository.save(a);
            return "redirect:/retCourses";
        } else{
            return "/notfound";
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
    public String newCandidate(@RequestParam("name") String name,
                               @RequestParam("surname") String surname,
                               @RequestParam("list") String list){
        candidateRepository.save(new Candidate(name,surname,list,0));
        return "voteManager";
    }


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

    //------------------------CONTROLLER MESSAGGI------------------------------

    @RequestMapping("/pNewMessage")
    public String message(Model model, @RequestParam("courseId") Long courseId){
        Optional<Course> a = courseRepository.findById(courseId);
        if (a.isPresent()) {
            Course course = a.get();
            model.addAttribute("courseName",course.getCourseName());
            model.addAttribute("courseId",courseId);
            return "/pNewMessage";
        } else{
            return "/notfound";
        }
    }

    @RequestMapping("/newMessage")
    public String newMessage(@RequestParam(name="title", required=true) String title,
                             @RequestParam(name="text", required=true) String text,
                             @RequestParam("courseId") Long courseId,
                             Model model){
        Optional<Course> a = courseRepository.findById(courseId);
        if (a.isPresent()){
            Course course = a.get();
            Message message = new Message(title,text,course.getCourseName());
            message.setCourse(course);
            messageRepository.save(message);
            returnToCourse(courseId,model);
            return "/pCourse";
        } else{
            return "/notfound"; //FIXME: cambiare ritorno
        }
    }


    @RequestMapping("/pShowCourseMessage")
    public String showCourseMessage(@RequestParam("courseId") Long courseId, Model model){
        Optional<Course> s = courseRepository.findById(courseId);
        if(s.isPresent()){
            Course test = s.get();
            model.addAttribute("courseName",test.getCourseName());
            model.addAttribute("courseId",courseId);
            List<Message> messages = messageRepository.findByCourse_id(courseId);
            model.addAttribute("messages",messages);
            return "/pShowCourseMessage";
        } else{
            return "/notfound";
        }
    }

    @RequestMapping("/sViewMessage")
    public String viewMessage(@RequestParam("courseId") Long courseId, Model model){
        Optional<Course> s = courseRepository.findById(courseId);
        if(s.isPresent()){
            Course test = s.get();
            model.addAttribute("courseName",test.getCourseName());
            model.addAttribute("courseId",courseId);
            List<Message> messages = messageRepository.findByCourse_id(courseId);
            model.addAttribute("messages",messages);
            return "/sViewMessage";
        } else{
            return "/notfound";
        }
    }

    @RequestMapping("/deleteMessage")
    public String deleteMessage(@RequestParam("messageId") Long messageId,Model model) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            messageRepository.deleteById(messageId);
            model.addAttribute("courseId", message.get().getCourse().getId());
            returnToCourse(message.get().getCourse().getId(), model);
            return "/pCourse";
        } else{
            return "redirect:/pShowCourseMessage";
        }
    }

    @RequestMapping("/editMessage")
    public String editMessage(@RequestParam("messageId") Long messageId, Model model){
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()){
            model.addAttribute("courseId",message.get().getCourse().getId());
            messageRepository.delete(message.get());
            return "/pNewMessage";
        } else{
            return "/notfound";
        }
    }




    //---------------------------------------------UPLOAD FILE STUDENTE------------------------------------------------------

    @GetMapping("/uploadStudente") //visualizza la pagina html upload lato studente
    public String uploadStudente(Model model) {
        FileListing fL = new FileListing();
        if (!studentId.equals("")) {
            fL.setUploadDir(studentId);
            model.addAttribute("userName", studentId);
            model.addAttribute("files", fL.getFileStringListing(studentId)); //popola la tabella con i file caricati
            return "cloudStudent";
        } else {
            return "/notfound";
        }
    }

//---------------------------------------------UPLOAD FILE DOCENTE------------------------------------------------------

    @GetMapping("/uploadDocente/{courseId}") //visualizza la pagina html upload lato docente
    public String uploadDocente(Model model,@PathVariable(name="courseId") Long courseId) {
        FileListing fL = new FileListing();
        Optional<Course> c = courseRepository.findById(courseId);
        String courseName = "";
        if (c.isPresent()) {
            courseName = c.get().getCourseName() + " " + c.get().getAcademicYear();

        }
        if (!username.equals("")) {
            String nameDirTeacher = courseName;
            System.out.println("nome directory <> " + nameDirTeacher);
            fL.setUploadDir(nameDirTeacher);
            model.addAttribute("courseId", courseId);
            model.addAttribute("userName", nameDirTeacher);
            model.addAttribute("files", fL.getFileStringListing(nameDirTeacher)); //popola la tabella con i file caricati
            return "/uploadDocente";
        } else {
            return "/notfound";
        }
    }




    @PostMapping("/upload") //Upload dei file sia docente che studente
    public String uploadFile(Model model,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam(name = "courseId", required = false) Long courseId,
                             RedirectAttributes attributes,
                             @RequestParam("userName") String userName,
                             @RequestParam(name = "eventId", required = false) Long eventId) {


        FileListing fL= new FileListing();

        // controlla che il file non sia vuoto
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");

            if(!username.equals("")){
                    model.addAttribute("courseId", courseId);
                    return "redirect:/uploadDocente/" + courseId;
            }else{
                if(eventId == null)
                    return "redirect:/uploadStudente";
                else {
                    returnToCourse(courseId, model);
                    return "/sCourse";
                }
            }
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
        if((extensionFile.equals("pdf"))||(extensionFile.equals("docx"))||(extensionFile.equals("tar.gz"))||(extensionFile.equals("zip"))||(extensionFile.equals("tar"))||(extensionFile.equals("txt"))||(extensionFile.equals("rar"))){
            try {
                Path path = Paths.get(fL.getUploadDir(userName) + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // ritorna una risposta di successo
            attributes.addFlashAttribute("message", "File caricato con successo ==> " + fileName + '!');
        }else{
            attributes.addFlashAttribute("message", "Upload fallito ==> "+fileName+" Estensione file non supportata" + '!');
        }


        if(!username.equals("")){
                model.addAttribute("courseId", courseId);
                return "redirect:/uploadDocente/" + courseId;
        }else{
            if(eventId == null)
                return "redirect:/uploadStudente";
            else {
                returnToCourse(courseId, model);
                return "/sCourse";
            }
        }
    }

    @PostMapping("/delete") // elimina il file selezionato
    public String deleteFile(Model model,@RequestParam("file") String fileName,
                             @RequestParam("userName") String userName,
                             @RequestParam(name="courseId",required = false) Long courseId) {

        FileListing fL=new FileListing();
        File[]files=fL.getFilePathListing(userName); //Prendo la lista dei file

        for (File file : files) {
            if(file.getName().equals(fileName)){ //cicla la lista dei file ed elimina quello scelto
                file.delete();
            }
        }

        if(!username.equals("")){
            Optional<Course> c = courseRepository.findById(courseId);
            Course course = new Course();
            if(c.isPresent())
                course = c.get();
            model.addAttribute(course);
            model.addAttribute("courseId", courseId);
            return "redirect:/uploadDocente/"+courseId;
        }else{
            return "redirect:/uploadStudente";
        }


    }





//---------------------------------------------SONDAGGIO--------------------------------------------------------------

    @GetMapping("/poll/{courseId}") //visualizza la pagina html poll
    public String poll(Model model,@PathVariable(name="courseId") Long courseId) {
        model.addAttribute(courseId);
        return "poll";
    }

    @RequestMapping("/pPoll")
    public String showPollPage(Model model){
        Professor professor = professorRepository.findByUserName(username);
        List<Course> courses = professor.getCourses();
        List<Poll> polls = new ArrayList<>();
        for(Course c : courses){
            List<Poll> genericPoll = c.getPolls();
            polls.addAll(genericPoll);
        }
        model.addAttribute("polls",polls);
        model.addAttribute("professor", professor);
        return "PollManager";
    }

    @RequestMapping("/addPoll")
    public String addPoll(@RequestParam("courseId") Long courseId, Model model){
        model.addAttribute("courseId", courseId);
        return "/addPoll";
    }

    @RequestMapping("/newPoll")
    public String newPoll(@RequestParam("title") String title,
                          @RequestParam(name = "mandatory", required = false) boolean mandatory,
                           @RequestParam("courseId") Long courseId,
                           Model model){
        Optional<Course> oCourse = courseRepository.findById(courseId);
        if(oCourse.isPresent()) {
            Course c = oCourse.get();
            Poll poll = new Poll(title);
            poll.setCoursePollName(c.getCourseName());
            System.out.println(poll.getCoursePollName());
            poll.setCourse(c);

            if(mandatory){
                poll.setMandatory(true);
            }
            pollRepository.save(poll);
            model.addAttribute(c);
            model.addAttribute(courseId);
            return "redirect:/poll/"+courseId;
        }
        else
            return "/notfound";
    }

    @RequestMapping("/editPoll")
    public String editPoll(@RequestParam("pollId") Long pollId, Model model) {
        Optional<Poll> poll = pollRepository.findById(pollId);
        if (poll.isPresent()){

            model.addAttribute("courseId", poll.get().getCourse().getId());
            pollRepository.delete(poll.get());
            return "/addPoll";
        }
        else
            return "/notfound";
    }

    @RequestMapping("/deletePoll")
    public String deletePoll(@RequestParam("pollId") Long pollId){
        Optional<Poll> poll = pollRepository.findById(pollId);
        if (poll.isPresent()) {
            pollRepository.deleteById(pollId);
            return "redirect:/pPoll";
        }
        else
            return "/notfound";
    }






    //TEST
    public void initTest(){
        Student s1 = new Student("Andrea", "Rossetti","VR1234");
        Student s2 = new Student("Simone", "Baldi","VR9876");
        s1.setPassword("andrea");
        s2.setPassword("simone");
        studentRepository.save(s1);
        studentRepository.save(s2);
        Professor p1 = new Professor("Alessandro", "Farinelli", "qwerty");
        p1.setPassword("alessandro");
        Course c = new Course("Fondamenti AI", "Farinelli","2021-22");
        c.setStudent(s1);
        c.setStudent(s2);
        c.setProfessor(p1);
        p1.setCourse(c);
        courseRepository.save(c);
        professorRepository.save(p1);

        Course c2 = new Course("Fondamenti di Ingegneria del SW", "Ceccato","2021-22");
        Professor p2 = new Professor("Mariano", "Ceccato", "asdfgh");
        p2.setPassword("mariano");
        c2.setStudent(s1);
        c2.setProfessor(p2);
        p2.setCourse(c2);
        courseRepository.save(c2);
        professorRepository.save(p2);


        //INIZIALIZZAZIONE CANDIDATI
        Candidate cand1 = new Candidate("Simone","Baldi","Lista 1",0);
        Candidate cand2 = new Candidate("Gianni","Caliari","Lista 2",0);
        Candidate cand3 = new Candidate("Raudo","Mefisto","Lista 3",0);
        candidateRepository.save(cand1);
        candidateRepository.save(cand2);
        candidateRepository.save(cand3);

        String examDate = "1970-01-01";
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(examDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Grade g = new Grade("test", "test", "test", date);
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
                //System.out.println("Directory is created!");
            }
        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
        }
    }


}
