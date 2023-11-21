package com.Padhantueducation.view_section.ui.home.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.Padhantueducation.view_section.MainActi.MainActivity;
import com.Padhantueducation.view_section.ui.home.Models.VideoResult;
import com.Padhantueducation.view_section.ui.home.adapter.VideoAdapter2;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllVideoListActivity extends AppCompatActivity {
ImageView iv_back;
RecyclerView recycler_video;
Session session;
    VideoAdapter2 videoAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_video_list);
        iv_back=findViewById(R.id.iv_back);
        recycler_video=findViewById(R.id.recycler_video);
        session=new Session(AllVideoListActivity.this);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllVideoListActivity.this, MainActivity.class));
            }
        });



        get_Video();





    }


    private void get_Video() {
        //  Utils.showProgressDialog(getActivity());
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<VideoResult> userCall = service.Get_Video(session.getUserId(), Utils.Get_Device_ID(AllVideoListActivity.this));
        userCall.enqueue(new Callback<VideoResult>() {
            @Override
            public void onResponse(Call<VideoResult> call, Response<VideoResult> response) {

                try {
                    if (response != null) {
                        //    dismissProgressDialog();
                        Log.e("home video data ", "" + response.body().getData());

                        if (response.body().getResult().equals("true")) {
                            // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();


                            videoAdapter2 = new VideoAdapter2(response.body().getData(), AllVideoListActivity.this);
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(AllVideoListActivity.this);
                            recycler_video.setLayoutManager(mLayoutManger);
                            recycler_video.setLayoutManager(new LinearLayoutManager(AllVideoListActivity.this, RecyclerView.VERTICAL, false));
                            recycler_video.setAdapter(videoAdapter2);
                            videoAdapter2.notifyDataSetChanged();




                        } else {

                            Toast.makeText(AllVideoListActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        }

                    }
                } catch (Exception e) {
                    //     dismissProgressDialog();
                    Log.e("error_i_report", e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<VideoResult> call, Throwable t) {
                //    dismissProgressDialog();
                Log.e("error_i_report1", t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}