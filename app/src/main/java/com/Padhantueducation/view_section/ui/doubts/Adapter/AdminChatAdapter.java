package com.Padhantueducation.view_section.ui.doubts.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.models.AdminChatModel;

import java.util.ArrayList;
import java.util.List;

public class AdminChatAdapter extends RecyclerView.Adapter<AdminChatAdapter.ViewHolder> {

    private List<AdminChatModel> allCommunityModels;
    private Context context;
    String user_id,product_id,wish_id;
    private Session session;


    public AdminChatAdapter(Context context,List<AdminChatModel> allCommunityModels1) {
        this.allCommunityModels = allCommunityModels1;
        this.context = context;
    }



    public void setFilter(List<AdminChatModel> apointUserModelArrayList1) {
        allCommunityModels = new ArrayList<>();
        allCommunityModels.addAll(apointUserModelArrayList1);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AdminChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new AdminChatAdapter.ViewHolder(itemView);

    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final AdminChatAdapter.ViewHolder holder, final int position) {
        if (allCommunityModels.size() > 0) {

            session=new Session(context);

            final AdminChatModel allCommunityModel=allCommunityModels.get(position);

            holder.admin_msg.setText(allCommunityModel.getAMsg());
            holder.customer_msg.setText(allCommunityModel.getUMsg());

            holder.admin_time.setText(allCommunityModel.getA_time());
            holder.my_time.setText(allCommunityModel.getTime());


            if (allCommunityModel.getAMsg().equals(""))
            {
                holder.admin_lin.setVisibility(View.GONE);
            }
            if (allCommunityModel.getUMsg().equals(""))
            {
                holder.cust_lin.setVisibility(View.GONE);
            }

        }
    }


    @Override
    public int getItemCount() {
        return allCommunityModels.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView customer_msg,admin_msg,admin_time,my_time;
        ImageView product_Imageviw,heart;
        LinearLayout payment_lin;
        RelativeLayout admin_lin,cust_lin;

        ViewHolder(View parent) {
            super(parent);

            admin_msg=parent.findViewById(R.id.admin_msg);
            customer_msg=parent.findViewById(R.id.customer_msg);
            admin_lin=parent.findViewById(R.id.admin_lin);
            cust_lin=parent.findViewById(R.id.cust_lin);


            admin_time=parent.findViewById(R.id.admin_time);
            my_time=parent.findViewById(R.id.my_time);


        }
    }

}






