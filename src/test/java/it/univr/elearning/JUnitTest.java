package it.univr.elearning;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;



//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class JUnitTest {

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

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void firstTest(){
        //Utils.initCoursesDatabase();
        //Utils.initStudentsDatabase();
        Student s1 = new Student("andrea", "rossetti","VR1234");
        Student s2 = new Student("simone", "baldi","VR9876");
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
        //Assertions.assertEquals("VR1234", s1.getStudentId());
    }
    @Test
    public void test(){
        firstTest();

        given()
                .queryParam("userName", "VR1234")
                .queryParam("password", "andrea")
                .when()
                .get("/courses")
                .then()
                .statusCode(200);
    }

}
