package com.tristankirkham.coursemanager.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CourseDao {

    //Insert a course
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCourse(CourseEntity courseEntity);

    //Insert all courses
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void InsertAllCourses(List<CourseEntity> courses);

    //Get a course by ID
    @Query("SELECT * FROM course WHERE course_id = :course_id")
    CourseEntity getCourseById(int course_id);

    //TODO: Check on this, see if it's needed and/or correct
    @Query("SELECT * FROM course WHERE term_id = :term_id")
    LiveData<List<CourseEntity>> getAssociatedTerm(int term_id);

    //Delete a course
    @Delete
    public void deleteCourse(CourseEntity courseEntity);

    //Retrieve course by date
    @Query("SELECT * FROM course ORDER BY startDate DESC")
    LiveData<List<CourseEntity>> getAllCourses();

    //Delete all courses
    @Query("DELETE FROM course")
    int deleteAllCourses();

    //Count all courses
    @Query("SELECT COUNT(*) FROM course WHERE term_id = :term_id")
    int getCoursesCount(int term_id);






}
