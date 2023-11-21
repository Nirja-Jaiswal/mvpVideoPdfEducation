package com.Padhantueducation.view_section.ui.home.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerModel {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("banner_img")
    @Expose
    private String banner_img;

    @SerializedName("banner_name")
    @Expose
    private String banner_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner_img() {
        return banner_img;
    }

    public void setBanner_img(String banner_img) {
        this.banner_img = banner_img;
    }

    public String getBanner_name() {
        return banner_name;
    }

    public void setBanner_name(String banner_name) {
        this.banner_name = banner_name;
    }
}
