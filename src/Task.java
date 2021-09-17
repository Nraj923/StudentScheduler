package com.example.studentscheduler;

public class Task {

    private int id;
    private String taskName;
    private String description;
    private String type;
    private String course;
    private String dueDate;
    private String timeDue;

    public Task(int id, String taskName, String description, String type, String course, String dueDate, String timeDue) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.type = type;
        this.course = course;
        this.dueDate = dueDate;
        this.timeDue = timeDue;
    }

    @Override
    public String toString() {
        return taskName +
                "\nDue Date: " + dueDate +
                "\nTime Due: " + timeDue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTimeDue() {
        return timeDue;
    }

    public void setTimeDue(String timeDue) {
        this.timeDue = timeDue;
    }
}
