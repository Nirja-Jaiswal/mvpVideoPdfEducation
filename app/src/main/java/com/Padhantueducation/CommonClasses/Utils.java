package com.Padhantueducation.CommonClasses;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.Layout;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.view_section.Login.LoginSection.LoginActivity;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import static com.Padhantueducation.remote.APIUrl.Logout_Api;

public class Utils {
    private static AlertDialog.Builder builder;
    private static AlertDialog alert;
    private static final int REQUEST_PHONE_CALL = 1;
    public static ProgressDialog dialog;
    public  static  File imgFile1;
    Session session;

    public static void Share_App(Context context) {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }


    public static void GET_KNOW_DEVICE_LANGUUAGE(){
        String languagename = Locale.getDefault().getDisplayLanguage();
        String country = Locale.getDefault().getCountry();
    }









    public static void Logout_Api(final String user_id, final Context context) {
        final Session session=new Session(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Logout_Api,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String result = obj.getString("result");

                            if (result.equals("true")) {
                                session.setLogin(false);
                                session.setUserId("");
                                session.setMobile("", "");
                                session.setUser_name("");
                                session.logout();
                                Intent intent=new Intent(context, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(intent);
                               /// dialog.dismiss();
                            }

                            else {
                                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println("<><><>" + e.getMessage().toString());
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }
















    public  static  CharSequence Current_Date(Context context)
    {
        Date d = new Date();
        CharSequence s  = DateFormat.format("MMMM d, yyyy ", d.getTime());

        return s;
    }



    @SuppressLint("ResourceAsColor")
    public static void alert_dialoge(Context context, String msg) {
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
                dialog.dismiss();
            }
        });
        dialog.show();
    }

public  static void Do_you_want_exit_Dialoge(final Context context)
{
    new AlertDialog.Builder(context)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Closing Application")
            .setMessage("Are you sure you want to close this App?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 21) {
                        // getActivity().finishAffinity();


                         System.exit(0);



                       /* Intent i=new Intent(Intent.ACTION_MAIN);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);*/


                    } else if (Build.VERSION.SDK_INT >= 21) {



                      /*  Intent i=new Intent(Intent.ACTION_MAIN);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);*/

                        System.exit(0);
                    }
                }

            })
            .setNegativeButton("No", null)
            .show();
}

    public static void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public static void showProgressDialog(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setTitle(R.string.app_name);
        dialog.setMessage("Loading please wait..");
        //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();

    }

    public static void Dialoge(Context context, Layout layout) {


        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //   dialog.setContentView(layout);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        dialog.getWindow().setAttributes(lp);

        // ok.setText(R.string.alert_error_msg);


        dialog.show();
    }


    public static void Choose_Image(final Context context, final ImageView imageView) {
/*
android{
 packagingOptions {
            exclude 'META-INF/rxjava.properties'
        }
//two lines for pickimage
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"

            //dependency
        ///////////////////pickimage
        implementation 'com.github.jrvansuita:PickImage:2.2.4'
        implementation 'com.nononsenseapps:filepicker:3.0.0'
        maven {
            url "https://jitpack.io"
            url 'https://maven.google.com'
        }


        in menifest
         <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.com.vansuita.pickimage.provider"
            android:exported="false"
            android:grantUriPermissions="true"
            tools:replace="android:authorities">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/picker_provider_paths" />
        </provider>

*/


        final PickImageDialog dialog = PickImageDialog.build(new PickSetup());
        dialog.setOnPickCancel(new IPickCancel() {
            @Override
            public void onCancelClick() {
                dialog.dismiss();
            }
        }).setOnPickResult(new IPickResult() {
            @Override
            public void onPickResult(PickResult r) {

                if (r.getError() == null) {
                    imageView.setImageBitmap(r.getBitmap());
                     imgFile1 = bitmapToFile(context, r.getBitmap());
                    String filename = imgFile1.getName();
                } else {
                    //TODO: do what you have to do with r.getError();
                    Toast.makeText(context, r.getError().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }).show((FragmentActivity) context);


    }

    public static void Toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }


    public static boolean LogE(Context context, String msg) {
        Log.e("MSG EE", msg);
        return true;
    }

    public static boolean LogV(Context context, String msg) {
        Log.e("MSG VV", msg);
        return true;
    }

    public  static  void logout_2Dialoge(Context context){
        final Session session=new Session(context);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout_alert);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        Button ok_btn = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button cancel_btn = (Button) dialog.findViewById(R.id.dialogButtonCancel);


        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView dynamic_msg = (TextView) dialog.findViewById(R.id.dynamic_msg);
        // dynamic_msg.setText(msg);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                session.setLogin(false);
                session.setUserId("");
                session.setMobile("", "");
                session.setUser_name("");
                session.set_role("");
                session.logout();
                dialog.dismiss();
            }
        });

        dialog.show();
    }



    //convert bitmap to file
    public static File bitmapToFile(Context mContext, Bitmap bitmap) {
        try {
            String name = System.currentTimeMillis() + ".png";
            File file = new File(mContext.getCacheDir(), name);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 60, bos);
            byte[] bArr = bos.toByteArray();
            bos.flush();
            bos.close();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bArr);
            fos.flush();
            fos.close();

            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void showOKMessageandBack(String message, final Context activity, final Class activityClass) {
        builder = new AlertDialog.Builder(activity);
        if (message.length() <= 0)
            builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alert.hide();
                hideLoader();
                Intent intent = new Intent(activity, activityClass);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
            }
        });
        alert = builder.create();
        alert.show();
    }

    public static void showLoader(Activity activity) {
        if (dialog == null) {
            dialog = new ProgressDialog(activity);
            dialog.setMessage(activity.getResources().getString(R.string.loading));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);

        }
        dialog.show();
    }


    public static void hideLoader() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }


    public  static  void LogoutDialoge(final Context context)
    {
        final Session session=new Session(context);

        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_logout_dialoge);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView ok = (TextView) dialog.findViewById(R.id.ok);
        TextView msg=dialog.findViewById(R.id.text);



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






            }
        });

        dialog.show();
    }






    public static void showAlertDialog(final Context context, String title, String message) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // setting Dialog title
        alertDialog.setTitle(title);

        // setting Dialog message
        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }


    public static String Get_Device_ID(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        return android_id;
    }


    public static void call_fun(Context context, String phn_no) {

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phn_no));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
            }
            else
            {
                context.startActivity(intent);
            }
        }
        else
        {
            context.startActivity(intent);
        }
    }






}
