package com.Padhantueducation.view_section.ui.other_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.ui.home.Models.RankResult;
import com.Padhantueducation.view_section.ui.home.adapter.RankAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankActivity extends AppCompatActivity {
    RecyclerView recyclerView;
String exam_id;
Session session;
RankAdapter rankAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        recyclerView = findViewById(R.id.recycleview);
        session=new Session(RankActivity.this);

        if (getIntent()!=null){
            exam_id=getIntent().getStringExtra("exam_id");

        }

        get_Rank_Result(exam_id);


    }

    private void get_Rank_Result(String exam_id) {

            APIService service = ApiClient.getClient().create(APIService.class);
            Call<RankResult> userCall = service.AllStudentRank(session.getUserId(), Utils.Get_Device_ID(RankActivity.this),exam_id);

            userCall.enqueue(new Callback<RankResult>() {
                @Override
                public void onResponse(Call<RankResult> call, Response<RankResult> response) {

                    try{
                        if (response!=null){
                            Log.e("res_i_report",""+response.body().getData());

                            if (response.body().getResult().equals("true")){
                                // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();

                                rankAdapter = new RankAdapter(RankActivity.this,response.body().getData());
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(RankActivity.this);
                                recyclerView.setLayoutManager(new LinearLayoutManager(RankActivity.this, RecyclerView.VERTICAL, false));
                                //     recycleview_subject.setLayoutManager(new GridLayoutManager(ChapterActivity.this,2));
                                recyclerView.setAdapter(rankAdapter);
                                rankAdapter.notifyDataSetChanged();
                            }else {

                                if (response.body().getMsg().equals("invalid_token")) {
                                    Utils.Logout_Api(session.getUserId(), RankActivity.this);
                                }
                                Toast.makeText(RankActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (Exception e){
                        Log.e("error_i_report", e.getMessage());
                    }
                    //  progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<RankResult> call, Throwable t) {
                    // progressDialog.dismiss();
                    Log.e("error_i_report1",t.getMessage());
                    //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }




}