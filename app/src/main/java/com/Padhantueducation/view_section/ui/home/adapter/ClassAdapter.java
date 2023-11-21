package com.Padhantueducation.view_section.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.Padhantueducation.R;
import com.Padhantueducation.view_section.ui.home.Activity.SubjectActivity;
import com.Padhantueducation.view_section.ui.home.Models.GetClass;

import java.util.List;

import static com.Padhantueducation.remote.APIUrl.CLASS_IMAGE;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.HeroViewHolder> {

    Context mCtx;
    List<GetClass> heroList;

    public ClassAdapter(Context mCtx, List<GetClass> heroList) {
        this.mCtx = mCtx;
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_class_layout, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, final int position) {
        GetClass hero = heroList.get(position);

        Glide.with(mCtx).load(CLASS_IMAGE+hero.getImage()).into(holder.imageView);

        holder.textView.setText(hero.getClass_name());

        holder.linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


         mCtx.startActivity(new Intent(mCtx, SubjectActivity.class)
         .putExtra("cource_id",heroList.get(position).getId()));
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
        LinearLayout linear_layout;

        public HeroViewHolder(View itemView) {
            super(itemView);

            linear_layout=itemView.findViewById(R.id.linear_layout);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);

        }
    }
}

