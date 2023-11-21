package com.Padhantueducation.view_section.profile;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.models.GetProfileResult;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.MainActi.MainActivity;

import static com.Padhantueducation.CommonClasses.Utils.alert_dialoge;
import static com.Padhantueducation.CommonClasses.Utils_common.dismissProgressDialog;
import static com.Padhantueducation.CommonClasses.Utils_common.showProgressDialog;

public class ChangePassword extends AppCompatActivity {
ImageView iv_back;
EditText et_old_pwd,et_new_pwd,et_confirm_pwd;
TextView submit;
Session session;
String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        session=new Session(ChangePassword.this);
        user_id=session.getUserId();

        iv_back=findViewById(R.id.iv_back);
        et_old_pwd=findViewById(R.id.et_old_pwd);
        et_new_pwd=findViewById(R.id.et_new_pwd);
        et_confirm_pwd=findViewById(R.id.et_confirm_pwd);
        submit=findViewById(R.id.submit);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String old_pwd=  et_old_pwd.getText().toString();
                String new_pwd= et_new_pwd.getText().toString();
                String confiem_pwd =et_confirm_pwd.getText().toString();

                if (old_pwd.isEmpty()||old_pwd.equals(""))
                {
                   et_old_pwd.setError("Please Enter Old Password");
                    return;
                }else if (new_pwd.isEmpty()||new_pwd.equals(""))
                {
                    et_new_pwd.setError("Please Enter New Password");
                   return;
                }else if (confiem_pwd.isEmpty()||confiem_pwd.equals(""))
                {
                    et_confirm_pwd.setError("Please Enter Confirm Password");
                    return;
                }else{
                    ChangePasword(old_pwd,new_pwd,confiem_pwd);
                }
            }
        });


    }







    private void ChangePasword(String old_pwd,String new_pwd,String confirm_pwd) {
        showProgressDialog(ChangePassword.this);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<GetProfileResult> userCall = service.Get_User_Change_Password(user_id,old_pwd,new_pwd,confirm_pwd);
        userCall.enqueue(new Callback<GetProfileResult>() {
            @Override
            public void onResponse(Call<GetProfileResult> call, Response<GetProfileResult> response) {
                dismissProgressDialog();
                // Log.e("onResponse", "" + response.body().getData().toString());
                if(response.body().getResult().equals("true")) {
                    Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );

                     Utils.Toast(ChangePassword.this,"Your Password has been Changed");
                     startActivity(new Intent(ChangePassword.this, MainActivity.class));

                }else {

                    if (response.body().getMsg().equals("invalid_token")) {
                        Utils.Logout_Api(session.getUserId(), ChangePassword.this);
                    }
                    alert_dialoge(ChangePassword.this,response.body().getMsg());
                   }
            }

            @Override
            public void onFailure(Call<GetProfileResult> call, Throwable t) {
                dismissProgressDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
