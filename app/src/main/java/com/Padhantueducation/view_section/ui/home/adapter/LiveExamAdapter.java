package com.Padhantueducation.view_section.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.Padhantueducation.view_section.ui.home.Activity.LiveExamListActivity;
import com.Padhantueducation.view_section.ui.home.Activity.LiveExamStartActivity;
import com.Padhantueducation.view_section.ui.home.Models.LiveExamModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.Padhantueducation.CommonClasses.Utils.alert_dialoge;
import static com.Padhantueducation.remote.APIUrl.Check_attempt_live_exam;

public class LiveExamAdapter extends RecyclerView.Adapter<LiveExamAdapter.HeroViewHolder> {
    Context mCtx;
    List<LiveExamModel> heroList;
    Session session;
    String date;
    long seconds,minutes,hours,days;
    boolean flag=false;


    public LiveExamAdapter(LiveExamListActivity mCtx, List<LiveExamModel> data) {
        this.mCtx = mCtx;
        this.heroList = data;
    }


    @NonNull
    @Override
    public LiveExamAdapter.HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_live_exam_list, parent, false);
        return new LiveExamAdapter.HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HeroViewHolder holder, final int position) {
        final LiveExamModel hero = heroList.get(position);
        session = new Session(mCtx);

        holder.tv_topic.setText(hero.getTopic());
        holder.no_of_que.setText(hero.getNoOfQuestion());
        holder.duration.setText(hero.getTimeSlot() + " Min");
        holder.quest_type.setText(hero.getQuestionType());
        holder.exam_start.setText(hero.getStartDate() + " at " + hero.getStartTime());
        holder.exam_expired.setText(hero.getEndDate() + " at " + hero.getEndTime());
        date=heroList.get(position).getFormat_dateTime();
        String delegate = "hh:mm:ss aaa";
        String AmPmTime= (String) DateFormat.format(delegate, Calendar.getInstance().getTime());
        Log.e("currentAmPm", AmPmTime);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        Log.e("currentDateandTime", currentDateandTime);


        if (date!=null && !date.isEmpty()){

            try {
                Date startDate = sdf.parse(date);
                System.out.println(startDate);
                Log.e("start_date", startDate.toString());
                Date currentDate = new Date();

                long diff = startDate.getTime() - currentDate.getTime();
                seconds = diff / 1000;
                minutes = seconds / 60;
                hours = minutes / 60;
                days = hours / 24;
                //Log.e("oldDate", "is previous date");
                Log.e("Difference1: ", " seconds: " + seconds + " minutes: " + minutes + " hours: " + hours + " days: " + days);
                if (seconds<1 || seconds<-1){
                    flag=true;
                    holder.start_now.setBackgroundColor(holder.start_now.getContext().getResources().getColor(R.color.green));
                    Log.e("sec_left", ""+minutes);
                }else {
                    flag=false;
                    Log.e("live_min_left+5", ""+minutes);
                }


            } catch (ParseException e) {

                e.printStackTrace();
            }
        }
        holder.start_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String Id=heroList.get(position).getId();
                    String Topic=heroList.get(position).getTopic();
                    String NoQuestion=heroList.get(position).getNoOfQuestion();
                    String TimeSlot=heroList.get(position).getTimeSlot();
                    JoinLiveGame(holder.start_now,Id,Topic,NoQuestion, TimeSlot);
                }catch (Exception e){

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

                             /*   mCtx.startActivity(new Intent(mCtx, LiveExamStartActivity.class)
                                        .putExtra("id", live_exam_id)
                                        .putExtra("title", topic)
                                        .putExtra("total_no_question",no_Ques )
                                        .putExtra("time_slot",timeslot ));
*/



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




    private void JoinLiveGame(Button btnJoin, String id, String topic, String noQuestion, String timeSlot) {
        String delegate = "hh:mm:ss aaa";
        String AmPmTime= (String) DateFormat.format(delegate, Calendar.getInstance().getTime());
        Log.e("currentAmPm", AmPmTime);

        // tv_live_nexttime

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        Log.e("currentDateandTime", currentDateandTime);

        if (date!=null && !date.isEmpty()){
            try {
                Date startDate = sdf.parse(date);
                System.out.println(startDate);
                Log.e("start_date", startDate.toString());
                Date currentDate = new Date();
                long diff = startDate.getTime() - currentDate.getTime();
                seconds = diff / 1000;
                minutes = seconds / 60;
                hours = minutes / 60;
                days = hours / 24;
                /// if (startDate.after(currentDate)) {
                //Log.e("oldDate", "is previous date");
                Log.e("Difference1: ", " seconds: " + seconds + " minutes: " + minutes + " hours: " + hours + " days: " + days);
                if (seconds<1 || seconds<-1){
                    btnJoin.setBackgroundColor(btnJoin.getContext().getResources().getColor(R.color.green));
                    check_attempt_Api(id,topic,noQuestion,timeSlot);
                }else {
                    Toast.makeText(mCtx, " Please Wait for the Exam, Best of Luck!!! ", Toast.LENGTH_SHORT).show();
                    Log.e("live_min_left+5", ""+minutes);
                }


            } catch (ParseException e) {

                e.printStackTrace();
            }
        }


    }












    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tv_topic, no_of_que, duration, quest_type, exam_start, exam_expired;
        Button start_now;


        public HeroViewHolder(View itemView) {
            super(itemView);

            exam_start = itemView.findViewById(R.id.exam_start);
            exam_expired = itemView.findViewById(R.id.exam_expired);
            duration = itemView.findViewById(R.id.duration);
            tv_topic = itemView.findViewById(R.id.tv_topic);
            quest_type = itemView.findViewById(R.id.quest_type);
            no_of_que = itemView.findViewById(R.id.no_of_que);
            start_now = itemView.findViewById(R.id.start_now);


        }
    }
}



