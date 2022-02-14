package it.univr.elearning;

import io.restassured.RestAssured;
import it.univr.elearning.model.*;
import it.univr.elearning.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;



//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class JUnitTest {
/*
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }
*/
    @Test
    public void studentTest() throws ParseException {
        Student student1 = new Student("Andrea", "Rossetti", "VR1234");
        student1.setPassword("andrea");
        student1.setLastGrade("25");
        student1.setId(1L);

        Course course1 = new Course("Fondamenti di Ingegneria del Software", "Mariano Ceccato", "2021-22");
        Course course2 = new Course("Fondamenti di Intelligenza Artificiale", "Alessandro Farinelli", "2021-22");
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        student1.setCourses(courses);
        Course course3 = new Course("Estrazione e Integrazione dei Dati", "Pietro Sala", "2021-22");
        student1.setCourse(course3);
        courses.add(course3);

        String examDate = "2022-02-14";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(examDate);

        Grade grade1 = new Grade ("Fondamenti di Ingegneria del Software", "Scritto", "25", date);
        Grade grade2 = new Grade ("Fondamenti di Ingegneria del Software", "Pratico", "20", date);
        Grade grade3 = new Grade ("Fondamenti di Intelligenza Artificiale", "Orale", "25", date);
        student1.setGrade(grade1);
        student1.setGrade(grade2);
        student1.setGrade(grade3);
        List<Grade> grades = new ArrayList<>();
        grades.add(grade1);
        grades.add(grade2);
        grades.add(grade3);

        assertEquals(1L, student1.getId());
        assertEquals("Andrea", student1.getFirstName());
        assertEquals("Rossetti", student1.getLastName());
        assertEquals("andrea", student1.getPassword());
        assertEquals("VR1234", student1.getStudentId());
        assertEquals("25", student1.getLastGrade());
        assertEquals(courses, student1.getCourses());
        assertEquals(grades, student1.getGrades());

        student1.setFirstName("Simone");
        student1.setLastName("Baldi");
        student1.setStudentId("VR9012");
        assertEquals("Simone", student1.getFirstName());
        assertEquals("Baldi", student1.getLastName());
        assertEquals("VR9012", student1.getStudentId());

    }

    @Test
    public void gradeTest() throws ParseException {
        String examDate = "2022-02-14";
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(examDate);

        Grade grade1 = new Grade ("Fondamenti di Ingegneria del Software", "Scritto", "25", date1);
        grade1.setId(1L);

        Student student1 = new Student("Andrea", "Rossetti", "VR1234");
        grade1.setStudent(student1);

        assertEquals("Fondamenti di Ingegneria del Software", grade1.getCourseName());
        assertEquals("Scritto", grade1.getExamType());
        assertEquals("25", grade1.getGrade());
        assertEquals(date1, grade1.getExamDate());
        assertEquals(1L, grade1.getId());
        assertEquals(student1,grade1.getStudent());

        grade1.setGrade("30");
        grade1.setExamType("Orale");
        grade1.setCourseName("Fondamenti di Intelligenza Artificiale");
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-02-15");
        grade1.setExamDate(date2);
        assertEquals("Fondamenti di Intelligenza Artificiale", grade1.getCourseName());
        assertEquals("Orale", grade1.getExamType());
        assertEquals("30", grade1.getGrade());
        assertEquals(date2, grade1.getExamDate());




    }

}
