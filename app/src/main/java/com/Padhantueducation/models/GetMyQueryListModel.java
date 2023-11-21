package com.Padhantueducation.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMyQueryListModel {


    @SerializedName("time")
    @Expose
    private String time;


    @SerializedName("id")
    @Expose
    private String id;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("subject_id")
    @Expose
    private String subjectId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("class_name")
    @Expose
    private String className;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }






}
