package com.Padhantueducation.view_section.Login.LoginSection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.PrefrenceManager;
import com.Padhantueducation.Session.Session;

import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.models.OtpResult;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.MainActi.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.Padhantueducation.CommonClasses.Utils.alert_dialoge;
import static com.Padhantueducation.CommonClasses.Utils_common.dismissProgressDialog;
import static com.Padhantueducation.CommonClasses.Utils_common.showProgressDialog;

public class LoginActivity extends AppCompatActivity {


    EditText email,password;
    Button signInButton;
    TextView signup,forget_pwd;
    Session session;
    PrefrenceManager prefrenceManager;
    String FCM_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_login);

        session=new Session(LoginActivity.this);
        signInButton=findViewById(R.id.signInButton);
        forget_pwd=findViewById(R.id.forget_pwd);
        signup=findViewById(R.id.signup);
        email=findViewById(R.id.email);
        password=findViewById(R.id.passwordEditText);
        prefrenceManager=new PrefrenceManager(LoginActivity.this);
        FCM_ID=  prefrenceManager.getTokenId(LoginActivity.this);
        System.out.println("fcm id  "+FCM_ID);


        forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));

            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().equals(""))
                {
                    email.setError("Please Enter Email");
                    return;
                }else if(password.getText().toString().equals(""))
                {
                    password.setError("Please Enter Password");
                    return;

                }else {



                    Log.e("check data",email.getText().toString()+" "+password.getText().toString()+" "+
                                    Utils.Get_Device_ID(LoginActivity.this)+" "+FCM_ID

                            );

                    login_api(email.getText().toString(),password.getText().toString());

                }




            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));

            }
        });


    }

    private void login_api(String email, String password) {
            showProgressDialog(LoginActivity.this);
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<OtpResult> userCall = service.LoginUser(email, password, Utils.Get_Device_ID(LoginActivity.this),FCM_ID);

            userCall.enqueue(new Callback<OtpResult>() {
                @Override
                public void onResponse(Call<OtpResult> call, Response<OtpResult> response) {
                    dismissProgressDialog();

                   // Log.e("onResponse", "" + response.body().getData().toString());


                    if(response.body().getResult().equals("true")) {
                        Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
                      //  SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getData());

                        session.setLogin(true);
                        session.setUserId(response.body().getData().getId());


                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else {
                        alert_dialoge(LoginActivity.this,response.body().getMsg());
                      //  alert_dialoge(response.body().getMsg());
                       // Toast.makeText(LoginActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OtpResult> call, Throwable t) {
                    dismissProgressDialog();
                    Log.d("onFailure", t.toString());
                }
            });
        }
/*

    private void alert_dialoge(String msg) {

        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_msg_alert);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        Button ok_btn = (Button) dialog.findViewById(R.id.dialogButtonOK);

        TextView dynamic_msg = (TextView) dialog.findViewById(R.id.dynamic_msg);
        dynamic_msg.setText(msg);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                // Delete_Product_data();
            }
        });

        dialog.show();
    }
*/


    }


