package com.Padhantueducation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpResult {

        @SerializedName("result")
        @Expose
        private String result;

        @SerializedName("data")
        @Expose
        private OtpVerifyModel data;

        @SerializedName("msg")
        @Expose
        private String msg;



        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public OtpVerifyModel getData() {
            return data;
        }

        public void setData(OtpVerifyModel data) {
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


    }

