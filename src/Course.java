package com.example.studentscheduler;

public class Course {

    private int id;
    private String courseName;
    private String professor;
    private String courseDescription;

    public Course(int id, String courseName, String professor, String courseDescription) {
        this.id = id;
        this.courseName = courseName;
        this.professor = professor;
        this.courseDescription = courseDescription;
    }

    @Override
    public String toString() {
        return courseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }
}
