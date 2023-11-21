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
import com.Padhantueducation.view_section.ui.home.Models.ChapterResult;
import com.Padhantueducation.view_section.ui.home.adapter.ChapterAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterActivity extends AppCompatActivity {


    Session session;
    String cource_id;
    RecyclerView recycleview_subject;
    ImageView iv_back;
    ChapterAdapter chapterAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        recycleview_subject=findViewById(R.id.recycleview);
        session=new Session(ChapterActivity.this);
        iv_back=findViewById(R.id.iv_back);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });



        if (getIntent()!=null)
        {
            cource_id=getIntent().getStringExtra("id");


            System.out.println("id"+cource_id);
            get_Chapter(cource_id);

        }








    }




    private void get_Chapter(String cource_id) {
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ChapterResult> userCall = service.Get_Chapters(session.getUserId(), Utils.Get_Device_ID(ChapterActivity.this),cource_id);

        userCall.enqueue(new Callback<ChapterResult>() {
            @Override
            public void onResponse(Call<ChapterResult> call, Response<ChapterResult> response) {

                try{
                    if (response!=null){
                        Log.e("res_i_report",""+response.body().getData());

                        if (response.body().getResult().equals("true")){
                            // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();

                            chapterAdapter = new ChapterAdapter(ChapterActivity.this,response.body().getData());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(ChapterActivity.this);
                            recycleview_subject.setLayoutManager(new LinearLayoutManager(ChapterActivity.this, RecyclerView.VERTICAL, false));

                            //     recycleview_subject.setLayoutManager(new GridLayoutManager(ChapterActivity.this,2));
                            recycleview_subject.setAdapter(chapterAdapter);
                            chapterAdapter.notifyDataSetChanged();
                        }else {


                            if (response.body().getMsg().equals("invalid_token")) {
                                Utils.Logout_Api(session.getUserId(), ChapterActivity.this);
                            }



                            Toast.makeText(ChapterActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    Log.e("error_i_report", e.getMessage());
                }
                //  progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ChapterResult> call, Throwable t) {
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