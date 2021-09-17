package com.example.studentscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public static final String COLUMN_COURSE = "COURSE";
    public static final String COURSE_TABLE = "COURSE_TABLE";
    public static final String COLUMN_COURSE_ID = "ID";
    public static final String COLUMN_COURSE_NAME = "COURSE_NAME";
    public static final String COLUMN_PROFESSOR = "PROFESSOR";
    public static final String COLUMN_COURSE_DESCRIPTION = "COURSE_DESCRIPTION";

    public static final String COLUMN_TASK_ID = "ID";
    public static final String TASK_TABLE = "TASK_TABLE";
    public static final String COLUMN_TASK_NAME = "TASK_NAME";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_TYPE = "TYPE";
    public static final String COLUMN_DUE_DATE = "DUE_DATE";
    public static final String COLUMN_TIME_DUE = "TIME_DUE";

    public Database(@Nullable Context context) {
        super(context, "schedule.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String courseTableQuery = "CREATE TABLE " + COURSE_TABLE + " (" + COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COURSE_NAME + " TEXT, " + COLUMN_PROFESSOR + " TEXT, " + COLUMN_COURSE_DESCRIPTION + " TEXT)";

        sqLiteDatabase.execSQL(courseTableQuery);

        String taskTableQuery = "CREATE TABLE " + TASK_TABLE + " (" + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TASK_NAME + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_TYPE + " TEXT, " +
                COLUMN_COURSE + " TEXT, " + COLUMN_DUE_DATE + " TEXT, " + COLUMN_TIME_DUE + " TEXT)";

        sqLiteDatabase.execSQL(taskTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_COURSE_NAME, course.getCourseName());
        cv.put(COLUMN_PROFESSOR, course.getProfessor());
        cv.put(COLUMN_COURSE_DESCRIPTION, course.getCourseDescription());

        long insert = db.insert(COURSE_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TASK_NAME, task.getTaskName());
        cv.put(COLUMN_DESCRIPTION, task.getDescription());
        cv.put(COLUMN_TYPE, task.getType());
        cv.put(COLUMN_COURSE, task.getCourse());
        cv.put(COLUMN_DUE_DATE, task.getDueDate());
        cv.put(COLUMN_TIME_DUE, task.getTimeDue());

        long insert = db.insert(TASK_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + COURSE_TABLE + " WHERE "
                + COLUMN_COURSE_ID + " = " + course.getId();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TASK_TABLE + " WHERE "
                + COLUMN_TASK_ID + " = " + task.getId();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();

        String query = "SELECT * FROM " + COURSE_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int courseID = cursor.getInt(0);
                String courseName = cursor.getString(1);
                String professor = cursor.getString(2);
                String courseDescription = cursor.getString(3);

                Course course = new Course(courseID, courseName, professor, courseDescription);
                courseList.add(course);

            } while(cursor.moveToNext());
        } else {
            // add nothing
        }
        cursor.close();
        db.close();

        return courseList;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();

        String query = "SELECT * FROM " + TASK_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int taskID = cursor.getInt(0);
                String taskName = cursor.getString(1);
                String description = cursor.getString(2);
                String type = cursor.getString(3);
                String course = cursor.getString(4);
                String dueDate = cursor.getString(5);
                String timeDue = cursor.getString(6);

                Task task = new Task(taskID, taskName, description, type, course, dueDate, timeDue);
                taskList.add(task);

            } while(cursor.moveToNext());
        } else {
            // add nothing
        }
        cursor.close();
        db.close();

        return taskList;
    }

    // Finds tasks based on specific date
    public List<Task> getTasksOnDate(String date) {
        List<Task> taskList = new ArrayList<>();

        String query = "SELECT * FROM " + TASK_TABLE + " WHERE "
                + COLUMN_DUE_DATE + " = " + "\"" + date + "\"";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int taskID = cursor.getInt(0);
                String taskName = cursor.getString(1);
                String description = cursor.getString(2);
                String type = cursor.getString(3);
                String course = cursor.getString(4);
                String dueDate = cursor.getString(5);
                String timeDue = cursor.getString(6);

                Task task = new Task(taskID, taskName, description, type, course, dueDate, timeDue);
                taskList.add(task);

            } while(cursor.moveToNext());
        } else {
            // add nothing
        }
        cursor.close();
        db.close();

        return taskList;
    }
}
