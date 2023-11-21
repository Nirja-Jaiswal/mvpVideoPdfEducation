package com.Padhantueducation.view_section.profile;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.models.GetProfileResult;
import com.Padhantueducation.remote.APIUrl;
import com.Padhantueducation.remote.ApiClient;
import com.rxandroidnetworking.RxAndroidNetworking;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONObject;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.Padhantueducation.CommonClasses.Utils.Get_Device_ID;
import static com.Padhantueducation.CommonClasses.Utils.alert_dialoge;
import static com.Padhantueducation.CommonClasses.Utils.bitmapToFile;
import static com.Padhantueducation.CommonClasses.Utils_common.dismissProgressDialog;
import static com.Padhantueducation.CommonClasses.Utils_common.showProgressDialog;
import static com.Padhantueducation.remote.APIUrl.PROFILE_BASE_URL;
import static com.Padhantueducation.remote.APIUrl.UPDATE_PROFILE;


import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditUserProfile extends AppCompatActivity {
CircleImageView profile_image;
EditText et_name,et_mobile,et_email;
Session session;
RelativeLayout takepic;
LinearLayout place_order;
    String user_id;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_user_profile);

        takepic=findViewById(R.id.takepic);

    et_name=findViewById(R.id.et_name);
    et_email=findViewById(R.id.et_email);
    et_mobile=findViewById(R.id.et_phone);
    profile_image=findViewById(R.id.profile_image);
        place_order=findViewById(R.id.place_order);

    session=new Session(EditUserProfile.this);
     user_id=session.getUserId();
        get_USer_profile_api(user_id);



        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              String mName=  et_name.getText().toString();
               String mStrMobile= et_mobile.getText().toString();
                String mStremail =et_email.getText().toString();
               UPdate_Profile_Api(mStrMobile,mStremail,mName);
            }
        });

        takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            profile_image.setImageBitmap(r.getBitmap());
                            Log.e("Imagepath", r.getPath());

                            file = bitmapToFile(EditUserProfile.this, r.getBitmap());
                            Log.e("imgFile", "" + file);
                            String filename = file.getName();
                            Log.e("filweName = ", filename);



                        } else {
                            //TODO: do what you have to do with r.getError();
                            Toast.makeText(EditUserProfile.this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }).show((FragmentActivity) EditUserProfile.this);



            }
        });


    }

    private void UPdate_Profile_Api(String mStrMobile, String mStremail, String mName) {
         showProgressDialog(EditUserProfile.this);
            RxAndroidNetworking.upload(APIUrl.BASE_URL +UPDATE_PROFILE)
                    .addMultipartParameter("user_id", user_id)
                    .addMultipartParameter("device_token", Utils.Get_Device_ID(EditUserProfile.this))
                    .addMultipartParameter("mobile", mStrMobile)
                    .addMultipartParameter("name", mName)
                    .addMultipartFile("image", file)

                    .build()
                    .getJSONObjectObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new  Observer<JSONObject>() {
                        @Override
                        public void onCompleted() {
                            // do anything onComplete
                            dismissProgressDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            dismissProgressDialog();
                            // handle error
                           Toast.makeText(EditUserProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            Log.e("rx_error", e.getMessage());
                        }


                        @Override
                        public void onNext(JSONObject response) {
                            //do anything with response
                            try {
                                dismissProgressDialog();;
                                Log.e("response check ", response.toString());
                                String res = response.getString("result");
                                String msg = response.getString("msg");

                                if (res.equals("true")) {
                                 Toast.makeText(EditUserProfile.this, "Your Profile Has Been Updated Successfully", Toast.LENGTH_SHORT).show();

                                 onBackPressed();
                                } else {
                                    if (msg.equals("invalid_token")) {
                                        Utils.Logout_Api(session.getUserId(), EditUserProfile.this);
                                    }
                                    System.out.println("check msg " + msg);
                                    Toast.makeText(EditUserProfile.this, " " + msg, Toast.LENGTH_SHORT).show();
                                }



                            } catch (Exception e) {
                                dismissProgressDialog();
                                Toast.makeText(EditUserProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("rx_error", e.getMessage());
                            }

                        }
                    });
        }





    private void get_USer_profile_api(String user_id) {
        showProgressDialog(EditUserProfile.this);
        APIService service = ApiClient.getClient().create(APIService.class);



        Call<GetProfileResult> userCall = service.Get_user_profile(user_id,Get_Device_ID(EditUserProfile.this));

        userCall.enqueue(new Callback<GetProfileResult>() {
            @Override
            public void onResponse(Call<GetProfileResult> call, Response<GetProfileResult> response) {
                dismissProgressDialog();

                // Log.e("onResponse", "" + response.body().getData().toString());


                if(response.body().getResult().equals("true")) {
                    Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );

                    String mStrname=response.body().getData().getName();
                    String mStrimage=response.body().getData().getImage();
                    String mStremail=response.body().getData().getEmail();
                    String mStrMobile=response.body().getData().getMobile();


                    et_name.setText(mStrname);
                    et_mobile.setText(mStrMobile);
                    et_email.setText(mStremail);

                  //  Glide.with(EditUserProfile.this).load(PROFILE_BASE_URL + response.body().getData().getImage()).into(profile_image);

                  //  Glide.with(EditUserProfile.this).load("http://logicalsofttech.com/olavo/assets/uploaded/users/dummy-profile.png").into(profile_image);
                /*    Glide.with(EditUserProfile.this).load("http://via.placeholder.com/300.png")
                            .thumbnail(0.5f)
                            .placeholder(R.drawable.ic_person)
                            .error(R.drawable.ic_attachment)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(profile_image);*/
                   // Glide.with(EditUserProfile.this).load(PROFILE_BASE_URL + response.body().getData().getImage()).placeholder(R.drawable.ic_person).fitCenter().into(profile_image);

                    Glide.with(EditUserProfile.this).load(PROFILE_BASE_URL + response.body().getData().getImage()).placeholder(R.drawable.ic_person).dontAnimate().into(profile_image);

                  //  Glide.with(EditUserProfile.this).load("http://via.placeholder.com/300.png").into(profile_image);

                    Log.e("image",PROFILE_BASE_URL + response.body().getData().getImage());

                }else {
                    alert_dialoge(EditUserProfile.this,response.body().getMsg());
                    //  alert_dialoge(response.body().getMsg());
                    // Toast.makeText(LoginActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProfileResult> call, Throwable t) {
                dismissProgressDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }
}



