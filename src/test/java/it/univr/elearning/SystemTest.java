package it.univr.elearning;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SystemTest extends BaseTest {

    @Test
    public void init(){
        driver.get("http://localhost:8080/init");
    }

    @Test
    public void testLoginProfessor(){
        driver.get("http://localhost:8080/login");
        String message1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message1);
        driver.findElement(By.name("userName")).sendKeys("asdfgh");
        driver.findElement(By.name("password")).sendKeys("mariano");
        driver.findElement(By.name("password")).submit();
        String message2 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", message2);
    }

    @Test
    public void testLoginStudent(){
        driver.get("http://localhost:8080/login");
        String message1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message1);
        driver.findElement(By.name("userName")).sendKeys("VR1234");
        driver.findElement(By.name("password")).sendKeys("andrea");
        driver.findElement(By.name("password")).submit();
        String message2 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", message2);
    }

    @Test
    public void testAddGrades(){
        driver.get("http://localhost:8080/login");
        String message1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message1);
        driver.findElement(By.name("userName")).sendKeys("qwerty");
        driver.findElement(By.name("password")).sendKeys("alessandro");
        driver.findElement(By.name("password")).submit();
        String message2 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", message2);
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click();
        driver.findElement(By.xpath("/html/body/a[1]")).click();
        driver.findElement(By.xpath("/html/body/form/input[1]")).sendKeys("14022022");
        driver.findElement(By.name("examType")).sendKeys("Scritto");
        driver.findElement(By.id("students0.lastGrade")).sendKeys("20");
        driver.findElement(By.id("students1.lastGrade")).sendKeys("30");
        driver.findElement(By.xpath("/html/body/form/input[5]")).click();
        String message3 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Fondamenti AI'", "Fondamenti AI", message3);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/a[3]")).click();
        String message4 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message4);
        driver.findElement(By.name("userName")).sendKeys("VR1234");
        driver.findElement(By.name("password")).sendKeys("andrea");
        driver.findElement(By.name("password")).submit();
        String message5 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", message5);
        driver.findElement(By.xpath("/html/body/a[1]")).click();
        String grade = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]")).getText();
        assertEquals("The grade should be '20'", "20", grade);
    }

    @Test
    public void testAddEvent(){
        driver.get("http://localhost:8080/login");
        String message1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message1);
        driver.findElement(By.name("userName")).sendKeys("qwerty");
        driver.findElement(By.name("password")).sendKeys("alessandro");
        driver.findElement(By.name("password")).submit();
        String message2 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", message2);
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click();
        driver.findElement(By.xpath("/html/body/a[2]")).click();
        driver.findElement(By.name("title")).sendKeys("Titolo evento");
        driver.findElement(By.name("description")).sendKeys("Descrizione dell'evento");
        driver.findElement(By.xpath("/html/body/form/input[3]")).sendKeys("14022022");
        driver.findElement(By.name("link")).sendKeys("https://www.linkdellevento.com");
        driver.findElement(By.xpath("/html/body/form/input[4]")).click();
        driver.findElement(By.xpath("/html/body/a[2]")).click();
        driver.findElement(By.name("title")).sendKeys("Titolo evento ");
        driver.findElement(By.name("description")).sendKeys("Descrizione dell'evento ");
        driver.findElement(By.xpath("/html/body/form/input[3]")).sendKeys("15022022");
        driver.findElement(By.name("link")).sendKeys("https://www.linkdellevento2.com");
        driver.findElement(By.xpath("/html/body/form/input[4]")).click();
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[3]/a")).click();
        String event1 = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]")).getText();
        assertEquals("The grade should be 'Titolo evento'", "titolo evento", event1);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[4]/a")).click();
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.name("title")).sendKeys("Titolo evento 3");
        driver.findElement(By.name("description")).sendKeys("Descrizione dell'evento 3");
        driver.findElement(By.xpath("/html/body/form/input[3]")).sendKeys("16022022");
        driver.findElement(By.name("link")).sendKeys("https://www.linkdellevento3.com");
        driver.findElement(By.xpath("/html/body/form/input[4]")).click();
    }

    @Test
    public void testAddHomework(){
        driver.get("http://localhost:8080/login");
        String message1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message1);
        driver.findElement(By.name("userName")).sendKeys("qwerty");
        driver.findElement(By.name("password")).sendKeys("alessandro");
        driver.findElement(By.name("password")).submit();
        String message2 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", message2);


        driver.findElement(By.name("homework")).click();

    }

    @Test
    public void testAddFile(){
        driver.get("http://localhost:8080/login");
        String message1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message1);
        driver.findElement(By.name("userName")).sendKeys("qwerty");
        driver.findElement(By.name("password")).sendKeys("alessandro");
        driver.findElement(By.name("password")).submit();
        String message2 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", message2);
    }

    @Test
    public void testPoll(){
        driver.get("http://localhost:8080/login");
        String message1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message1);
        driver.findElement(By.name("userName")).sendKeys("qwerty");
        driver.findElement(By.name("password")).sendKeys("alessandro");
        driver.findElement(By.name("password")).submit();
        String message2 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", message2);
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click();
        driver.findElement(By.xpath("/html/body/a[6]")).click();
        driver.findElement(By.name("title")).sendKeys("Titolo messaggio");
        driver.findElement(By.name("text")).sendKeys("Testo messaggio");
        driver.findElement(By.id("btn")).click();
        String message3 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Fondamenti AI'", "Fondamenti AI", message3);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/a[3]")).click();
        String message4 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message4);
        driver.findElement(By.name("userName")).sendKeys("VR1234");
        driver.findElement(By.name("password")).sendKeys("andrea");
        driver.findElement(By.name("password")).submit();
        String message5 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", message5);
        driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[4]/a")).click();
        driver.findElement(By.xpath("/html/body/a[2]")).click();
        String text = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]")).getText();
        assertEquals("The text should be Testo messaggio","Testo messaggio",text);
        driver.findElement(By.xpath("/html/body/a")).click();
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/a[5]")).click();
    }

    @Test
    public void testAddFileToCourse(){
        driver.get("http://localhost:8080/login");
        String message1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message1);
        driver.findElement(By.name("userName")).sendKeys("VR1234");
        driver.findElement(By.name("password")).sendKeys("andrea");
        driver.findElement(By.name("password")).submit();
        String message2 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", message2);
        driver.findElement(By.xpath("/html/body/a[3]")).click();
        driver.findElement(By.xpath("/html/body/a")).click();
        driver.findElement(By.name("title")).sendKeys("Titolo test");
        driver.findElement(By.name("text")).sendKeys("Testo test");
        driver.findElement(By.name("courseName")).sendKeys("Nome corso test");
        driver.findElement(By.id("btn")).click();
        String title = driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td[2]")).getText();
        assertEquals("Text should be 'Titolo test'","Titolo test",title);
        String text = driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td[3]")).getText();
        assertEquals("Text should be 'Testo test'","Testo test",text);
        String courseName = driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td[4]")).getText();
        assertEquals("Text should be 'Nome corso test'","Nome corso test",courseName);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/a[5]")).click();
    }

    @Test
    public void testAddMessage(){
        init();
        testLoginProfessor();
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click();
        String message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti di Ingegneria del SW",message);
        driver.findElement(By.xpath("/html/body/a[6]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Send a new message to students of: Fondamenti di Ingegneria del SW",message);
        driver.findElement(By.name("title")).sendKeys("Titolo");
        driver.findElement(By.name("text")).sendKeys("Testo");
        driver.findElement(By.id("btn")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti di Ingegneria del SW",message);
        driver.findElement(By.xpath("/html/body/a[7]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Messages of: Fondamenti di Ingegneria del SW",message);
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]")).getText();
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]")).getText();
        driver.findElement(By.xpath("/html/body/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti di Ingegneria del SW",message);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Courses",message);
        driver.findElement(By.xpath("/html/body/a[3]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login",message);
        testLoginStudent();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Courses",message);
        driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[4]/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti di Ingegneria del SW",message);
        driver.findElement(By.xpath("/html/body/a[2]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Messages of: Fondamenti di Ingegneria del SW",message);
        String title = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]")).getText();
        assertEquals("Text should be 'Titolo'","Titolo",title);
        String text = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]")).getText();
        assertEquals("Text should be 'Testo'","Testo",text);
        driver.findElement(By.xpath("/html/body/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti di Ingegneria del SW",message);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Courses",message);
        driver.findElement(By.xpath("/html/body/a[5]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login",message);
    }

    @Test
    public void testAddNotice(){
        init();
        testLoginStudent();
        driver.findElement(By.xpath("/html/body/a[3]")).click();
        String message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Notice board",message);
        driver.findElement(By.xpath("/html/body/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Create a new notice",message);
        driver.findElement(By.name("title")).sendKeys("Titolo");
        driver.findElement(By.name("text")).sendKeys("Testo");
        driver.findElement(By.name("courseName")).sendKeys("Nome corso");
        driver.findElement(By.id("btn")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Notice board",message);
        String title = driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td[2]")).getText();
        assertEquals("Text should be 'Titolo'","Titolo",title);
        String text = driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td[3]")).getText();
        assertEquals("Text should be 'Testo'","Testo",text);
        String courseName = driver.findElement(By.xpath("/html/body/table/tbody/tr[3]/td[4]")).getText();
        assertEquals("Text should be 'Nome corso'","Nome corso",courseName);
        driver.findElement(By.xpath("/html/body/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Create a new notice",message);
        driver.findElement(By.xpath("/html/body/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Notice board",message);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Courses",message);
        driver.findElement(By.xpath("/html/body/a[5]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login",message);
    }

    @Test
    public void testElections(){
        driver.get("http://localhost:8080/login");
        String message1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message1);
        driver.findElement(By.name("userName")).sendKeys("qwerty");
        driver.findElement(By.name("password")).sendKeys("alessandro");
        driver.findElement(By.name("password")).submit();
        String message2 = driver.findElement(By.tagName("h2")).getText();
        assertEquals("First row should be 'Vote manager for student election'", "Vote manager for student election", message2);
        driver.findElement(By.xpath("/html/body/a[2]")).click();
        

    }
}
