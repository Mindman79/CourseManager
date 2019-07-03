package com.tristankirkham.coursemanager.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "course")

public class CourseEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date startDate;
    private Date endDate;
    private int status;
    private String mentorName;
    private String mentorPhone;
    private String mentorEmail;
    private int termID;


    //Create new course
    public CourseEntity(int id, Date startDate, Date endDate, int status, String mentorName, String mentorPhone, String mentorEmail, int termID) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.termID = termID;
    }


    //Edit existing course
    @Ignore
    public CourseEntity(Date startDate, Date endDate, int status, String mentorName, String mentorPhone, String mentorEmail) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
    }

    //Set course items individually
    @Ignore
    public CourseEntity() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }


    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + id +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status=" + status +
                ", mentorName='" + mentorName + '\'' +
                ", mentorPhone='" + mentorPhone + '\'' +
                ", mentorEmail='" + mentorEmail + '\'' +
                ", termID=" + termID +
                '}';
    }
}
