package com.tristankirkham.coursemanager.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "term")
public class TermEntity {

    @PrimaryKey(autoGenerate = true)
    private int term_id;
    private String title;
    private Date startDate;
    private Date endDate;


    //Create term, assign values individually
    @Ignore
    public TermEntity() {
    }


    //Create new term
    public TermEntity(int term_id, String title, Date startDate, Date endDate) {
        this.term_id = term_id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //Edit existing term
    @Ignore
    public TermEntity(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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



    @Override
    public String toString() {
        return "TermEntity{" +
                "term_id=" + term_id +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }





}
