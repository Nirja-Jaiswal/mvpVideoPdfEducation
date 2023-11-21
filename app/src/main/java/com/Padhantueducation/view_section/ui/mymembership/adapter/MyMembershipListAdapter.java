package com.Padhantueducation.view_section.ui.mymembership.adapter;

import android.content.Context;
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
import com.Padhantueducation.models.MyMembershipModel;

import java.util.List;


public class MyMembershipListAdapter extends RecyclerView.Adapter<MyMembershipListAdapter.HeroViewHolder> {

        Context mCtx;
        List<MyMembershipModel> heroList;
        Session session;

        public MyMembershipListAdapter(Context mCtx, List<MyMembershipModel> heroList) {
            this.mCtx = mCtx;
            this.heroList = heroList;
        }

        @NonNull
        @Override
        public MyMembershipListAdapter.HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mCtx).inflate(R.layout.item_my_membership, parent, false);
            return new MyMembershipListAdapter.HeroViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyMembershipListAdapter.HeroViewHolder holder, final int position) {
            MyMembershipModel hero = heroList.get(position);
            session = new Session(mCtx);

            //  Glide.with(mCtx).load(CLASS_IMAGE+hero.getChapter_img()).placeholder(R.drawable.book).into(holder.imageView);

            holder.tv_price.setText(hero.getPrice()+" Rs");

            holder.tv_mrp.setText(hero.getMrp()+" Rs");
            holder.tv_mrp.setPaintFlags(holder.tv_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.class_name.setText(hero.getClassName());
            holder.tv_description.setText(hero.getDescription());


        }


        @Override
        public int getItemCount() {
            return heroList.size();
        }

        class HeroViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView class_name,tv_description,tv_mrp,tv_price,view_details;
            CardView linear_layout;
            Button btn_upgrade_now, btn_cancel;

            public HeroViewHolder(View itemView) {
                super(itemView);

                view_details=itemView.findViewById(R.id.view_details);
                tv_mrp=itemView.findViewById(R.id.tv_mrp);
                class_name = itemView.findViewById(R.id.class_name);
                tv_description = itemView.findViewById(R.id.tv_description);
                btn_upgrade_now = itemView.findViewById(R.id.btn_upgrade_now);
                tv_price=itemView.findViewById(R.id.tv_price);


            }
        }
    }



