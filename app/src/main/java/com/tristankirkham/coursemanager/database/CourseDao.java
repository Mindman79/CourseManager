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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertCourse(CourseEntity courseEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void InsertAll(List<CourseEntity> course);

    @Query("SELECT * FROM course WHERE course_id = :course_id")
    CourseEntity getCourseById(int course_id);

    //TODO: Check on this, see if it's needed and/or correct
    @Query("SELECT * FROM course WHERE term_id = :term_id")
    CourseEntity getAssociatedTerms(int term_id);



    @Delete
    public void deleteCourse(CourseEntity courseEntity);

}
