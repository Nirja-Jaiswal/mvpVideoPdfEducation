package com.Padhantueducation.view_section.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.CommonClasses.VolleySingleton;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.view_section.ui.home.Activity.LiveExamStartActivity;
import com.Padhantueducation.view_section.ui.home.Activity.LiveResultShowActivity;
import com.Padhantueducation.view_section.ui.home.Models.LiveExamModel;
import com.Padhantueducation.view_section.ui.other_activity.RankActivity;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.Padhantueducation.CommonClasses.Utils.alert_dialoge;
import static com.Padhantueducation.remote.APIUrl.Check_attempt_live_exam;

    public class MyExamHistoryAdapter extends RecyclerView.Adapter<MyExamHistoryAdapter.HeroViewHolder> {

        Context mCtx;
        List<LiveExamModel> heroList;
        Session session;
        String status;


        public MyExamHistoryAdapter(Context mCtx, List<LiveExamModel> data,String status) {
            this.mCtx = mCtx;
            this.heroList = data;
            this.status=status;
        }


        @NonNull
        @Override
        public MyExamHistoryAdapter.HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mCtx).inflate(R.layout.item_my_score_list, parent, false);
            return new com.Padhantueducation.view_section.ui.home.adapter.MyExamHistoryAdapter.HeroViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyExamHistoryAdapter.HeroViewHolder holder, final int position) {
            final LiveExamModel hero = heroList.get(position);
            session = new Session(mCtx);

            //  Glide.with(mCtx).load(CLASS_IMAGE+hero.getChapter_img()).placeholder(R.drawable.book).into(holder.imageView);


            holder.tv_topic.setText(hero.getTopic());
            holder.no_of_que.setText(hero.getNoOfQuestion());
            holder.duration.setText(hero.getTimeSlot() + " Min");


            holder.start_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (status.equalsIgnoreCase("exam")){
                        mCtx.startActivity(new Intent(mCtx, LiveResultShowActivity.class).putExtra("exam_id",heroList.get(position).getLive_exam_id()));
                    }else {
                        mCtx.startActivity(new Intent(mCtx, RankActivity.class).putExtra("exam_id",heroList.get(position).getLive_exam_id()));
                    }
                }
            });


        }

        private void check_attempt_Api(final String live_exam_id,final String topic,final  String  no_Ques,final String timeslot) {
            Utils.showProgressDialog(mCtx);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Check_attempt_live_exam,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Utils.dismissProgressDialog();
                                JSONObject obj = new JSONObject(response);
                                String result = obj.getString("result");
                                String msg = obj.getString("msg");
                                if (result.equalsIgnoreCase("true")) {
                                    alert_dialoge(mCtx, msg);
                                } else {

                                    mCtx.startActivity(new Intent(mCtx, LiveExamStartActivity.class)
                                            .putExtra("id", live_exam_id)
                                            .putExtra("title", topic)
                                            .putExtra("total_no_question",no_Ques )
                                            .putExtra("time_slot",timeslot ));

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
                            Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", session.getUserId());
                    params.put("live_exam_id", live_exam_id);
                    params.put("device_token", Utils.Get_Device_ID(mCtx));
                    return params;
                }
            };
            VolleySingleton.getInstance(mCtx).addToRequestQueue(stringRequest);
        }


        @Override
        public int getItemCount() {
            return heroList.size();
        }

        class HeroViewHolder extends RecyclerView.ViewHolder {
            TextView tv_topic, no_of_que, duration, quest_type, exam_start, exam_expired, start_now;


            public HeroViewHolder(View itemView) {
                super(itemView);
               // exam_start = itemView.findViewById(R.id.exam_start);
               // exam_expired = itemView.findViewById(R.id.exam_expired);
                duration = itemView.findViewById(R.id.duration);
                tv_topic = itemView.findViewById(R.id.tv_topic);
              //  quest_type = itemView.findViewById(R.id.quest_type);
                no_of_que = itemView.findViewById(R.id.no_of_que);
                start_now = itemView.findViewById(R.id.start_now);
            }
        }
    }




