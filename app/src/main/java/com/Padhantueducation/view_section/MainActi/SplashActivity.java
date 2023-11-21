package com.Padhantueducation.view_section.MainActi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.Padhantueducation.CommonClasses.Config;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.PrefrenceManager;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.view_section.Login.LoginSection.LoginActivity;

import static com.Padhantueducation.CommonClasses.Utils.Get_Device_ID;

public class SplashActivity extends AppCompatActivity {
Session session;
Context mContext;
    public static String regId;
    PrefrenceManager prefrenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        session = new Session(this);
        mContext = this;
        Log.e("device_id",Get_Device_ID(SplashActivity.this));


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken", newToken);

                prefrenceManager=new PrefrenceManager(SplashActivity.this);

                prefrenceManager.setTokenId(mContext,newToken);

                putFirebaseRegId(newToken);
                setPreference(mContext,"regId",newToken);
                getPreference(mContext, "regId");


                displayFirebaseRegId();



            }
        });

        Log.e("checl fb id",getPreference(mContext, "regId"));

        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(1*1000);

                    if (session.isLoggedIn()) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }else {
                        Intent i=new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }

                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }









    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);
        Log.e("check splash", " : " + regId);
    }

    private void putFirebaseRegId(String newToken) {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString("regId", newToken);
        editor.commit();
    }

    public static boolean setPreference(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getPreference(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(Config.SHARED_PREF, Context.MODE_PRIVATE);
        String value=settings.getString(key,"default value");
        Log.e("get fb key ++",value);
        return settings.getString(key, "defaultValue");
    }









}


