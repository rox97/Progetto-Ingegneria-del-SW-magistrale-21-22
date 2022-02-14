package it.univr.elearning;

import it.univr.elearning.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class JUnitTest {

    @Test
    public void studentTest() throws ParseException {
        Student student1 = new Student("Andrea", "Rossetti", "VR1234");
        student1.setPassword("andrea");
        student1.setLastGrade("25");
        student1.setId(1L);

        Course course1 = new Course("Fondamenti di Ingegneria del Software", "Mariano Ceccato", "2021-22");
        Course course2 = new Course("Fondamenti di Intelligenza Artificiale", "Alessandro Farinelli", "2021-22");
        Course course3 = new Course("Estrazione e Integrazione dei Dati", "Pietro Sala", "2021-22");
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        student1.setCourses(courses);
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

    @Test
    public void professorTest(){
        Professor professor1 = new Professor("Mariano", "Ceccato", "asdfgh");
        professor1.setId(1L);
        professor1.setPassword("mariano");

        Course course1 = new Course("Fondamenti di Ingegneria del Software", "Mariano Ceccato", "2021-22");
        Course course2 = new Course("Fondamenti di Ingegneria del Software", "Mariano Ceccato", "2020-21");
        Course course3 = new Course("Fondamenti di Ingegneria del Software", "Mariano Ceccato", "2019-20");
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        professor1.setCourses(courses);
        courses.add(course3);
        professor1.setCourse(course3);

        assertEquals(1L, professor1.getId());
        assertEquals("Mariano", professor1.getProfessorName());
        assertEquals("Ceccato", professor1.getProfessorSurname());
        assertEquals("asdfgh", professor1.getUserName());
        assertEquals("mariano", professor1.getPassword());
        assertEquals(courses, professor1.getCourses());

        professor1.setProfessorName("Alessandro");
        professor1.setProfessorSurname("Farinelli");
        professor1.setUserName("qwerty");

        assertEquals("Alessandro", professor1.getProfessorName());
        assertEquals("Farinelli", professor1.getProfessorSurname());
        assertEquals("qwerty", professor1.getUserName());

    }

    @Test
    public void courseTest() throws ParseException {
        Course course1 = new Course("Fondamenti di Ingegneria del Software", "Mariano Ceccato", "2021-22");
        course1.setId(1L);

        Student student1 = new Student("Andrea", "Rossetti", "VR1234");
        Student student2 = new Student("Andrea", "Caliari", "VR5678");
        Student student3 = new Student("Simone", "Baldi", "VR9012");
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        course1.setStudents(students);
        students.add(student3);
        course1.setStudent(student3);

        Professor professor1 = new Professor("Mariano", "Ceccato", "asdfgh");
        course1.setProfessor(professor1);

        String eventDate = "2022-02-14";
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(eventDate);
        Event event1 = new Event("Seminario", "Seminario di test","Fondamenti di Ingegneria del Software", date1);
        Event event2 = new Event("Seminario 2", "Seminario 2 di test","Fondamenti di Ingegneria del Software", date1);
        Event event3 = new Event("Seminario 3", "Seminario 3 di test","Fondamenti di Ingegneria del Software", date1);
        List<Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);
        course1.setEvents(events);
        events.add(event3);
        course1.setEvent(event3);

        Message message1 = new Message("Messaggio 1", "Messaggio 1 di test", "Fondamenti di Ingegneria del Software");
        Message message2 = new Message("Messaggio 2", "Messaggio 2 di test", "Fondamenti di Ingegneria del Software");
        Message message3 = new Message("Messaggio 3", "Messaggio 3 di test", "Fondamenti di Ingegneria del Software");
        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        course1.setMessages(messages);
        messages.add(message3);
        course1.setMessage(message3);

        assertEquals(1L, course1.getId());
        assertEquals("Fondamenti di Ingegneria del Software", course1.getCourseName());
        assertEquals("Mariano Ceccato", course1.getCoordinatorName());
        assertEquals("2021-22", course1.getAcademicYear());
        assertEquals(students, course1.getStudents());
        assertEquals(professor1, course1.getProfessor());
        assertEquals(events, course1.getEvents());
        assertEquals(messages, course1.getMessages());

        course1.setCourseName("Fondamenti di Intelligenza Artificiale");
        course1.setCoordinatorName("Alessandro Farinelli");
        course1.setAcademicYear("2020-21");
        assertEquals("Fondamenti di Intelligenza Artificiale", course1.getCourseName());
        assertEquals("Alessandro Farinelli", course1.getCoordinatorName());
        assertEquals("2020-21", course1.getAcademicYear());

    }

    @Test
    public void eventTest() throws ParseException {
        String eventDate = "2022-02-14";
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(eventDate);
        Event event1 = new Event("Seminario", "Seminario di test","Fondamenti di Ingegneria del Software",date1);
        event1.setId(1L);
        event1.setHomework(true);
        event1.setLink("https://www.testlink.com");
        Course course1 = new Course("Fondamenti di Ingegneria del Software", "Mariano Ceccato", "2021-22");
        event1.setCourse(course1);

        assertEquals(1L, event1.getId());
        assertEquals("Seminario", event1.getEventTitle());
        assertEquals("Seminario di test", event1.getEventDescription());
        assertEquals("Fondamenti di Ingegneria del Software", event1.getEventCourse());
        assertEquals(date1, event1.getEventDate());
        assertTrue(event1.isHomework());
        assertEquals("https://www.testlink.com", event1.getLink());
        assertEquals(course1, event1.getCourse());

        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-02-15");
        event1.setEventTitle("Seminario 2");
        event1.setEventDescription("Seminario 2 di test");
        event1.setEventCourse("Fondamenti di Intelligenza Artificiale");
        event1.setEventDate(date2);

        assertEquals("Seminario 2", event1.getEventTitle());
        assertEquals("Seminario 2 di test", event1.getEventDescription());
        assertEquals("Fondamenti di Intelligenza Artificiale", event1.getEventCourse());
        assertEquals(date2, event1.getEventDate());





    }


}
