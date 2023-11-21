package com.Padhantueducation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdminChatModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("doubt_ques_id")
    @Expose
    private String doubtQuesId;
    @SerializedName("a_msg")
    @Expose
    private String aMsg;
    @SerializedName("u_msg")
    @Expose
    private String uMsg;
    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("a_time")
    @Expose
    private String a_time;


    public String getaMsg() {
        return aMsg;
    }

    public void setaMsg(String aMsg) {
        this.aMsg = aMsg;
    }

    public String getuMsg() {
        return uMsg;
    }

    public void setuMsg(String uMsg) {
        this.uMsg = uMsg;
    }

    public String getA_time() {
        return a_time;
    }

    public void setA_time(String a_time) {
        this.a_time = a_time;
    }

    public AdminChatModel(String id, String doubtQuesId, String aMsg, String uMsg, String date, String time,String a_time) {
        this.id = id;
        this.doubtQuesId = doubtQuesId;
        this.aMsg = aMsg;
        this.uMsg = uMsg;
        this.date = date;
        this.time = time;
        this.a_time=a_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoubtQuesId() {
        return doubtQuesId;
    }

    public void setDoubtQuesId(String doubtQuesId) {
        this.doubtQuesId = doubtQuesId;
    }

    public String getAMsg() {
        return aMsg;
    }

    public void setAMsg(String aMsg) {
        this.aMsg = aMsg;
    }

    public String getUMsg() {
        return uMsg;
    }

    public void setUMsg(String uMsg) {
        this.uMsg = uMsg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }








}
