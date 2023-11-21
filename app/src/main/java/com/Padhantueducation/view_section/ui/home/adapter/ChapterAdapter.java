package com.Padhantueducation.view_section.ui.home.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.models.AdminChatResult;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.ui.home.Activity.ClassModulesActivity;
import com.Padhantueducation.view_section.ui.home.Models.ChapterModel;
import com.Padhantueducation.view_section.ui.other_activity.SubscriptionActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.Padhantueducation.CommonClasses.Utils_common.dismissProgressDialog;
import static com.Padhantueducation.CommonClasses.Utils_common.showProgressDialog;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.HeroViewHolder> {

    Context mCtx;
    List<ChapterModel> heroList;
    Session session;

    public ChapterAdapter(Context mCtx, List<ChapterModel> heroList) {
        this.mCtx = mCtx;
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_chapter_layout, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HeroViewHolder holder, final int position) {
        final ChapterModel hero = heroList.get(position);
        session = new Session(mCtx);

        //  Glide.with(mCtx).load(CLASS_IMAGE+hero.getChapter_img()).placeholder(R.drawable.book).into(holder.imageView);

        holder.textView.setText(hero.getSub_chapter());

        if (hero.getType().equals("0")){
            holder.ImgFree.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.GONE);
        }

        holder.linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cource_id = heroList.get(position).getCourse_id();
                String _id = heroList.get(position).getId();
                String sub_chapter = heroList.get(position).getSub_chapter();

                if (hero.getType().equals("0")){


                    mCtx.startActivity(new Intent(mCtx, ClassModulesActivity.class)
                            .putExtra("id", _id)
                            .putExtra("chapter_name", sub_chapter));


                }else {
                    Check_Plan_api(cource_id,_id,sub_chapter);
                }


            }
        });





    }

    public  void check_plan(){
        final Dialog dialog = new Dialog(mCtx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.subscription_alert_msg);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btn_upgrade_now = (Button) dialog.findViewById(R.id.btn_upgrade_now);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_upgrade_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCtx.startActivity(new Intent(mCtx, SubscriptionActivity.class));
                dialog.dismiss();
            }
        });
        dialog.show();

    }







    private void Check_Plan_api(String cource_id, final String id, final String sub_chapter) {

        showProgressDialog(mCtx);
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<AdminChatResult> userCall = service.Check_Plan(session.getUserId(), Utils.Get_Device_ID(mCtx), cource_id);

        userCall.enqueue(new Callback<AdminChatResult>() {
            @Override
            public void onResponse(Call<AdminChatResult> call, Response<AdminChatResult> response) {
                dismissProgressDialog();
                // Log.e("onResponse", "" + response.body().getData().toString());
                if (response.body().getResult().equals("true")) {
                    Log.e("TAG", "response 33: " + new Gson().toJson(response.body()));
                    //Utils.Toast(mCtx,response.body().getPlan_status());

                    String plan_status = response.body().getPlan_status();


                    if (plan_status.equals("1")) {
                        mCtx.startActivity(new Intent(mCtx, ClassModulesActivity.class)
                                .putExtra("id", id)
                                .putExtra("chapter_name", sub_chapter));
                    }


                } else {

                    check_plan();


                   // alert_dialoge(mCtx, response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<AdminChatResult> call, Throwable t) {
                dismissProgressDialog();
                Log.d("onFailure", t.toString());
            }
        });


    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView,ImgFree;
        TextView textView;
        CardView linear_layout;
        Button btn_upgrade_now, btn_cancel;

        public HeroViewHolder(View itemView) {
            super(itemView);

            ImgFree=itemView.findViewById(R.id.ImgFree);
            linear_layout = itemView.findViewById(R.id.card_subject);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.chapter_name);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            btn_upgrade_now = itemView.findViewById(R.id.btn_upgrade_now);


        }
    }
}



