package com.Padhantueducation.remote;

import com.Padhantueducation.models.AdminChatResult;
import com.Padhantueducation.models.ExamAnsResult;
import com.Padhantueducation.models.ForgetModelResult;
import com.Padhantueducation.models.GetMyQueryListResult;
import com.Padhantueducation.models.GetProfileResult;
import com.Padhantueducation.models.MyMembershipResult;
import com.Padhantueducation.models.OtpResult;
import com.Padhantueducation.models.SubmitQueryResult;
import com.Padhantueducation.models.SubscriptionListResult;
import com.Padhantueducation.view_section.ui.home.Models.ChapterResult;
import com.Padhantueducation.view_section.ui.home.Models.ClassResult;
import com.Padhantueducation.view_section.ui.home.Models.ExamResult;
import com.Padhantueducation.view_section.ui.home.Models.GetQuestionResult;
import com.Padhantueducation.view_section.ui.home.Models.LiveExamResult;
import com.Padhantueducation.view_section.ui.home.Models.MaterialResult;
import com.Padhantueducation.view_section.ui.home.Models.NotesResult;
import com.Padhantueducation.view_section.ui.home.Models.RankResult;
import com.Padhantueducation.view_section.ui.home.Models.Result;
import com.Padhantueducation.view_section.ui.home.Models.StudyZoneResult;
import com.Padhantueducation.view_section.ui.home.Models.SubjectResult;
import com.Padhantueducation.view_section.ui.home.Models.VideoResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIService {
    @FormUrlEncoded
    @POST("user_signup")
    Call<com.Padhantueducation.models.Result> createUser(
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_token") String device_token,
            @Field("fcm_id") String fcm_id,
            @Field("referral_code") String referral_code);

    @FormUrlEncoded
    @POST("login")
    Call<OtpResult> LoginUser(
            @Field("mobile_email") String mobile_email,
            @Field("password") String password,
            @Field("device_token") String device_token,
            @Field("fcm_id") String fcm_id);

    @FormUrlEncoded
    @POST("verify_otp")
    Call<OtpResult> VerifyOtp(
            @Field("user_id") String user_id,
            @Field("otp") String otp);

    @FormUrlEncoded
    @POST("get_user_profile")
    Call<GetProfileResult> Get_user_profile(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token);

    @FormUrlEncoded
    @POST("change_user_password")
    Call<GetProfileResult> Get_User_Change_Password(
            @Field("user_id") String user_id,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password,
            @Field("confirm_password") String confirm_password);

    @FormUrlEncoded
    @POST("forgot_password")
    Call<ForgetModelResult> Get_Forgot_password(
            @Field("email") String email);

    @FormUrlEncoded
    @POST("get_banner")
    Call<Result> Get_Banner(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token);

    @FormUrlEncoded
    @POST("get_all_course")
    Call<ClassResult> Get_ALL_CLASSES(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token);



    @FormUrlEncoded
    @POST("myCourse")
    Call<ClassResult> MY_COURSE(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token);




    @FormUrlEncoded
    @POST("get_video")
    Call<VideoResult> Get_Video(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token);

    @FormUrlEncoded
    @POST("subjects_by_courseID")
    Call<SubjectResult> Get_Subjects(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token,
            @Field("course_id") String course_id);

    @FormUrlEncoded
    @POST("chapters_by_subjectID")
    Call<ChapterResult> Get_Chapters(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token,
            @Field("subject_id") String id);

    @FormUrlEncoded
    @POST("exam_list")
    Call<ExamResult> getExamList(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token,
            @Field("chapter_id") String chapter_id);

    @FormUrlEncoded
    @POST("get_questions")
    Call<GetQuestionResult> getQuestionList(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token,
            @Field("exam_id") String chapter_id);

    @FormUrlEncoded
    @POST("chapters_pdf_doc")
    Call<MaterialResult> getMaterialList(
            @Field("user_id") String user_id,
            @Field("device_token") String chapter_id,
            @Field("chapter_id") String device_token);

    @FormUrlEncoded
    @POST("chapters_notes")
    Call<NotesResult> getNotes(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token ,
            @Field("chapter_id") String chapter_id);

    @FormUrlEncoded
    @POST("chapters_by_video")
    Call<StudyZoneResult> getVideoList(
            @Field("user_id") String user_id,
            @Field("device_token") String chapter_id,
            @Field("chapter_id") String device_token);

    @FormUrlEncoded
    @POST("submit_exam")
    Call<GetQuestionResult> Submit_Exam_Result(
            @Field("user_id") String user_id,
            @Field("exam_id") String exam_id,
            @Field("json") String marks,
            @Field("time_taken") String time_taken);

    @FormUrlEncoded
    @POST("exam_result_score")
    Call<ExamAnsResult> Exam_Result_SHow(
            @Field("user_id") String user_id,
            @Field("exam_id") String exam_id,
            @Field("device_token") String device_token
    );

    @Multipart
    @POST("doubts")
    Call<SubmitQueryResult> UploadQuery(
            @Part("user_id") RequestBody user_id,
            @Part("course_id") RequestBody course_id,
            @Part("subject_id") RequestBody subject_id,
            @Part("question") RequestBody question,
            @Part("device_token") RequestBody device_token,
            @Part MultipartBody.Part fileToUpload
    );

    @Multipart
    @POST("doubts")
    Call<SubmitQueryResult> UploadQuery2(
            @Part("user_id") RequestBody user_id,
            @Part("course_id") RequestBody course_id,
            @Part("subject_id") RequestBody subject_id,
            @Part("question") RequestBody question,
            @Part("device_token") RequestBody device_token
    );

    @FormUrlEncoded
    @POST("get_doubt_question")
    Call<GetMyQueryListResult> GetMyQuestionList(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token
    );

    @FormUrlEncoded
    @POST("doubt_chat_to_admin")
    Call<GetMyQueryListResult> Send_MSg(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token,
            @Field("doubt_ques_id") String doubt_ques_id,
            @Field("u_msg") String u_msg
    );

    @FormUrlEncoded
    @POST("get_doubt_chat")
    Call<AdminChatResult> Get_Chat(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token,
            @Field("doubt_ques_id") String doubt_ques_id
    );

    @FormUrlEncoded
    @POST("check_user_plan")
    Call<AdminChatResult> Check_Plan(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token,
            @Field("course_id") String course_id
    );

    @FormUrlEncoded
    @POST("get_course_plan")
    Call<SubscriptionListResult> Get_course_plan(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token
    );

    @FormUrlEncoded
    @POST("get_user_course_plan")
    Call<MyMembershipResult> Get_user_course_plan(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token
    );



    @FormUrlEncoded
    @POST("get_live_exam_list")
    Call<LiveExamResult> Get_live_exam_list(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token,
            @Field("course_id") String course_id);



    @FormUrlEncoded
    @POST("live_exam_questions")
    Call<GetQuestionResult> Live_exam_questions(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token,
            @Field("live_exam_id") String live_exam_id);



    @FormUrlEncoded
    @POST("live_exam_submit")
    Call<GetQuestionResult> Submit_LiveExam_Result(
            @Field("user_id") String user_id,
            @Field("live_exam_id") String exam_id,
            @Field("json") String marks,
            @Field("time_taken") String time_taken,
            @Field("device_token") String device_token
            );


    @FormUrlEncoded
    @POST("live_exam_score")
    Call<ExamAnsResult> Live_Exam_Result_SHow(
            @Field("user_id") String user_id,
            @Field("live_exam_id") String exam_id,
            @Field("device_token") String device_token
    );


    @FormUrlEncoded
    @POST("user_score_history")
    Call<LiveExamResult> User_score_history(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token
    );


    @FormUrlEncoded
    @POST("live_score_history")
    Call<RankResult> AllStudentRank(
            @Field("user_id") String user_id,
            @Field("device_token") String device_token,
            @Field("live_exam_id")String live_exam_id
    );










}
