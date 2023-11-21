package com.Padhantueducation.view_section.Login.LoginSection;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.models.ForgetModelResult;
import com.Padhantueducation.remote.ApiClient;

import static com.Padhantueducation.CommonClasses.Utils.alert_dialoge;
import static com.Padhantueducation.CommonClasses.Utils_common.dismissProgressDialog;
import static com.Padhantueducation.CommonClasses.Utils_common.showProgressDialog;

public class ForgetPasswordActivity extends AppCompatActivity {
Button submit;
EditText et_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        submit=findViewById(R.id.submit);
        et_email=findViewById(R.id.et_email);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!et_email.getText().toString().isEmpty()||et_email.getText().toString().equals(""))
                {
                    submit_email_api(et_email.getText().toString());
                }else {

                    Utils.Toast(ForgetPasswordActivity.this,"Please Enter Email");
                }



                //startActivity(new Intent(ForgetPasswordActivity.this,OtpActivity.class));
            }
        });


    }

    private void submit_email_api(String email) {

            showProgressDialog(ForgetPasswordActivity.this);
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<ForgetModelResult> userCall = service.Get_Forgot_password(email);
            userCall.enqueue(new Callback<ForgetModelResult>() {
                @Override
                public void onResponse(Call<ForgetModelResult> call, Response<ForgetModelResult> response) {
                    dismissProgressDialog();
                    // Log.e("onResponse", "" + response.body().getData().toString());
                    if(response.body().getResult().equals("true")) {
                        Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );

                        Utils.Toast(ForgetPasswordActivity.this,"Your Password has been Changed");
                        startActivity(new Intent(ForgetPasswordActivity.this, OtpActivity.class));

                    }else {


                        alert_dialoge(ForgetPasswordActivity.this,response.body().getMsg());
                    }
                }

                @Override
                public void onFailure(Call<ForgetModelResult> call, Throwable t) {
                    dismissProgressDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }
}
