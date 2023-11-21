package com.Padhantueducation.view_section.Login.LoginSection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.Session.PrefrenceManager;
import com.Padhantueducation.models.Result;
import com.Padhantueducation.R;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.Padhantueducation.CommonClasses.Utils.alert_dialoge;
import static com.Padhantueducation.CommonClasses.Utils_common.dismissProgressDialog;
import static com.Padhantueducation.CommonClasses.Utils_common.showProgressDialog;
public class RegistrationActivity extends AppCompatActivity {

    TextView Login;
     Button sigup;
     EditText email,number,password,referalcode,confirmpasswordEditText,et_name;
    PrefrenceManager prefrenceManager;
    String FCM_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
       sigup=findViewById(R.id.signInButton);
        Login = findViewById(R.id.Login);
        et_name=findViewById(R.id.et_name);
        email=findViewById(R.id.et_email);
        number=findViewById(R.id.edit_number);
        password=findViewById(R.id.passwordEditText);
        referalcode=findViewById(R.id.referal_code);
        confirmpasswordEditText=findViewById(R.id.confirmpasswordEditText);






        prefrenceManager=new PrefrenceManager(RegistrationActivity.this);
        FCM_ID=  prefrenceManager.getTokenId(RegistrationActivity.this);
        System.out.println("fcm id  "+FCM_ID);







        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

        sigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_name.getText().toString().equals(""))
                {
                    et_name.setError("Enter an Name");
                    et_name.requestFocus();
                }
                if (email.getText().toString().equals(""))
                {
                    email.setError("Enter an E-Mail Address");
                    email.requestFocus();
                }else if(number.getText().toString().equals(""))
                {

                    number.setError("Enter Mobile No.");
                    number.requestFocus();

                }else if (password.getText().toString().equals(""))
                {
                    password.setError("Enter Password.");
                    password.requestFocus();
                }
                else if (confirmpasswordEditText.getText().toString().equals(""))
                {
                    confirmpasswordEditText.setError("Enter confirm Password.");
                    confirmpasswordEditText.requestFocus();
                }
                else if (!password.getText().toString().equals(confirmpasswordEditText.getText().toString()))
                {
                    confirmpasswordEditText.setError("please enter same password.");
                    confirmpasswordEditText.requestFocus();
                }else {

                    userSignUp(et_name.getText().toString(),email.getText().toString(),number.getText().toString(),password.getText().toString(),referalcode.getText().toString());

                }






            }
        });





    }





    private void userSignUp(String name,String email, String number, String password, String referalCodeText) {

        showProgressDialog(RegistrationActivity.this);


        APIService service = ApiClient.getClient().create(APIService.class);
        Call<Result> userCall = service.createUser(name, number,email, password, Utils.Get_Device_ID(RegistrationActivity.this),FCM_ID,referalCodeText);

        userCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                dismissProgressDialog();
                //onSignupSuccess();
                if(response.body().getResult().equals("true")) {
                    Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );


                    String user_id=response.body().getData().getId();



                      alert_dialoge_signup(RegistrationActivity.this,response.body().getMsg(),user_id);


                }else {

                    alert_dialoge(RegistrationActivity.this,response.body().getMsg());

                   // Toast.makeText(RegistrationActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();



                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                dismissProgressDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }
    public  void alert_dialoge_signup(Context context, String msg, final String user_id) {
        final Dialog dialog = new Dialog(context);
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


             //   Toast.makeText(RegistrationActivity.this, ""+user_id, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(RegistrationActivity.this, OtpActivity.class).putExtra("user_id",user_id));
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
}








}
