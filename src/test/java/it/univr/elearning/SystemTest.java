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
    public void aInit(){
        driver.get("http://localhost:8080/");
    }

    @Test
    public void aTestLoginProfessor(){ //Eseguo il login come professore
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
    public void bTestLoginStudent(){//Eseguo il login come studente
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
        aTestLoginProfessor();
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
        bTestLoginStudent();
        driver.findElement(By.xpath("/html/body/a[1]")).click();
        String grade1 = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]")).getText();
        assertEquals("The grade should be '20'", "20", grade1);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/a[5]")).click();
        aTestLoginProfessor();
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click();
        driver.findElement(By.xpath("/html/body/a[1]")).click();
        driver.findElement(By.xpath("/html/body/form/input[1]")).sendKeys("14022022");
        driver.findElement(By.name("examType")).sendKeys("Orale");
        driver.findElement(By.id("students0.lastGrade")).sendKeys("30");
        driver.findElement(By.xpath("/html/body/form/input[5]")).click();
        String message5 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Fondamenti AI'", "Fondamenti AI", message5);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/a[3]")).click();
        String message6 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message6);
        bTestLoginStudent();
        driver.findElement(By.xpath("/html/body/a[1]")).click();
        String grade2 = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]")).getText();
        assertEquals("The grade should be '30'", "30", grade2);
    }

    @Test
    public void testAddEvent(){
        aTestLoginProfessor();
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click();
        driver.findElement(By.xpath("/html/body/a[2]")).click();
        driver.findElement(By.name("title")).sendKeys("Titolo evento");
        driver.findElement(By.name("description")).sendKeys("Descrizione dell'evento");
        driver.findElement(By.xpath("/html/body/form/input[3]")).sendKeys("14022022");
        driver.findElement(By.name("link")).sendKeys("https://www.linkdellevento.com");
        driver.findElement(By.xpath("/html/body/form/input[7]")).click();
        driver.findElement(By.xpath("/html/body/a[2]")).click();
        driver.findElement(By.name("title")).sendKeys("Titolo evento 2");
        driver.findElement(By.name("description")).sendKeys("Descrizione dell'evento 2");
        driver.findElement(By.xpath("/html/body/form/input[3]")).sendKeys("15022022");
        driver.findElement(By.name("link")).sendKeys("https://www.linkdellevento2.com");
        driver.findElement(By.xpath("/html/body/form/input[7]")).click();
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/a[1]")).click();
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]/a")).click();
        String event1 = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]")).getText();
        assertEquals("The event title should be 'Titolo evento'", "Titolo evento", event1);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[4]/a")).click();
        driver.findElement(By.name("title")).sendKeys("Titolo evento 3");
        driver.findElement(By.name("description")).sendKeys("Descrizione dell'evento 3");
        driver.findElement(By.xpath("/html/body/form/input[3]")).sendKeys("16022022");
        driver.findElement(By.name("link")).sendKeys("https://www.linkdellevento3.com");
        driver.findElement(By.xpath("/html/body/form/input[7]")).click();
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/a[1]")).click();
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]/a")).click();
        String event2 = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]")).getText();
        assertEquals("The event title should be 'Titolo evento 2'", "Titolo evento 2", event2);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[5]/a")).click();
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]/a")).click();
        String event3 = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]")).getText();
        assertEquals("The event title should be 'Titolo evento 3'", "Titolo evento 3", event3);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/a[3]")).click();
        String message3 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message3);
        bTestLoginStudent();
        driver.findElement(By.xpath("/html/body/a[2]")).click();
        String message4 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Student's calendar''", "Student's calendar", message4);
        driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[3]/a")).click();
        String event4 = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]")).getText();
        assertEquals("The event title should be 'Titolo evento 3'", "Titolo evento 3", event4);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/form/input")).click();
        String message5 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", message5);
    }

    @Test
    public void testAddHomework(){
        aTestLoginProfessor(); //effettuo il login come professore
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click();
        driver.findElement(By.xpath("/html/body/a[2]")).click();
        driver.findElement(By.name("title")).sendKeys("Titolo homework");
        driver.findElement(By.name("description")).sendKeys("Descrizione dell'homework");
        driver.findElement(By.xpath("/html/body/form/input[3]")).sendKeys("14022022");
        driver.findElement(By.name("homework")).click();
        driver.findElement(By.xpath("/html/body/form/input[7]")).click();
        driver.findElement(By.xpath("/html/body/a[2]")).click();
        driver.findElement(By.name("title")).sendKeys("Titolo homework 2");
        driver.findElement(By.name("description")).sendKeys("Descrizione dell'homework 2");
        driver.findElement(By.xpath("/html/body/form/input[3]")).sendKeys("15022022");
        driver.findElement(By.name("homework")).click();
        driver.findElement(By.xpath("/html/body/form/input[7]")).click();
        driver.findElement(By.xpath("/html/body/a[5]")).click();
        String homework1 = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]")).getText();
        assertEquals("The homework title should be 'Titolo homework'", "Titolo homework", homework1);
        driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[3]/a")).click();
        driver.findElement(By.name("title")).sendKeys("Titolo homework 3");
        driver.findElement(By.name("description")).sendKeys("Descrizione dell'homework 3");
        driver.findElement(By.xpath("/html/body/form/input[3]")).sendKeys("16022022");
        driver.findElement(By.name("homework")).click();
        driver.findElement(By.xpath("/html/body/form/input[7]")).click();
        driver.findElement(By.xpath("/html/body/a[5]")).click();
        String homework2 = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]")).getText();
        assertEquals("The homework title should be 'Titolo homework 2'", "Titolo homework 2", homework2);
        driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[4]/a")).click();
        driver.findElement(By.xpath("/html/body/a[5]")).click();
        String homework3 = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]")).getText();
        assertEquals("The homework title should be 'Titolo homework 3'", "Titolo homework 3", homework3);
        driver.findElement(By.xpath("/html/body/form/input[2]")).click();
        driver.findElement(By.xpath("/html/body/form/input")).click();
        driver.findElement(By.xpath("/html/body/a[3]")).click();
        String message3 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message3);
        bTestLoginStudent();
        driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[4]/a")).click();
        driver.findElement(By.xpath("/html/body/a[1]")).click();
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]/a")).click();
        String homework4 = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]")).getText();
        assertEquals("The homework title should be 'Titolo homework 3'", "Titolo homework 3", homework4);
    }

    @Test
    public void testAddFile(){
        //init();
        bTestLoginStudent(); //login credenziali studente

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
        aTestLoginProfessor();//Login con credenziali professore

        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click(); // Entro nella pagina del corso
        String title1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Fondamenti AI'", "Fondamenti AI", title1); //Controllo di essere entrato nel corso giusto
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
        assertEquals("First row should be 'Fondamenti AI'", "Fondamenti AI", title1); //Controllo di essere tornato alla pagina del corso

        driver.findElement(By.xpath("/html/body/form/input")).click(); //Clicco fine per tornare alla pagina dei corsi
        title1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Courses'", "Courses", title1);


    }

    @Test
    public void testAddFileToCourse(){
        //init();
        aTestLoginProfessor();//login credenziali professore

        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click(); // Entro nella pagina del corso
        String title1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Fondamenti AI'", "Fondamenti AI", title1); //Controllo di essere entrato nel corso giusto
        driver.findElement(By.xpath("/html/body/a[3]/button")).click(); //Entro nella pagina di aggiunta file

        title1 = driver.findElement(By.tagName("h2")).getText();
        assertEquals("First row should be 'Upload File Docente aggiornato'", "Upload File Docente aggiornato", title1); //Controllo di essere entrato nella pagina sondaggio

        WebElement uploadElement = driver.findElement(By.xpath("/html/body/section/div/div/div/form[1]/div/input[3]"));

        uploadElement.sendKeys(System.getProperty("user.dir")+ "\\src\\test\\resources\\fileTest\\Course-project.pdf"); //Seleziono il file da caricare
        driver.findElement(By.xpath("/html/body/section/div/div/div/form[1]/button")).click(); // Faccio upload del file
        title1 = driver.findElement(By.xpath("/html/body/section/div/div/div/p")).getText();

        String[] trimmedText = title1.split("==>");
        assertEquals("First row should be 'File caricato con successo '", "File caricato con successo ", trimmedText[0]); //Controllo che il file sia stato caricato
        //driver.findElement(By.xpath("/html/body/section/div/div/div/table/tbody/tr/td[2]/form/input[3]")).click(); // Elimino il file
        driver.findElement(By.xpath("/html/body/section/div/div/div/form[2]/input[2]")).click(); // Torno alla pagina del corso

        title1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("First row should be 'Fondamenti AI'", "Fondamenti AI", title1); //Controllo di essere tornato nel corso giusto
    }

    @Test
    public void testMessage(){
        //aInit();
        aTestLoginProfessor(); //login con credenziali professore
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click();
        String message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti AI",message);
        //create first message
        driver.findElement(By.xpath("/html/body/a[6]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Send a new message to students of: Fondamenti AI",message);
        driver.findElement(By.name("title")).sendKeys("Titolo");
        driver.findElement(By.name("text")).sendKeys("Testo");
        driver.findElement(By.id("btn")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti AI",message);
        //create second message
        driver.findElement(By.xpath("/html/body/a[6]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Send a new message to students of: Fondamenti AI",message);
        driver.findElement(By.name("title")).sendKeys("Titolo 2");
        driver.findElement(By.name("text")).sendKeys("Testo 2");
        driver.findElement(By.id("btn")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti AI",message);
        driver.findElement(By.xpath("/html/body/a[7]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Messages of: Fondamenti AI",message);
        String title = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]")).getText();
        assertEquals("Text should be 'Titolo","Titolo",title);
        String text = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]")).getText();
        assertEquals("Text should be 'Testo","Testo",text);
        driver.findElement(By.xpath("/html/body/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti AI",message);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Courses",message);
        driver.findElement(By.xpath("/html/body/a[3]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login",message);
        bTestLoginStudent(); //login con credenziali studente
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Courses",message);
        driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td[4]/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti AI",message);
        driver.findElement(By.xpath("/html/body/a[2]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Messages of: Fondamenti AI",message);
        title = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]")).getText();
        assertEquals("Text should be 'Titolo'","Titolo",title);
        text = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]")).getText();
        assertEquals("Text should be 'Testo'","Testo",text);

        //return to login
        driver.findElement(By.xpath("/html/body/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti AI",message);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Courses",message);
        driver.findElement(By.xpath("/html/body/a[5]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login",message);

        //test modify message
        aTestLoginProfessor();
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti AI",message);
        driver.findElement(By.xpath("/html/body/a[7]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Messages of: Fondamenti AI",message);
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Send a new message to students of: Fondamenti AI",message);
        driver.findElement(By.name("title")).sendKeys("Titolo modificato");
        driver.findElement(By.name("text")).sendKeys("Testo modificato");
        driver.findElement(By.id("btn")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti AI",message);
        driver.findElement(By.xpath("/html/body/a[7]")).click();
        title = driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[2]")).getText();
        assertEquals("Text should be 'Titolo modificato'","Titolo modificato",title);
        text = driver.findElement((By.xpath("/html/body/table/tbody/tr[2]/td[3]"))).getText();
        assertEquals("Text should be 'Testo modificato'","Testo modificato",text);
        driver.findElement(By.xpath("/html/body/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti AI",message);
        //return to login
        driver.findElement(By.xpath("/html/body/form/input")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Courses",message);
        driver.findElement(By.xpath("/html/body/a[3]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login",message);

        //test delete message
        aTestLoginProfessor();
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[4]/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti AI",message);
        driver.findElement(By.xpath("/html/body/a[7]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Messages of: Fondamenti AI",message);
        driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[5]/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti AI",message);
        driver.findElement(By.xpath("/html/body/a[7]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Messages of: Fondamenti AI",message);
        text = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]")).getText();
        assertEquals("Title should be 'Titolo 2'","Titolo 2",text);
        title = driver.findElement(By.xpath("/html/body/table/tbody/tr/td[3]")).getText();
        assertEquals("Text should be 'Testo 2'","Testo 2",title);
        //return to login
        driver.findElement(By.xpath("/html/body/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Fondamenti AI",message);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Courses",message);
        driver.findElement(By.xpath("/html/body/a[3]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login",message);

    }

    @Test
    public void testAddNotice(){
        bTestLoginStudent();
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
        //Test back button
        driver.findElement(By.xpath("/html/body/a")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Create a new notice",message);
        //return to login
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
    public void testElection(){
        //add candidate from election manager
        driver.get("http://localhost:8080/login");
        String message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login message expected", "Login", message);
        driver.findElement(By.name("userName")).sendKeys("elezioni");
        driver.findElement(By.name("password")).sendKeys("elezioni");
        driver.findElement(By.name("password")).submit();
        message = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Vote manager for student election", message);
        driver.findElement(By.xpath("/html/body/a[2]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Insert a candidate for election", message);
        driver.findElement(By.name("name")).sendKeys("Gianni");
        driver.findElement(By.name("surname")).sendKeys("Caliari");
        driver.findElement(By.name("list")).sendKeys("Lista 1");
        driver.findElement(By.id("btn")).click();
        message = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Vote manager for student election", message);
        driver.findElement(By.xpath("/html/body/a[3]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login", message);

        //verifica l'aggiunta del candidato ed effettua la votazione
        bTestLoginStudent();
        driver.findElement(By.xpath("/html/body/a[4]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Student votes", message);
        String name = driver.findElement(By.xpath("/html/body/form/table/tbody/tr[4]/td[2]")).getText();
        assertEquals("Text should be 'Gianni'","Gianni",name);
        String surname = driver.findElement(By.xpath("/html/body/form/table/tbody/tr[4]/td[3]")).getText();
        assertEquals("Text should be 'Caliari'","Caliari",surname);
        String list = driver.findElement(By.xpath("/html/body/form/table/tbody/tr[4]/td[1]")).getText();
        assertEquals("Text should be 'Lista 1'","Lista 1",list);
        driver.findElement(By.xpath("/html/body/form/table/tbody/tr[4]/td[4]/input")).click();
        driver.findElement(By.id("btn")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Courses", message);
        driver.findElement(By.xpath("/html/body/a[5]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login", message);

        //verifica l'avvenuta votazione
        driver.findElement(By.name("userName")).sendKeys("elezioni");
        driver.findElement(By.name("password")).sendKeys("elezioni");
        driver.findElement(By.name("password")).submit();
        message = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Vote manager for student election", message);
        driver.findElement(By.xpath("/html/body/a[1]")).click();
        message = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Result of election", message);
        String vote = driver.findElement(By.xpath("/html/body/table/tbody/tr[4]/td[4]")).getText();
        assertEquals("Text should be '1'","1",vote);
        driver.findElement(By.xpath("/html/body/form/input")).click();
        message = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Vote manager for student election", message);
        driver.findElement(By.xpath("/html/body/a[3]")).click();
        message = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Login", message);
    }
}
