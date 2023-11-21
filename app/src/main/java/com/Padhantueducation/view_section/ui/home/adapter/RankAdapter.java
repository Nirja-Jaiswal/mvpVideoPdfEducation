package com.Padhantueducation.view_section.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.view_section.ui.home.Models.RankModel;

import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.HeroViewHolder> {

        Context mCtx;
        List<RankModel> heroList;
        Session session;

        public RankAdapter(Context mCtx, List<RankModel> heroList) {
            this.mCtx = mCtx;
            this.heroList = heroList;
        }

        @NonNull
        @Override
        public RankAdapter.HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mCtx).inflate(R.layout.item_rank_list, parent, false);
            return new RankAdapter.HeroViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RankAdapter.HeroViewHolder holder, final int position) {
            RankModel hero = heroList.get(position);
            session = new Session(mCtx);

            //  Glide.with(mCtx).load(CLASS_IMAGE+hero.getChapter_img()).placeholder(R.drawable.book).into(holder.imageView);

            holder.user_name.setText(hero.getName());
            holder.rank.setText(hero.getMarks()+" Marks ");
            holder.counting.setText(String.valueOf(position+1));


        }


        @Override
        public int getItemCount() {
            return heroList.size();
        }

        class HeroViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView user_name,rank,counting;


            public HeroViewHolder(View itemView) {
                super(itemView);

                counting=itemView.findViewById(R.id.counting);
                rank=itemView.findViewById(R.id.rank);
                user_name = itemView.findViewById(R.id.user_name);


            }
        }
    }




