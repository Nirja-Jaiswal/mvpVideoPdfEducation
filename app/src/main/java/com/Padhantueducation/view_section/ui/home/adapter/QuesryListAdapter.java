package com.Padhantueducation.view_section.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.Padhantueducation.R;
import com.Padhantueducation.models.GetMyQueryListModel;
import com.Padhantueducation.view_section.ui.doubts.AdminChatActivity;

import java.util.List;

    public class QuesryListAdapter extends RecyclerView.Adapter<com.Padhantueducation.view_section.ui.home.adapter.QuesryListAdapter.HeroViewHolder> {

        Context mCtx;
        List<GetMyQueryListModel> heroList;

        public QuesryListAdapter(Context mCtx, List<GetMyQueryListModel> heroList) {
            this.mCtx = mCtx;
            this.heroList = heroList;
        }

        @NonNull
        @Override
        public com.Padhantueducation.view_section.ui.home.adapter.QuesryListAdapter.HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mCtx).inflate(R.layout.item_query_layout, parent, false);
            return new com.Padhantueducation.view_section.ui.home.adapter.QuesryListAdapter.HeroViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull com.Padhantueducation.view_section.ui.home.adapter.QuesryListAdapter.HeroViewHolder holder, final int position) {
            GetMyQueryListModel hero = heroList.get(position);

            //  Glide.with(mCtx).load(CLASS_IMAGE+hero.getChapter_img()).placeholder(R.drawable.book).into(holder.imageView);

            holder.question_tv.setText(hero.getQuestion());
            holder.answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCtx.startActivity(new Intent(mCtx, AdminChatActivity.class)
                            .putExtra("id",heroList.get(position).getId())
                                .putExtra("question",heroList.get(position).getQuestion())

                    );
                }
            });

           holder.tv_date_time.setText("Asked on "+hero.getCreatedAt()+" at "+hero.getTime());







        }

        @Override
        public int getItemCount() {
            return heroList.size();
        }

        class HeroViewHolder extends RecyclerView.ViewHolder {


            TextView question_tv,tv_date_time,answer;


            public HeroViewHolder(View itemView) {
                super(itemView);

                answer=itemView.findViewById(R.id.answer);
                question_tv = itemView.findViewById(R.id.question_tv);
                tv_date_time=itemView.findViewById(R.id.tv_date_time);

            }
        }
    }




