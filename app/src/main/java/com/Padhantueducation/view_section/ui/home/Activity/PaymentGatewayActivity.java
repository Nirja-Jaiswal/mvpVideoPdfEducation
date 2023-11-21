package com.Padhantueducation.view_section.ui.home.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.Padhantueducation.remote.APIUrl.GET_Plan_FEE_SUBMIT;

public class PaymentGatewayActivity extends AppCompatActivity {
TextView mTotalprice;
LinearLayout online_payment_linear;
String mcource_id,mid;
Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        mTotalprice=findViewById(R.id.mTotalprice);
        online_payment_linear=findViewById(R.id.online_payment_linear);
         session=new Session(PaymentGatewayActivity.this);


        if(getIntent()!=null)
        {
            String mprice=getIntent().getStringExtra("total_price");
            mcource_id=getIntent().getStringExtra("cource_id");
            mid=getIntent().getStringExtra("id");
            mTotalprice.setText(mprice+" Rs");
        }


        online_payment_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                online_payment_api();
            }
        });
    }

    private void online_payment_api() {
            Utils.showProgressDialog(PaymentGatewayActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_Plan_FEE_SUBMIT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Utils.dismissProgressDialog();
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("result");
                                String msg = obj.getString("msg");
                                if (result.equalsIgnoreCase("true")) {
                                //    alert_dialoge(getActivity(),msg);
                             Toast.makeText(PaymentGatewayActivity.this, "Your Plan Successfully Activated.", Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(PaymentGatewayActivity.this, MainActivity.class));
                             finish();

                                }else {


                                    if (msg.equals("invalid_token")) {
                                        Utils.Logout_Api(session.getUserId(), PaymentGatewayActivity.this);
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
                            Toast.makeText(PaymentGatewayActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", session.getUserId());
                    params.put("course_id", mcource_id);
                    params.put("plan_id", mid);
                    params.put("device_token", Utils.Get_Device_ID(PaymentGatewayActivity.this));
                    return params;
                }
            };
            VolleySingleton.getInstance(PaymentGatewayActivity.this).addToRequestQueue(stringRequest);
        }

    }
