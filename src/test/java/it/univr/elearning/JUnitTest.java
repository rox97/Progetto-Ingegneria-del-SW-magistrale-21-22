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
    public void studentTest(){
        Student student = new Student("Andrea", "Rossetti", "VR1234");
        student.setPassword("andrea");
        student.setLastGrade("25");
        Course course1 = new Course("Fondamenti di Ingegneria del Software", "Mariano Ceccato", "2021-22");
        student.setCourse(course1);
        Course course2 = new Course("Fondamenti di Intelligenza Artificiale", "Alessandro Farinelli", "2021-22");
        student.setCourse(course2);
        String examDate = "2022-02-14";
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(examDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Grade grade1 = new Grade ("Fondamenti di Ingegneria del Software", "Scritto", "25", date);
        Grade grade2 = new Grade ("Fondamenti di Ingegneria del Software", "Pratico", "20", date);
        Grade grade3 = new Grade ("Fondamenti di Intelligenza Artificiale", "Orale", "25", date);
        student.setGrade(grade1);
        student.setGrade(grade2);
        student.setGrade(grade3);
        List<Grade> grades2 = new ArrayList<>();
        grades2.add(grade1);
        grades2.add(grade2);
        grades2.add(grade3);

        assertEquals("Andrea", student.getFirstName());
        assertEquals("Rossetti", student.getLastName());
        assertEquals("25", student.getLastGrade());
        assertEquals(grades2, student.getGrades());





    }

}
