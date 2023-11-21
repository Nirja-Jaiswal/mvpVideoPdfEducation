package com.Padhantueducation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamAnsResult {






    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("total_question")
    @Expose
    private String totalQuestion;
    @SerializedName("correct_question")
    @Expose
    private String correctQuestion;
    @SerializedName("time_taken")
    @Expose
    private String timeTaken;
    @SerializedName("data")
    @Expose
    private List<ExamResultModel> data = null;

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

    public String getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(String totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public String getCorrectQuestion() {
        return correctQuestion;
    }

    public void setCorrectQuestion(String correctQuestion) {
        this.correctQuestion = correctQuestion;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public List<ExamResultModel> getData() {
        return data;
    }

    public void setData(List<ExamResultModel> data) {
        this.data = data;
    }


}
