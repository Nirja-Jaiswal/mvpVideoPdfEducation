package com.Padhantueducation.view_section.ui.doubts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.models.AdminChatModel;
import com.Padhantueducation.models.AdminChatResult;
import com.Padhantueducation.models.GetMyQueryListResult;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.ui.doubts.Adapter.AdminChatAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminChatActivity extends AppCompatActivity {
    TextView tv_header_comment;
    String id, question;
    EditText text_send;
    ImageButton ImageButton;
    ImageView iv_back;
    Session session;
    RecyclerView recycler_view;
    AdminChatAdapter adminChatAdapter;
    ArrayList<AdminChatModel> chat_modelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chat);


        tv_header_comment = findViewById(R.id.tv_header_comment);
        text_send = findViewById(R.id.text_send);
        ImageButton = findViewById(R.id.btn_send);
        recycler_view = findViewById(R.id.recycler_view);
        session = new Session(AdminChatActivity.this);
        iv_back=findViewById(R.id.iv_back);





        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            question = getIntent().getStringExtra("question");
            tv_header_comment.setText(question);
        }


        ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text_send.getText().toString().equals("")) {
                    Toast.makeText(AdminChatActivity.this, "Please Enter msg..", Toast.LENGTH_SHORT).show();
                } else {
                    send_msg_to_admin(text_send.getText().toString());
                }
            }
        });


        get_admin_chat();


    }





    private void get_admin_chat() {
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<AdminChatResult> userCall = service.Get_Chat(session.getUserId(), Utils.Get_Device_ID(AdminChatActivity.this), id);

        userCall.enqueue(new Callback<AdminChatResult>() {
            @Override
            public void onResponse(Call<AdminChatResult> call, Response<AdminChatResult> response) {

                try {
                    if (response != null) {
                        Log.e("res_i_report", "" + response.body().getData());

                        if (response.body().getResult().equals("true")) {


                            adminChatAdapter = new AdminChatAdapter(AdminChatActivity.this, response.body().getData());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(AdminChatActivity.this);
                            recycler_view.setLayoutManager(new LinearLayoutManager(AdminChatActivity.this, RecyclerView.VERTICAL, false));
                            //recycleview_subject.setLayoutManager(new GridLayoutManager(ChapterActivity.this,2));
                            recycler_view.setAdapter(adminChatAdapter);
                            adminChatAdapter.notifyDataSetChanged();
                            recycler_view.scrollToPosition(chat_modelArrayList.size() - 2);


                        } else {
                            if (response.body().getMsg().equals("invalid_token")) {
                                Utils.Logout_Api(session.getUserId(), AdminChatActivity.this);
                            }
                            Toast.makeText(AdminChatActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("error_i_report", e.getMessage());
                }
                //  progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AdminChatResult> call, Throwable t) {
                Log.e("error_i_report1", t.getMessage());
            }
        });


    }

    private void send_msg_to_admin(String msg) {

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<GetMyQueryListResult> userCall = service.Send_MSg(session.getUserId(), Utils.Get_Device_ID(AdminChatActivity.this), id, msg);

        userCall.enqueue(new Callback<GetMyQueryListResult>() {
            @Override
            public void onResponse(Call<GetMyQueryListResult> call, Response<GetMyQueryListResult> response) {

                try {
                    if (response != null) {
                        Log.e("res_i_report", "" + response.body().getData());

                        if (response.body().getResult().equals("true")) {

                            //    Toast.makeText(AdminChatActivity.this, "Msg Sent", Toast.LENGTH_SHORT).show();
                            text_send.setText("");
                            get_admin_chat();
                          /*  quesryListAdapter = new QuesryListAdapter(getActivity(),response.body().getData());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            //     recycleview_subject.setLayoutManager(new GridLayoutManager(ChapterActivity.this,2));
                            recycleview.setAdapter(quesryListAdapter);
                            quesryListAdapter.notifyDataSetChanged();*/
                        } else {
                            Toast.makeText(AdminChatActivity.this, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("error_i_report", e.getMessage());
                }
                //  progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GetMyQueryListResult> call, Throwable t) {
                Log.e("error_i_report1", t.getMessage());
            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}