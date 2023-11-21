package com.Padhantueducation.view_section.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.Padhantueducation.R;
import com.Padhantueducation.view_section.ui.home.Activity.VideoViewActivity;
import com.Padhantueducation.view_section.ui.home.Models.StudyZoneModel;

import java.util.List;

import static com.Padhantueducation.remote.APIUrl.CLASS_VIDEO;
import static com.Padhantueducation.remote.APIUrl.VIDEO_THUMBNIL;

public class StudyZoneAdapter extends RecyclerView.Adapter<StudyZoneAdapter.HeroViewHolder> {

    Context mCtx;
    List<StudyZoneModel> heroList;


    public StudyZoneAdapter(Context mCtx, List<StudyZoneModel> heroList) {
        this.mCtx = mCtx;
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_studyzone_layout, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, final int position) {
        StudyZoneModel hero = heroList.get(position);

        Glide.with(mCtx).load(VIDEO_THUMBNIL+hero.getChapter_banner()).into(holder.banner_img);

        holder.video_title.setText(hero.getTitle());

        holder.video_desc.setText(hero.getSub_chapter());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCtx.startActivity(new Intent(mCtx, VideoViewActivity.class)
                        .putExtra("video_url",CLASS_VIDEO+heroList.get(position).getChapter_video())
                        .putExtra("chapter_name",heroList.get(position).getTitle()));
            }
        });







    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {

        ImageView banner_img;
        TextView video_title,video_desc;
        CardView cardView;
        public HeroViewHolder(View itemView) {
            super(itemView);


            banner_img=itemView.findViewById(R.id.banner_img);
            video_title=itemView.findViewById(R.id.video_title);
            video_desc=itemView.findViewById(R.id.video_desc);
            cardView =itemView.findViewById(R.id.card);




        }
    }
}


