package com.Padhantueducation.view_section.ui.home.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetQuestionModel {

    private boolean isSelectedA;
    private boolean isSelectedB;
    private boolean isSelectedC;
    private boolean isSelectedD;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("exam_id")
    @Expose
    private String exam_id;

    @SerializedName("live_exam_id")
    @Expose
    private String live_exam_id;

    public String getLive_exam_id() {
        return live_exam_id;
    }

    public void setLive_exam_id(String live_exam_id) {
        this.live_exam_id = live_exam_id;
    }

    @SerializedName("questions")
    @Expose
    private String questions;

    @SerializedName("question")
    @Expose
    private String question;


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @SerializedName("optionA")
    @Expose
    private String optionA;
    @SerializedName("optionB")
    @Expose
    private String optionB;
    @SerializedName("optionC")
    @Expose
    private String optionC;
    @SerializedName("optionD")
    @Expose
    private String optionD;
    @SerializedName("correct_ans")
    @Expose
    private String correct_ans;
    @SerializedName("sub_chapter")
    @Expose
    private String sub_chapter;

    @SerializedName("questionNo")
    @Expose
    private  String questionNo;





    @SerializedName("questionIMG")
    @Expose
    private  String questionIMG;


    @SerializedName("optionA_img")
    @Expose
    private  String optionA_img;

    @SerializedName("optionB_img")
    @Expose
    private  String optionB_img;


    @SerializedName("optionC_img")
    @Expose
    private  String optionC_img;


    @SerializedName("optionD_img")
    @Expose
    private  String optionD_img;


    @SerializedName("correct_ans_img")
    @Expose
    private  String correct_ans_img;


    @SerializedName("explain_img")
    @Expose
    private  String explain_img;


    public String getQuestionIMG() {
        return questionIMG;
    }

    public void setQuestionIMG(String questionIMG) {
        this.questionIMG = questionIMG;
    }

    public String getOptionA_img() {
        return optionA_img;
    }

    public void setOptionA_img(String optionA_img) {
        this.optionA_img = optionA_img;
    }

    public String getOptionB_img() {
        return optionB_img;
    }

    public void setOptionB_img(String optionB_img) {
        this.optionB_img = optionB_img;
    }

    public String getOptionC_img() {
        return optionC_img;
    }

    public void setOptionC_img(String optionC_img) {
        this.optionC_img = optionC_img;
    }

    public String getOptionD_img() {
        return optionD_img;
    }

    public void setOptionD_img(String optionD_img) {
        this.optionD_img = optionD_img;
    }

    public String getCorrect_ans_img() {
        return correct_ans_img;
    }

    public void setCorrect_ans_img(String correct_ans_img) {
        this.correct_ans_img = correct_ans_img;
    }

    public String getExplain_img() {
        return explain_img;
    }

    public void setExplain_img(String explain_img) {
        this.explain_img = explain_img;
    }

    boolean is_tick;


    public String getQuestionNo() {
        return questionNo;
    }
    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrect_ans() {
        return correct_ans;
    }

    public void setCorrect_ans(String correct_ans) {
        this.correct_ans = correct_ans;
    }

    public String getSub_chapter() {
        return sub_chapter;
    }

    public void setSub_chapter(String sub_chapter) {
        this.sub_chapter = sub_chapter;
    }

    public boolean isSelectedA() {
        return isSelectedA;
    }

    public void setSelectedA(boolean selectedA) {
        isSelectedA = selectedA;
    }

    public boolean isSelectedB() {
        return isSelectedB;
    }

    public void setSelectedB(boolean selectedB) {
        isSelectedB = selectedB;
    }

    public boolean isSelectedC() {
        return isSelectedC;
    }

    public void setSelectedC(boolean selectedC) {
        isSelectedC = selectedC;
    }

    public boolean isSelectedD() {
        return isSelectedD;
    }

    public void setSelectedD(boolean selectedD) {
        isSelectedD = selectedD;
    }

    public boolean isIs_tick() {
        return is_tick;
    }

    public void setIs_tick(boolean is_tick) {
        this.is_tick = is_tick;
    }
}
