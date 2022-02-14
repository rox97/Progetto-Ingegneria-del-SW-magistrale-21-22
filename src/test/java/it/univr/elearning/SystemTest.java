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
    public void testLogin(){
        driver.get("http://localhost:8080/login");
        String message1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message1);
        driver.findElement(By.name("userName")).sendKeys("asdfgh");
        driver.findElement(By.name("password")).sendKeys("mariano");
        //TODO: capire come funziona
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
        driver.get("http://localhost:8080/login");
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
        driver.get("http://localhost:8080/login");
        String message3 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message3);
        driver.findElement(By.name("userName")).sendKeys("qwerty");
        driver.findElement(By.name("password")).sendKeys("alessandro");
        driver.findElement(By.name("password")).submit();
        String message4 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", message4);
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
    }

    @Test
    public void testAddFileToCourse(){
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
    public void testAddMessage(){
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
    public void testAddNotice(){
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
    public void testElections(){
        driver.get("http://localhost:8080/login");
        String message1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message1);
        driver.findElement(By.name("userName")).sendKeys("qwerty");
        driver.findElement(By.name("password")).sendKeys("alessandro");
        driver.findElement(By.name("password")).submit();
        String message2 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", message2);
    }
}
