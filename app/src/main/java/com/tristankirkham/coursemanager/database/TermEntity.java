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
    private int id;
    private String title;
    private String startDate;
    private String endDate;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");

    //Create term, assign values individually
    @Ignore
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
    @Ignore
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

    public SimpleDateFormat getDateFormatter() {
        return dateFormatter;
    }

   public void setDateFormatter(SimpleDateFormat dateFormatter) {
        this.dateFormatter = dateFormatter;
    }


    @Override
    public String toString() {
        return "TermEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }


    public boolean isTermValid() {
        if (title.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            return false;
        }

        try {
            //Check date format
            Date start = dateFormatter.parse(startDate);
            Date end = dateFormatter.parse(endDate);

            //Start date must be before end date
            if (!start.before(end)) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }





}
