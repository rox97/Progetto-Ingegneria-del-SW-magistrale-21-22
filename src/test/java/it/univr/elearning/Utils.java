package it.univr.elearning;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Utils {

    /**
     * Generates the JSON string of the "student" object
     * @param firstName first name
     * @param lastName last name
     * @param courses list of course
     * @return the generated JSON string
     */


    public static String getStudentJson(String firstName, String lastName, List<Course> courses){
        return "{\n" +
                "    \"firstName\": \"" + firstName + "\",\n" +
                "    \"lastName\": \"" + lastName + "\",\n" +
                "    \"courses\": \"" + courses + "\",\n" +
                "}";
    }



    /**
     * Generates the JSON string of the "course" object
     * @param courseName course name
     * @param coordinatorName coordinator name
     * @param students list of students
     * @return the generated JSON string
     */

    public static String getCourseJson(String courseName, String coordinatorName, List<Student> students){
        return "{\n" +
                "    \"courseName\": \"" + courseName + "\",\n" +
                "    \"coordinatorName\": \"" + coordinatorName + "\",\n" +
                "    \"students\": \"" + students + "\",\n" +
                "}";
    }

    public static void initCoursesDatabase(){
        given().contentType("application/json").body(getCourseJson("Intelligienza Artificiale", "Farinelli", null)).when().post("/courses").then().statusCode(200);
    }

    public static void initStudentsDatabase(){

    }
}
