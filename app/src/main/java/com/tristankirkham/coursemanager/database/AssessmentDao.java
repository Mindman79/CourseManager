package com.tristankirkham.coursemanager.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AssessmentDao {

    //Insert an assessment
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAssessment(AssessmentEntity assessmentEntity);

    //Insert all assessments
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    public void insertAllAssessments(List<AssessmentEntity> assessments);

    @Query("SELECT * FROM assessment WHERE assessmentId = :assessmentId")
    AssessmentEntity getAssessmentById(int assessmentId);

    //Retrieve assessment by date
    @Query("SELECT * FROM assessment ORDER BY assessmentDate DESC")
    LiveData<List<AssessmentEntity>> getAllAssessments();


    @Delete
    void deleteAssessment(AssessmentEntity assessmentEntity);

    @Query("DELETE FROM assessment")
    int deleteAllAssessments();

    @Query("SELECT COUNT(*) FROM term")
    int getAssessmentCount();




}
