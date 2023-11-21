package com.Padhantueducation.view_section.ui.home.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.MainActi.MainActivity;
import com.Padhantueducation.view_section.ui.home.Models.GetQuestionModel;
import com.Padhantueducation.view_section.ui.home.Models.GetQuestionResult;
import com.Padhantueducation.view_section.ui.home.adapter.QuestionListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.Padhantueducation.CommonClasses.Utils.Current_Date;
import static com.Padhantueducation.CommonClasses.Utils.dismissProgressDialog;
import static com.Padhantueducation.view_section.ui.home.adapter.QuestionListAdapter.selectedAnswers;

public class LiveExamStartActivity extends AppCompatActivity {

    String id,title,mTotal_No,time_slot;
    TextView tv_header_comment,total_no_question,current_date,tv_time_slot,Submit;
    ImageView iv_back;
    CharSequence mCurrentDate;
    RecyclerView recycleview;
    Session session;
    RecyclerView recyclerView;
    QuestionListAdapter questionListAdapter;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    String timeLeftFormatted;
    private  List<GetQuestionModel> questionModels=new ArrayList<>();
    String message = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_exam_start);
        recycleview=findViewById(R.id.recycleview);





        initview();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if(getIntent()!=null){
            id =getIntent().getStringExtra("id");
            title =getIntent().getStringExtra("title");
            tv_header_comment.setText(title);
            mTotal_No=getIntent().getStringExtra("total_no_question");
            total_no_question.setText(mTotal_No);
            time_slot=getIntent().getStringExtra("time_slot");
        }
        get_Api_Exam_Start(id);

        long millisInput = Long.parseLong(time_slot) * 60000;
        setTime(millisInput);
        if (mTimerRunning) {
            pauseTimer();
        } else {
            startTimer();
        }
        /*Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < selectedAnswers.size(); i++) {
                    //   message = message + "," + (i + 1) + "=" + selectedAnswers.get(i);
                    message = message + "," + selectedAnswers.get(i);
                    message = message.startsWith(",") ? message.substring(1) : message;
                }
                exam_result_submit(message,timeLeftFormatted);

              //  Log.e("check data",message+" "+timeLeftFormatted+" "+id+"c "+Utils.Get_Device_ID(LiveExamStartActivity.this));
             //  Utils.Toast(LiveExamStartActivity.this,message+" "+timeLeftFormatted+" "+id+"c "+Utils.Get_Device_ID(LiveExamStartActivity.this));
        }
        });*/


        selectedAnswers.clear();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=selectedAnswers.toString();
                str = str.replaceAll("\\[", "").replaceAll("\\]","");
                String str1="{"+str+"}";
                Log.e("check data",str1);
                exam_result_submit(str1, timeLeftFormatted);
            }
        });





    }
    private void exam_result_submit(String message,String time_taken) {
        Utils.showProgressDialog(LiveExamStartActivity.this);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<GetQuestionResult> userCall = service.Submit_LiveExam_Result(
                session.getUserId(),id, message,time_taken,Utils.Get_Device_ID(LiveExamStartActivity.this));
        userCall.enqueue(new Callback<GetQuestionResult>() {
            @Override
            public void onResponse(Call<GetQuestionResult> call, Response<GetQuestionResult> response) {
                try{
                    if (response!=null){
                        dismissProgressDialog();
                        Log.e("live exam question",""+response.body().getData());
                        if (response.body().getResult().equals("true")){
                            Toast.makeText(LiveExamStartActivity.this, "Your Exam Has Been Submitted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LiveExamStartActivity.this,ExamShowing24HourMsgActivity.class));
                        // startActivity(new Intent(LiveExamStartActivity.this, LiveResultShowActivity.class).putExtra("exam_id",id));

                        }else {


                            if (response.body().getMsg().equals("invalid_token")) {
                                Utils.Logout_Api(session.getUserId(), LiveExamStartActivity.this);
                            }


                            Toast.makeText(LiveExamStartActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    Log.e("error_i_report", e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<GetQuestionResult> call, Throwable t) {
                dismissProgressDialog();
                Log.e("error_i_report1",t.getMessage());
            }
        });


    }
    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }
    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {

               /* for (int i = 0; i < selectedAnswers.size(); i++) {
                    //   message = message + "," + (i + 1) + "=" + selectedAnswers.get(i);
                    message = message + "," + selectedAnswers.get(i);
                    message = message.startsWith(",") ? message.substring(1) : message;
                }
                exam_result_submit(message,time_slot);*/






                String str=selectedAnswers.toString();
                str = str.replaceAll("\\[", "").replaceAll("\\]","");
                String str1="{"+str+"}";
                Log.e("check data",str1);
                exam_result_submit(str1, timeLeftFormatted);





                mTimerRunning = false;
                updateWatchInterface();
            }
        }.start();
        mTimerRunning = true;
        updateWatchInterface();
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }
    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }
    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        tv_time_slot.setText(timeLeftFormatted);
    }
    private void updateWatchInterface() {
        if (mTimerRunning) {
        } else {
            if (mTimeLeftInMillis < 1000) {
            } else {
            }
            if (mTimeLeftInMillis < mStartTimeInMillis) {
            } else {
            }
        }
    }
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.apply();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        updateCountDownText();
        updateWatchInterface();
        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }
    private void initview() {
        tv_header_comment=findViewById(R.id.tv_header_comment);
        iv_back=findViewById(R.id.iv_back);
        total_no_question=findViewById(R.id.total_no_question);
        current_date=findViewById(R.id.current_date);
        recycleview=findViewById(R.id.recycleview);
        session=new Session(LiveExamStartActivity.this);
        recyclerView=findViewById(R.id.recycleview);
        tv_time_slot=findViewById(R.id.tv_time_slot);
        mCurrentDate=Current_Date(LiveExamStartActivity.this);
        current_date.setText(mCurrentDate);
        Submit=findViewById(R.id.submit);


    }
    private void get_Api_Exam_Start(String exam_id) {
        selectedAnswers.clear();
        Utils.showProgressDialog(LiveExamStartActivity.this);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<GetQuestionResult> userCall = service.Live_exam_questions(session.getUserId(), Utils.Get_Device_ID(LiveExamStartActivity.this),exam_id);

        userCall.enqueue(new Callback<GetQuestionResult>() {
            @Override
            public void onResponse(Call<GetQuestionResult> call, Response<GetQuestionResult> response) {
                try{
                    if (response!=null){
                        Log.e("live exam ",""+response.body().getData());
                        dismissProgressDialog();
                        if (response.body().getResult().equals("true")){
                            for (int i=0;i<response.body().getData().size();i++)
                            {
                                GetQuestionModel questionModel=new GetQuestionModel();
                                questionModels.add(questionModel);
                            }
                            questionListAdapter = new QuestionListAdapter(LiveExamStartActivity.this,response.body().getData());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(LiveExamStartActivity.this);
                            recycleview.setLayoutManager(new LinearLayoutManager(LiveExamStartActivity.this, RecyclerView.VERTICAL, false));
                            //recycleview_subject.setLayoutManager(new GridLayoutManager(ChapterActivity.this,2));
                            recycleview.setAdapter(questionListAdapter);
                        }else {


                            if (response.body().getMsg().equals("invalid_token")) {
                                Utils.Logout_Api(session.getUserId(), LiveExamStartActivity.this);
                            }


                            Toast.makeText(LiveExamStartActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }catch (Exception e){
                    Log.e("error_i_report", e.getMessage());
                }
                //  progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetQuestionResult> call, Throwable t) {
                dismissProgressDialog();
                Log.e("error_i_report1",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) < 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        call_dialoge();
        return;
    }

    public void call_dialoge() {
        new AlertDialog.Builder(LiveExamStartActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Application")
                .setMessage("Are you sure you want to close this Exam?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 21) {
                            // getActivity().finishAffinity();
                            Intent i = new Intent(LiveExamStartActivity.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        } else if (Build.VERSION.SDK_INT >= 21) {
                            Intent i = new Intent(LiveExamStartActivity.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            //  getActivity().finishAndRemoveTask();
                        }
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}