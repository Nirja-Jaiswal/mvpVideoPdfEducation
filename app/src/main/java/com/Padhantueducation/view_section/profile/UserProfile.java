package com.Padhantueducation.view_section.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.models.GetProfileResult;
import com.Padhantueducation.remote.ApiClient;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.Padhantueducation.CommonClasses.Utils.Get_Device_ID;
import static com.Padhantueducation.CommonClasses.Utils.alert_dialoge;
import static com.Padhantueducation.remote.APIUrl.PROFILE_BASE_URL;

public class UserProfile extends AppCompatActivity {


    ImageView edit_icon;
    TextView chnge_pwd, tv_name, tv_mobile, tv_email, tv_about;
    Session session;
    CircleImageView profile_image;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        session = new Session(UserProfile.this);
        tv_name = findViewById(R.id.tv_name);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_email = findViewById(R.id.tv_email);
        profile_image = findViewById(R.id.profile_image);


        user_id = session.getUserId();

        get_USer_profile_api(user_id);

        // Toast(UserProfile.this,user_id);


        edit_icon = findViewById(R.id.edit_icon);
        chnge_pwd = findViewById(R.id.chnge_pwd);
        edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, EditUserProfile.class));
            }
        });
        chnge_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this, ChangePassword.class));
            }
        });

    }

    private void get_USer_profile_api(String user_id) {
        //   showProgressDialog(UserProfile.this);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<GetProfileResult> userCall = service.Get_user_profile(user_id, Get_Device_ID(UserProfile.this));
        userCall.enqueue(new Callback<GetProfileResult>() {
            @Override
            public void onResponse(Call<GetProfileResult> call, Response<GetProfileResult> response) {
                //   dismissProgressDialog();
                // Log.e("onResponse", "" + response.body().getData().toString());
                if (response.body().getResult().equals("true")) {
                    Log.e("TAG", "response 33: " + new Gson().toJson(response.body()));

                    String mStrname = response.body().getData().getName();
                    String mStrimage = response.body().getData().getImage();
                    String mStremail = response.body().getData().getEmail();
                    String mStrMobile = response.body().getData().getMobile();


                    tv_name.setText(mStrname);
                    tv_mobile.setText(mStrMobile);
                    tv_email.setText(mStremail);

                    Glide.with(UserProfile.this)
                            .load(PROFILE_BASE_URL + response.body().getData().getImage())
                            .placeholder(R.drawable.ic_person)
                            .error(R.drawable.ic_person)
                            .into(profile_image);


                    //  startActivity(new Intent(UserProfile.this, MainActivity.class));

                } else {
                    if (response.body().getMsg().equals("invalid_token")) {
                        Utils.Logout_Api(session.getUserId(), UserProfile.this);
                    }
                    alert_dialoge(UserProfile.this, response.body().getMsg());
                    //  alert_dialoge(response.body().getMsg());
                    // Toast.makeText(LoginActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProfileResult> call, Throwable t) {
                // dismissProgressDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }


    @Override
    protected void onResume() {
        get_USer_profile_api(user_id);
        super.onResume();
    }
}
