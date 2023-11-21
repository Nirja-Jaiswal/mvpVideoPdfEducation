package com.Padhantueducation.view_section.ui.home.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotesModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("chapter_id")
    @Expose
    private String chapterId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("chapter_pdf1")
    @Expose
    private String chapterPdf1;
    @SerializedName("chapter_pdf2")
    @Expose
    private String chapterPdf2;
    @SerializedName("chapter_pdf3")
    @Expose
    private String chapterPdf3;
    @SerializedName("chapter_pdf4")
    @Expose
    private String chapterPdf4;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("sub_chapter")
    @Expose
    private String subChapter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getChapterPdf1() {
        return chapterPdf1;
    }

    public void setChapterPdf1(String chapterPdf1) {
        this.chapterPdf1 = chapterPdf1;
    }

    public String getChapterPdf2() {
        return chapterPdf2;
    }

    public void setChapterPdf2(String chapterPdf2) {
        this.chapterPdf2 = chapterPdf2;
    }

    public String getChapterPdf3() {
        return chapterPdf3;
    }

    public void setChapterPdf3(String chapterPdf3) {
        this.chapterPdf3 = chapterPdf3;
    }

    public String getChapterPdf4() {
        return chapterPdf4;
    }

    public void setChapterPdf4(String chapterPdf4) {
        this.chapterPdf4 = chapterPdf4;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSubChapter() {
        return subChapter;
    }

    public void setSubChapter(String subChapter) {
        this.subChapter = subChapter;
    }





}
