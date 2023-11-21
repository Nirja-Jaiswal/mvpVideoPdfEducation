package com.Padhantueducation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgetModelResult {


    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("msg")
    @Expose
    private String msg;



    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
