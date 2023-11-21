package com.Padhantueducation.view_section.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.Padhantueducation.R;
import com.Padhantueducation.view_section.ui.home.Models.ExamListModel;
import com.Padhantueducation.view_section.view.ExamdetailsActivity;
import java.util.List;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.HeroViewHolder> {

    Context mCtx;
    List<ExamListModel> heroList;

    public ExamListAdapter(Context mCtx, List<ExamListModel> heroList) {
        this.mCtx = mCtx;
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_exam_layout, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, final int position) {
        ExamListModel hero = heroList.get(position);

        //  Glide.with(mCtx).load(CLASS_IMAGE+hero.getChapter_img()).placeholder(R.drawable.book).into(holder.imageView);

        holder.title.setText(hero.getTitle());
        holder.duration.setText(hero.getTime_slot()+" Min");
        holder.no_of_que.setText(hero.getNo_of_question());


        holder.start_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCtx.startActivity(new Intent(mCtx, ExamdetailsActivity.class)
                        .putExtra("id",heroList.get(position).getId())
                        .putExtra("title",heroList.get(position).getTitle())
                        .putExtra("total_no_question",heroList.get(position).getNo_of_question())
                        .putExtra("time_slot",heroList.get(position).getTime_slot())

                );

            }
        });








    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title,no_of_que,duration,start_now;

        public HeroViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);

            title = itemView.findViewById(R.id.title);
            no_of_que=itemView.findViewById(R.id.no_of_que);
            duration=itemView.findViewById(R.id.duration);
            start_now=itemView.findViewById(R.id.start_now);

        }
    }
}

