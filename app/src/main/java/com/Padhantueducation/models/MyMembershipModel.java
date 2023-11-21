package com.Padhantueducation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyMembershipModel {



    @SerializedName("plan_status")
    @Expose
    private String planStatus;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("mrp")
    @Expose
    private String mrp;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("class_name")
    @Expose
    private String className;

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }







}
