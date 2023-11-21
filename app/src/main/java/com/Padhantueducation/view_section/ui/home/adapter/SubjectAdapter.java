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
import com.Padhantueducation.view_section.ui.home.Activity.ChapterActivity;
import com.Padhantueducation.view_section.ui.home.Models.SubjectModel;

import java.util.List;

import static com.Padhantueducation.remote.APIUrl.CLASS_IMAGE;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.HeroViewHolder> {

    Context mCtx;
    List<SubjectModel> heroList;

    public SubjectAdapter(Context mCtx, List<SubjectModel> heroList) {
        this.mCtx = mCtx;
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_subject_layout, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, final int position) {
        SubjectModel hero = heroList.get(position);

        Glide.with(mCtx).load(CLASS_IMAGE+hero.getSubject_img()).placeholder(R.drawable.mathes).into(holder.imageView);

        holder.textView.setText(hero.getSubject_name());

        holder.linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCtx.startActivity(new Intent(mCtx, ChapterActivity.class).putExtra("id",heroList.get(position).getId()));



            }
        });




    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        CardView linear_layout;

        public HeroViewHolder(View itemView) {
            super(itemView);

            linear_layout=itemView.findViewById(R.id.card_subject);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);

        }
    }
}



