package com.Padhantueducation.view_section.ui.home.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudyZoneResult {


    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose

    private List<StudyZoneModel> data = null;
    @SerializedName("msg")
    @Expose

    private String msg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<StudyZoneModel> getData() {
        return data;
    }

    public void setData(List<StudyZoneModel> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }






}
