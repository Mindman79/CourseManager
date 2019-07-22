package com.tristankirkham.coursemanager.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessment")
public class AssessmentEntity {

    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    private String assessmentName;
    private int assessmentType;
    private Date assessmentDate;
    private int courseId;






    @Ignore
    public AssessmentEntity() {
    }

    public AssessmentEntity(int assessmentId, String assessmentName, int assessmentType, Date assessmentDate, int courseId) {
        this.assessmentId = assessmentId;
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentDate = assessmentDate;
        this.courseId = courseId;
    }


    @Ignore
    public AssessmentEntity(String assessmentName, int assessmentType, Date assessmentDate) {
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentDate = assessmentDate;
    }


    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public int getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(int assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Date getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(Date assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "assessmentId=" + assessmentId +
                ", assessmentName='" + assessmentName + '\'' +
                ", assessmentType=" + assessmentType +
                ", assessmentDate=" + assessmentDate +
                ", courseId=" + courseId +
                '}';
    }
}




