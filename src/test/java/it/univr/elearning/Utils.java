package it.univr.elearning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Utils {


    /**
     * Generates the JSON string of the "student" object
     * @param firstName first name
     * @param lastName last name
     //* @param courses list of course
     * @return the generated JSON string
     */


    public static String getStudentJson(String firstName, String lastName){
        return "{\n" +
                "    \"firstName\": \"" + firstName + "\",\n" +
                "    \"lastName\": \"" + lastName + "\"\n" +
                "}";
    }



    /**
     * Generates the JSON string of the "course" object
     * @param courseName course name
     * @param coordinatorName coordinator name
     //* @param students list of students
     * @return the generated JSON string
     */


    public static String getCourseJson (String courseName, String coordinatorName){
        return "{\n" +
                "    \"courseName\": \"" + courseName + "\",\n" +
                "    \"coordinatorName\": \"" + coordinatorName + "\"\n" +
                "}";

    }

    public static String getProfessorJson (String professorName, String professorSurname){
        return "{\n" +
                "    \"professorName\": \"" + professorName + "\",\n" +
                "    \"professorSurname\": \"" + professorSurname + "\"\n" +
                "}";
    }

    public static String getCandidateJson (String name, String surname, String list){
        return "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"surname\": \"" + surname + "\",\n" +
                "    \"list\": \"" + list + "\"\n" +
                "}";
    }


    public static void initCoursesDatabase(){

        given().contentType("application/json").body(getCourseJson("Fondamenti di Intelligenza Artificiale", "Alessandro Farinelli")).when().post("/courses").then().statusCode(200);
        given().contentType("application/json").body(getCourseJson("Fondamenti di Ingegneria del Software", "Mariano Ceccato")).when().post("/courses").then().statusCode(200);
    }

    public static void initStudentsDatabase(){

        given().contentType("application/json").body(getStudentJson("Andrea", "Rossetti")).when().post("/students").then().statusCode(200);
        given().contentType("application/json").body(getStudentJson("Andrea", "Caliari")).when().post("/students").then().statusCode(200);
        given().contentType("application/json").body(getStudentJson("Simone", "Baldi")).when().post("/students").then().statusCode(200);
    }

    public static void initProfessorDatabase(){
        given().contentType("application/json").body(getProfessorJson("Mariano", "Ceccato")).when().post("/professor").then().statusCode(200);
        given().contentType("application/json").body(getProfessorJson("Alessandro", "Farinelli")).when().post("professor").then().statusCode(200);
        given().contentType("application/json").body(getProfessorJson("Damiano", "Carra")).when().post("/professor").then().statusCode(200);
        given().contentType("application/json").body(getProfessorJson("Enrico", "Gregorio")).when().post("/professor").then().statusCode(200);
    }

    public static void initCandidateDatabase(){

        given().contentType("application/json").body(getCandidateJson("Andrea", "Rossetti","Lista 1")).when().post("/students").then().statusCode(200);
        given().contentType("application/json").body(getCandidateJson("Andrea", "Caliari", "Lista 2")).when().post("/students").then().statusCode(200);
        given().contentType("application/json").body(getCandidateJson("Simone", "Baldi", "Lista 3")).when().post("/students").then().statusCode(200);

    }


}
