package it.univr.elearning;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;


public class SystemTest extends BaseTest {

    @Test
    public void testAddEvent() {
        driver.get("http://localhost:8080/");
        String message1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message1);
        driver.findElement(By.name("userName")).sendKeys("asdfgh");
        driver.findElement(By.name("password")).sendKeys("mariano");
        driver.findElement(By.name("password")).submit();
        String firstRow = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", firstRow);
    }

}
