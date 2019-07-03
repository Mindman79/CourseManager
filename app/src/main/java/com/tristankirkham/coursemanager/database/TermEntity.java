package com.tristankirkham.coursemanager.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "term")
public class TermEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Date startDate;
    private Date endDate;
    //private SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");

    //Create term, assign values individually
    @Ignore
    public TermEntity() {
    }


    //Create new term
    public TermEntity(int id, String title, Date startDate, Date endDate) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

//    public SimpleDateFormat getDateFormatter() {
//        return dateFormatter;
//    }

   /* public void setDateFormatter(SimpleDateFormat dateFormatter) {
        this.dateFormatter = dateFormatter;
    }*/


    @Override
    public String toString() {
        return "TermEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
//                ", dateFormatter=" + dateFormatter +
                '}';
    }
}
