package com.tristankirkham.coursemanager.model;

import java.text.SimpleDateFormat;

public class AssessmentEntity {

    private int id;
    private String name;
    private String type;
    private String date;
    private int courseId;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");



    //Set assessment items individually
    public AssessmentEntity() {
    }

    //Create new assessment
    public AssessmentEntity(int id, String name, String type, String date, int courseId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.courseId = courseId;
    }

    //Edit existing assessment
    public AssessmentEntity(String name, String type, String date) {
        this.name = name;
        this.type = type;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", courseId=" + courseId +
                '}';
    }
}




