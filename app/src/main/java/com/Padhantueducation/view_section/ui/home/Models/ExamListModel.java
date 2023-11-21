package com.Padhantueducation.view_section.ui.home.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamListModel {




    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("topic")
    @Expose
    private String title;





    @SerializedName("chapter_id")
    @Expose
    private String chapter_id;

    @SerializedName("question_type")
    @Expose
    private String question_type;

    @SerializedName("no_of_question")
    @Expose
    private String no_of_question;


    @SerializedName("time_slot")
    @Expose
    private String time_slot;


    @SerializedName("sub_chapter")
    @Expose
    private String sub_chapter;



    @SerializedName("subject_name")
    @Expose
    private String subject_name;



    @SerializedName("class_name")
    @Expose
    private String class_name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public String getNo_of_question() {
        return no_of_question;
    }

    public void setNo_of_question(String no_of_question) {
        this.no_of_question = no_of_question;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

    public String getSub_chapter() {
        return sub_chapter;
    }

    public void setSub_chapter(String sub_chapter) {
        this.sub_chapter = sub_chapter;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
