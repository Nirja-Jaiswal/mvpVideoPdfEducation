package com.Padhantueducation.view_section.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.models.SubscriptionListModel;
import com.Padhantueducation.view_section.ui.home.Activity.MemberShipDetailActivity;

import java.util.List;

public class MemberShipAdapter extends RecyclerView.Adapter<com.Padhantueducation.view_section.ui.home.adapter.MemberShipAdapter.HeroViewHolder> {

        Context mCtx;
        List<SubscriptionListModel> heroList;
        Session session;

        public MemberShipAdapter(Context mCtx, List<SubscriptionListModel> heroList) {
            this.mCtx = mCtx;
            this.heroList = heroList;
        }

        @NonNull
        @Override
        public com.Padhantueducation.view_section.ui.home.adapter.MemberShipAdapter.HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mCtx).inflate(R.layout.item_membership_adapter, parent, false);
            return new com.Padhantueducation.view_section.ui.home.adapter.MemberShipAdapter.HeroViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull com.Padhantueducation.view_section.ui.home.adapter.MemberShipAdapter.HeroViewHolder holder, final int position) {
            SubscriptionListModel hero = heroList.get(position);
            session = new Session(mCtx);

            //  Glide.with(mCtx).load(CLASS_IMAGE+hero.getChapter_img()).placeholder(R.drawable.book).into(holder.imageView);

            holder.tv_price.setText(hero.getPrice()+" Rs");

            holder.tv_mrp.setText(hero.getMrp()+" Rs");
            holder.tv_mrp.setPaintFlags(holder.tv_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.class_name.setText(hero.getClassName());
            holder.tv_description.setText(hero.getDescription());
            holder.tv_plan_expire.setText(hero.getEnd_date());



            holder.view_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mCtx.startActivity(new Intent(mCtx, MemberShipDetailActivity.class)
                    .putExtra("class_name",heroList.get(position).getClassName())
                    .putExtra("price",heroList.get(position).getPrice())
                    .putExtra("mrp",heroList.get(position).getMrp())
                    .putExtra("description",heroList.get(position).getDescription())
                    .putExtra("cource_id",heroList.get(position).getCourseId())
                    .putExtra("id",heroList.get(position).getId())
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
            TextView class_name,tv_description,tv_mrp,tv_price,view_details,tv_plan_expire;
            CardView linear_layout;
            Button btn_upgrade_now, btn_cancel;

            public HeroViewHolder(View itemView) {
                super(itemView);



                tv_plan_expire=itemView.findViewById(R.id.tv_plan_expire);
                view_details=itemView.findViewById(R.id.view_details);
                tv_mrp=itemView.findViewById(R.id.tv_mrp);
                class_name = itemView.findViewById(R.id.class_name);
                tv_description = itemView.findViewById(R.id.tv_description);
                btn_upgrade_now = itemView.findViewById(R.id.btn_upgrade_now);
                tv_price=itemView.findViewById(R.id.tv_price);
            }
        }
    }




