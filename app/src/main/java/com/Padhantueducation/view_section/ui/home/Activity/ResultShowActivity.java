package com.Padhantueducation.view_section.ui.home.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.models.ExamAnsResult;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.MainActi.MainActivity;
import com.Padhantueducation.view_section.ui.home.adapter.ResultShowAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.Padhantueducation.CommonClasses.Utils.dismissProgressDialog;

public class ResultShowActivity extends AppCompatActivity {
    TextView score, time_taken;
    RecyclerView recyclerView;
    Session session;
    String exam_id;
    ResultShowAdapter resultShowAdapter;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_show);
        score = findViewById(R.id.score);
        time_taken = findViewById(R.id.time_taken);
        recyclerView = findViewById(R.id.recycleview);
        session = new Session(ResultShowActivity.this);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        if (getIntent() != null) { exam_id = getIntent().getStringExtra("exam_id"); }
        get_Show_Result(exam_id);

    }


    private void get_Show_Result(String exam_id) {
        Utils.showProgressDialog(ResultShowActivity.this);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ExamAnsResult> userCall = service.Exam_Result_SHow(session.getUserId(),exam_id,Utils.Get_Device_ID(ResultShowActivity.this));
        userCall.enqueue(new Callback<ExamAnsResult>() {
            @Override
            public void onResponse(Call<ExamAnsResult> call, Response<ExamAnsResult> response) {

                try {
                    if (response != null) {
                        Log.e("report card", "" + response.body().getData());
                        dismissProgressDialog();
                       // Log.e("report card res  => ",new Gson().toJson(response));
                        if (response.body().getResult().equals("true")) {


                           String mtotal_question=response.body().getTotalQuestion();
                           String mcorrect_ans=response.body().getCorrectQuestion();
                           String mTimeTaken=response.body().getTimeTaken();
                           score.setText(mcorrect_ans+"/"+mtotal_question);
                           time_taken.setText(mTimeTaken);

                            resultShowAdapter = new ResultShowAdapter(ResultShowActivity.this, response.body().getData());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(ResultShowActivity.this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ResultShowActivity.this, RecyclerView.VERTICAL, false));
                            recyclerView.setAdapter(resultShowAdapter);

                        } else {

                            if (response.body().getMsg().equals("invalid_token")) {
                                Utils.Logout_Api(session.getUserId(), ResultShowActivity.this);
                            }

                            Toast.makeText(ResultShowActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("report card exce", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ExamAnsResult> call, Throwable t) {
                dismissProgressDialog();
                Log.e("result caed", t.getMessage());
            }
        });

    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
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
        Intent intent = new Intent(ResultShowActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        return;
    }







}