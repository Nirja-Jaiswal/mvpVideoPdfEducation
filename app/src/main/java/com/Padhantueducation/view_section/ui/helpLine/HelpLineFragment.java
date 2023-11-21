package com.Padhantueducation.view_section.ui.helpLine;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.CommonClasses.VolleySingleton;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.view_section.MainActi.MainActivity;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.Padhantueducation.remote.APIUrl.HELP_LINE;

public class HelpLineFragment extends Fragment {

    TextView et_phone,et_query;
    Button submit;
    Session session;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_qanda, container, false);

        et_query =root.findViewById(R.id.et_query);
        et_phone=root.findViewById(R.id.et_phone);
        submit=root.findViewById(R.id.submit);
        session=new Session(getActivity());


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_phone.getText().toString().equals("")){
                    et_phone.setError("Please Enter Phone Number");
                    return;
                }else if (et_query.getText().toString().equals("")){
                    et_query.setError("Please Enter Your Query");
                    return;
                }else{

                    Submit_Api(et_phone.getText().toString(),et_query.getText().toString());

                }


            }
        });







       /* viewPager = (ViewPager) root.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getActivity(), getFragmentManager());
        adapter.addFragment(new DiscussionForumFragment(), "Discussion Forum");
        adapter.addFragment(new MyQuestionFragment(), "My Question");
        adapter.addFragment(new MyAnswerFragment(), "My Answer");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
*/

        return root;
    }

    private void Submit_Api(final String Phone, final String et_Quesry) {
            Utils.showProgressDialog(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HELP_LINE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Utils.dismissProgressDialog();
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("result");
                                String msg = obj.getString("msg");
                                if (result.equalsIgnoreCase("true")) {
                                    alert_dialoge(getActivity(),msg);
                                  //  Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                }else {

                                    if (msg.equals("invalid_token")) {
                                        Utils.Logout_Api(session.getUserId(), getActivity());
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Utils.dismissProgressDialog();
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", session.getUserId());
                    params.put("contact_no", Phone);
                    params.put("query", et_Quesry);
                    params.put("device_token", Utils.Get_Device_ID(getActivity()));
                    return params;
                }
            };
            VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
        }





    public  void alert_dialoge(Context context, String msg) {
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

                startActivity(new Intent(getActivity(), MainActivity.class));


                dialog.dismiss();
            }
        });

        dialog.show();
    }














}