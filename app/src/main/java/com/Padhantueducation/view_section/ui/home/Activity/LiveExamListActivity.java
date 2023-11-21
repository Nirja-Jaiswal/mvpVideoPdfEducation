package com.Padhantueducation.view_section.ui.home.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.ui.home.Models.LiveExamResult;
import com.Padhantueducation.view_section.ui.home.adapter.LiveExamAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveExamListActivity extends AppCompatActivity {
ImageView iv_back;
RecyclerView recycleview_live;
Session session;
LiveExamAdapter liveExamResultAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_exam_list);
        iv_back=findViewById(R.id.iv_back);
        recycleview_live=findViewById(R.id.recycleview_live);
        session=new Session(LiveExamListActivity.this);


        if (getIntent()!=null){
           String cource_id=getIntent().getStringExtra("cource_id");
            get_Live_Exam_List(cource_id);
        }






        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });






    }

    private void get_Live_Exam_List(String cource_id) {
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<LiveExamResult> userCall = service.Get_live_exam_list(session.getUserId(), Utils.Get_Device_ID(LiveExamListActivity.this),cource_id);
            userCall.enqueue(new Callback<LiveExamResult>() {
                @Override
                public void onResponse(Call<LiveExamResult> call, Response<LiveExamResult> response) {
                    try{
                        if (response!=null){
                            Log.e("res_i_report",""+response.body().getData());

                            if (response.body().getResult().equals("true")){
                               liveExamResultAdapter = new LiveExamAdapter(LiveExamListActivity.this,response.body().getData());
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(LiveExamListActivity.this);
                                recycleview_live.setLayoutManager(new LinearLayoutManager(LiveExamListActivity.this, RecyclerView.VERTICAL, false));
                                //recycleview_subject.setLayoutManager(new GridLayoutManager(ChapterActivity.this,2));
                                recycleview_live.setAdapter(liveExamResultAdapter);
                                liveExamResultAdapter.notifyDataSetChanged();
                            }else {
                                if (response.body().getMsg().equals("invalid_token")) {
                                    Utils.Logout_Api(session.getUserId(), LiveExamListActivity.this); }
                                Toast.makeText(LiveExamListActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (Exception e){
                        Log.e("error_i_report", e.getMessage());
                    }
                    //  progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<LiveExamResult> call, Throwable t) {
                    // progressDialog.dismiss();
                    Log.e("error_i_report1",t.getMessage());
                    //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}