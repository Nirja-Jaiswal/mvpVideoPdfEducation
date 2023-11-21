package com.Padhantueducation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminChatResult {


    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;


    @SerializedName("total_msg")
    @Expose
    private String totalMsg;

    @SerializedName("plan_status")
    @Expose
    private String plan_status;

    public String getPlan_status() {
        return plan_status;
    }

    public void setPlan_status(String plan_status) {
        this.plan_status = plan_status;
    }

    @SerializedName("data")
    @Expose
    private List<AdminChatModel> data = null;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTotalMsg() {
        return totalMsg;
    }

    public void setTotalMsg(String totalMsg) {
        this.totalMsg = totalMsg;
    }

    public List<AdminChatModel> getData() {
        return data;
    }

    public void setData(List<AdminChatModel> data) {
        this.data = data;
    }







}
