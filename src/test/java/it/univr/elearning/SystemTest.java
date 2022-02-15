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
        //init();
        testLoginStudent(); //login credenziali studente

        driver.findElement(By.xpath("/html/body/form/input")).click(); // Entro nella pagina di archiviazione personale lato studente
        String title1 = driver.findElement(By.tagName("h2")).getText();
        assertEquals("First row should be 'Cloud Personale'", "Cloud Personale", title1); //Controllo di essere entrato nel corso giusto

        WebElement uploadElement = driver.findElement(By.xpath("/html/body/section/div/div/div/form[1]/div/input[2]"));

        uploadElement.sendKeys(System.getProperty("user.dir")+ "\\src\\test\\resources\\fileTest\\Course-project.pdf"); //Seleziono il file da caricare
        driver.findElement(By.xpath("/html/body/section/div/div/div/form[1]/button")).click(); // Faccio upload del file
        title1 = driver.findElement(By.xpath("/html/body/section/div/div/div/p")).getText();

        String[] trimmedText = title1.split("==>");
        assertEquals("First row should be 'File caricato con successo '", "File caricato con successo ", trimmedText[0]); //Controllo che il file sia stato caricato
        //driver.findElement(By.xpath("/html/body/section/div/div/div/table/tbody/tr/td[2]/form/input[3]")).click(); // Elimino il file
        driver.findElement(By.xpath("/html/body/section/div/div/div/form[2]/input")).click(); // Torno alla pagina dei corsi

        title1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", title1); //Controllo di essere tornato alla pagina dei corsi


    }


    @Test
    public void testPoll(){
        //init();
        testLoginProfessor();//Login con credenziali professore

        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click(); // Entro nella pagina del corso
        String title1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Fondamenti di Ingegneria del SW'", "Fondamenti di Ingegneria del SW", title1); //Controllo di essere entrato nel corso giusto
        driver.findElement(By.xpath("/html/body/a[4]/button")).click(); //Entro nella pagina del sondaggio

        title1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Create a new Survey'", "Create a new Survey", title1); //Controllo di essere entrato nella pagina sondaggio
        driver.findElement(By.name("title")).sendKeys("Sondaggio qualità del corso"); // Inserisco titolo sondaggio
        driver.findElement(By.xpath("/html/body/form/input[2]")).click(); //Clicco sulla checkbox isMandatory

        driver.findElement(By.xpath("/html/body/form/input[4]")).click(); //Clicco sul salva
        title1 = driver.findElement(By.xpath("/html/body/div/label")).getText();
        assertEquals("First row should be 'Answer Survey:'", "Answer Survey:", title1); //Controllo di essere nella pagina di creazione delle domande

        driver.findElement(By.xpath("/html/body/div/input")).sendKeys("Ritiene che il materiale fornito sia sufficente per seguire le lezioni?"); //Inserimento dati sondaggio
        driver.findElement(By.xpath("/html/body/form[1]/div/input[2]")).sendKeys("No");
        driver.findElement(By.xpath("/html/body/button[1]")).click();
        driver.findElement(By.xpath("/html/body/form[1]/div[2]/input[2]")).sendKeys("Abbastanza");
        driver.findElement(By.xpath("/html/body/button[1]")).click();
        driver.findElement(By.xpath("/html/body/form[1]/div[3]/input[2]")).sendKeys("Si");
        driver.findElement(By.xpath("/html/body/form[2]/input[2]")).click(); //Clicco fine per salvare il sondaggio

        title1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Fondamenti di Ingegneria del SW'", "Fondamenti di Ingegneria del SW", title1); //Controllo di essere tornato alla pagina del corso

        driver.findElement(By.xpath("/html/body/form/input")).click(); //Clicco fine per tornare alla pagina dei corsi
        title1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", title1);


    }

    @Test
    public void testAddFileToCourse(){
        //init();
        testLoginProfessor();//login credenziali professore

        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click(); // Entro nella pagina del corso
        String title1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Fondamenti di Ingegneria del SW'", "Fondamenti di Ingegneria del SW", title1); //Controllo di essere entrato nel corso giusto
        driver.findElement(By.xpath("/html/body/a[3]/button")).click(); //Entro nella pagina di aggiunta file

        title1 = driver.findElement(By.tagName("h2")).getText();
        assertEquals("First row should be 'Upload File Docente aggiornato'", "Upload File Docente aggiornato", title1); //Controllo di essere entrato nella pagina sondaggio

        WebElement uploadElement = driver.findElement(By.xpath("/html/body/section/div/div/div/form[1]/div/input[3]"));

        uploadElement.sendKeys(System.getProperty("user.dir")+ "\\src\\test\\resources\\fileTest\\Testing.pdf"); //Seleziono il file da caricare
        driver.findElement(By.xpath("/html/body/section/div/div/div/form[1]/button")).click(); // Faccio upload del file
        title1 = driver.findElement(By.xpath("/html/body/section/div/div/div/p")).getText();

        String[] trimmedText = title1.split("==>");
        assertEquals("First row should be 'File caricato con successo '", "File caricato con successo ", trimmedText[0]); //Controllo che il file sia stato caricato
        //driver.findElement(By.xpath("/html/body/section/div/div/div/table/tbody/tr/td[2]/form/input[3]")).click(); // Elimino il file
        driver.findElement(By.xpath("/html/body/section/div/div/div/form[2]/input[2]")).click(); // Torno alla pagina del corso

        title1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Fondamenti di Ingegneria del SW'", "Fondamenti di Ingegneria del SW", title1); //Controllo di essere tornato nel corso giusto
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
