package com.Padhantueducation.view_section.ui.home.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialModel {



    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("chapter_pdf")
    @Expose
    private String chapter_pdf;

    @SerializedName("sub_chapter")
    @Expose
    private String sub_chapter;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChapter_pdf() {
        return chapter_pdf;
    }

    public void setChapter_pdf(String chapter_pdf) {
        this.chapter_pdf = chapter_pdf;
    }

    public String getSub_chapter() {
        return sub_chapter;
    }

    public void setSub_chapter(String sub_chapter) {
        this.sub_chapter = sub_chapter;
    }
}
