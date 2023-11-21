package com.Padhantueducation.view_section.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.Padhantueducation.CommonClasses.VolleySingleton;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.models.GetProfileResult;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.Login.LoginSection.LoginActivity;
import com.Padhantueducation.view_section.profile.ChangePassword;
import com.Padhantueducation.view_section.profile.EditUserProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.Padhantueducation.CommonClasses.Utils.Get_Device_ID;
import static com.Padhantueducation.remote.APIUrl.Logout_Api;
import static com.Padhantueducation.remote.APIUrl.PROFILE_BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
View view;
    ImageView edit_icon;
    TextView chnge_pwd,tv_name,tv_mobile,tv_email,tv_about;
    Session session;
    CircleImageView profile_image;
    String user_id;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_profile, container, false);
        session = new Session(getActivity());
        tv_name = view.findViewById(R.id.tv_name);
        tv_mobile = view.findViewById(R.id.tv_mobile);
        tv_email = view.findViewById(R.id.tv_email);
        profile_image = view.findViewById(R.id.profile_image);
        edit_icon = view.findViewById(R.id.edit_icon);
        chnge_pwd = view.findViewById(R.id.chnge_pwd);
        user_id = session.getUserId();

        get_USer_profile_api(user_id);



        edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditUserProfile.class));
            }
        });
        chnge_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePassword.class));
            }
        });





        return view;
    }



    private void get_USer_profile_api(String user_id) {
        //   showProgressDialog(UserProfile.this);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<GetProfileResult> userCall = service.Get_user_profile(user_id, Get_Device_ID(getActivity()));
        userCall.enqueue(new Callback<GetProfileResult>() {
            @Override
            public void onResponse(Call<GetProfileResult> call, Response<GetProfileResult> response) {
                if (response.body().getResult().equals("true")) {
                    Log.e("TAG", "response 33: " + new Gson().toJson(response.body()));
                    String mStrname = response.body().getData().getName();
                    String mStrimage = response.body().getData().getImage();
                    String mStremail = response.body().getData().getEmail();
                    String mStrMobile = response.body().getData().getMobile();
                    tv_name.setText(mStrname);
                    tv_mobile.setText(mStrMobile);
                    tv_email.setText(mStremail);
                    Glide.with(view.getContext()).load(PROFILE_BASE_URL+response.body().getData().getImage()).placeholder(R.drawable.ic_person).dontAnimate().into(profile_image);
                    //  startActivity(new Intent(UserProfile.this, MainActivity.class));
                } else {
                    if (response.body().getMsg().equals("invalid_token")) {
                        Logout_Api(session.getUserId());
                    }


                    //  alert_dialoge(getActivity(), response.body().getMsg());
                    //  alert_dialoge(response.body().getMsg());
                   //  Toast.makeText(getActivity(), "weeee" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetProfileResult> call, Throwable t) {
                // dismissProgressDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }

    private void Logout_Api(String userId) {

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
                                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                }

                                else {
                                    Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", user_id);
                    return params;
                }
            };

            VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        }




    @Override
    public void onResume() {
        get_USer_profile_api(user_id);
        super.onResume();
    }



}
