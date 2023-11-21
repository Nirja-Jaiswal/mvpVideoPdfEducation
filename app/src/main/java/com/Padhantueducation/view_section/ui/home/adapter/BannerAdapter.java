package com.Padhantueducation.view_section.ui.home.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.Padhantueducation.R;
import com.Padhantueducation.view_section.ui.home.Models.BannerModel;
import com.Padhantueducation.view_section.ui.other_activity.SubscriptionActivity;

import java.util.List;

import static com.Padhantueducation.remote.APIUrl.IMAGE_BANNER;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.HeroViewHolder> {

    Context mCtx;
    List<BannerModel> heroList;

    public BannerAdapter(Context mCtx, List<BannerModel> heroList) {
        this.mCtx = mCtx;
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.banner_layout, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        BannerModel hero = heroList.get(position);

        Glide.with(mCtx).load(IMAGE_BANNER+hero.getBanner_img()).into(holder.imageView);

        holder.check_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_plan();
            }
        });

       // holder.textView.setText(hero.getBanner_name());

    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        LinearLayout check_plan;

        public HeroViewHolder(View itemView) {
            super(itemView);
            check_plan=itemView.findViewById(R.id.check_plan);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);

        }
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








}

