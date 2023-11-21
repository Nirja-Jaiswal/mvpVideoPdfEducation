package com.Padhantueducation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("data")
    @Expose
    private SignupModel data;

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("OTP")
    @Expose
    private Integer oTP;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public SignupModel getData() {
        return data;
    }

    public void setData(SignupModel data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOTP() {
        return oTP;
    }

    public void setOTP(Integer oTP) {
        this.oTP = oTP;
    }
}
