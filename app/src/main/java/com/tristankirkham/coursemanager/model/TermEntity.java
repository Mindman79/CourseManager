package com.tristankirkham.coursemanager.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TermEntity {

    private int id;
    private String title;
    private String startDate;
    private String endDate;
    //private SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");

    //Create term, assign values individually
    public TermEntity() {
    }


    //Create new term
    public TermEntity(int id, String title, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //Edit existing term
    public TermEntity(String title, String startDate, String endDate) {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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
