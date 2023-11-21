package com.Padhantueducation.view_section.ui.home.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveExamModel {
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("question_type")
    @Expose
    private String questionType;
    @SerializedName("no_of_question")
    @Expose
    private String noOfQuestion;
    @SerializedName("time_slot")
    @Expose
    private String timeSlot;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("end_time")
    @Expose
    private String endTime;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("live_exam_id")
    @Expose
    private String live_exam_id;

    @SerializedName("class_name")
    @Expose
    private String class_name;

    @SerializedName("format_dateTime")
    @Expose
    private  String format_dateTime;


    public String getFormat_dateTime() {
        return format_dateTime;
    }

    public void setFormat_dateTime(String format_dateTime) {
        this.format_dateTime = format_dateTime;
    }

    public String getLive_exam_id() {
        return live_exam_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public void setLive_exam_id(String live_exam_id) {
        this.live_exam_id = live_exam_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getNoOfQuestion() {
        return noOfQuestion;
    }

    public void setNoOfQuestion(String noOfQuestion) {
        this.noOfQuestion = noOfQuestion;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
