package com.Padhantueducation.view_section.ui.home.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChapterModel  {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("sub_chapter")
    @Expose
    private String sub_chapter;

    @SerializedName("chapter_img")
    @Expose
    private String chapter_img;

    @SerializedName("subject_name")
    @Expose
    private String subject_name;



    @SerializedName("type")
    @Expose
    private String type;






    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @SerializedName("course_id")
    @Expose
    private String course_id;


    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSub_chapter() {
        return sub_chapter;
    }

    public void setSub_chapter(String sub_chapter) {
        this.sub_chapter = sub_chapter;
    }

    public String getChapter_img() {
        return chapter_img;
    }

    public void setChapter_img(String chapter_img) {
        this.chapter_img = chapter_img;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }
}
