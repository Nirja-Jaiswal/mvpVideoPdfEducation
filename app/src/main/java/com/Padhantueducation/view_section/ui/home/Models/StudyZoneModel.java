package com.Padhantueducation.view_section.ui.home.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudyZoneModel {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("chapter_video")
    @Expose
    private String chapter_video;


    @SerializedName("chapter_banner")
    @Expose
    private String chapter_banner;


    @SerializedName("sub_chapter")
    @Expose
    private String sub_chapter;


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

    public String getChapter_video() {
        return chapter_video;
    }

    public void setChapter_video(String chapter_video) {
        this.chapter_video = chapter_video;
    }

    public String getChapter_banner() {
        return chapter_banner;
    }

    public void setChapter_banner(String chapter_banner) {
        this.chapter_banner = chapter_banner;
    }

    public String getSub_chapter() {
        return sub_chapter;
    }

    public void setSub_chapter(String sub_chapter) {
        this.sub_chapter = sub_chapter;
    }
}
