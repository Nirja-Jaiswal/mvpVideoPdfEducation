package com.Padhantueducation.view_section.ui.home.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.ui.home.Models.SubjectResult;
import com.Padhantueducation.view_section.ui.home.adapter.SubjectAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubjectActivity extends AppCompatActivity {
   Session session;
    String cource_id;
    RecyclerView recycleview_subject;
    SubjectAdapter subjectAdapter;
    ImageView iv_back;
    CardView card_live_exams;
    LinearLayout linear_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        recycleview_subject=findViewById(R.id.recycleview_subject);
        session=new Session(SubjectActivity.this);
        iv_back=findViewById(R.id.iv_back);
        card_live_exams=findViewById(R.id.card_live_exams);
        linear_layout=findViewById(R.id.linear_layout);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


        if (getIntent()!=null)
        {
            cource_id=getIntent().getStringExtra("cource_id");
            get_Subject(cource_id);
        }


        card_live_exams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SubjectActivity.this, LiveExamListActivity.class).putExtra("cource_id",cource_id));



            }
        });







    }

    private void get_Subject(String cource_id) {
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<SubjectResult> userCall = service.Get_Subjects(session.getUserId(), Utils.Get_Device_ID(SubjectActivity.this),cource_id);

            userCall.enqueue(new Callback<SubjectResult>() {
                @Override
                public void onResponse(Call<SubjectResult> call, Response<SubjectResult> response) {

                    try{
                        if (response!=null){
                            Log.e("res_i_report",""+response.body().getData());

                            if (response.body().getResult().equals("true")){
                                // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();
                                linear_layout.setVisibility(View.VISIBLE);
                                subjectAdapter = new SubjectAdapter(SubjectActivity.this,response.body().getData());
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(SubjectActivity.this);
                                recycleview_subject.setLayoutManager(mLayoutManger);
                                recycleview_subject.setLayoutManager(new GridLayoutManager(SubjectActivity.this,2));
                                recycleview_subject.setAdapter(subjectAdapter);
                                subjectAdapter.notifyDataSetChanged();




                            }else {



                                if (response.body().getMsg().equals("invalid_token")) {
                                    Utils.Logout_Api(session.getUserId(), SubjectActivity.this);
                                }






                                Toast.makeText(SubjectActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    }catch (Exception e){
                        Log.e("error_i_report", e.getMessage());
                    }
                    //  progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<SubjectResult> call, Throwable t) {
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