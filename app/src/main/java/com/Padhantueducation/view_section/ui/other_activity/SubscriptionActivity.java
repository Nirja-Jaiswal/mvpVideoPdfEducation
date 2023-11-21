package com.Padhantueducation.view_section.ui.other_activity;

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
import com.Padhantueducation.models.SubscriptionListResult;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.ui.home.adapter.MemberShipAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MemberShipAdapter memberShipAdapter;
    Session session;
    ImageView iv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        recyclerView = findViewById(R.id.recycleview);
        session=new Session(SubscriptionActivity.this);
        iv_back=findViewById(R.id.iv_back);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


        get_Subscription_List();

    }

    private void get_Subscription_List() {
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<SubscriptionListResult> userCall = service.Get_course_plan(session.getUserId(), Utils.Get_Device_ID(SubscriptionActivity.this));
            userCall.enqueue(new Callback<SubscriptionListResult>() {
                @Override
                public void onResponse(Call<SubscriptionListResult> call, Response<SubscriptionListResult> response) {
                    try{
                        if (response!=null){
                            Log.e("res_i_report",""+response.body().getData());

                            if (response.body().getResult().equals("true")){
                                memberShipAdapter = new MemberShipAdapter(SubscriptionActivity.this,response.body().getData());
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(SubscriptionActivity.this);
                                recyclerView.setLayoutManager(new LinearLayoutManager(SubscriptionActivity.this, RecyclerView.VERTICAL, false));
                                //     recycleview_subject.setLayoutManager(new GridLayoutManager(ChapterActivity.this,2));
                                recyclerView.setAdapter(memberShipAdapter);
                                memberShipAdapter.notifyDataSetChanged();
                            }else {

                                if (response.body().getMsg().equals("invalid_token")) {
                                    Utils.Logout_Api(session.getUserId(), SubscriptionActivity.this);
                                }
                                Toast.makeText(SubscriptionActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (Exception e){
                        Log.e("error_i_report", e.getMessage());
                    }
                    //  progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<SubscriptionListResult> call, Throwable t) {
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
