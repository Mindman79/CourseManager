package com.tristankirkham.coursemanager.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "course")


/*@Entity(tableName = "course", foreignKeys = @ForeignKey(entity = TermEntity.class, onDelete = CASCADE, parentColumns = "term_id", childColumns = "course_id"))*/


public class CourseEntity {

    @PrimaryKey(autoGenerate = true)

    private int course_id;
    private String courseName;
    private Date startDate;
    private Date endDate;
    private int status;
    private String mentorName;
    private String mentorPhone;
    private String mentorEmail;
    private String termTitle;


    //Create new course
    public CourseEntity(int course_id, String courseName, Date startDate, Date endDate, int status, String mentorName, String mentorPhone, String mentorEmail, String termTitle) {
        this.course_id = course_id;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.termTitle = termTitle;
    }

    //Edit existing course
    @Ignore
    public CourseEntity(String courseName, Date startDate, Date endDate, int status, String mentorName, String mentorPhone, String mentorEmail, String termTitle) {
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.termTitle = termTitle;
    }




    //Set course items individually
    @Ignore
    public CourseEntity() {
    }


    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + course_id +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status=" + status +
                ", mentorName='" + mentorName + '\'' +
                ", mentorPhone='" + mentorPhone + '\'' +
                ", mentorEmail='" + mentorEmail + '\'' +
                ", termTitle=" + termTitle +
                '}';
    }





}
