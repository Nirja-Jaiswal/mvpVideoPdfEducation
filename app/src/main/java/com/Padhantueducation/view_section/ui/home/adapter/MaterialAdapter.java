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
import com.Padhantueducation.R;
import com.Padhantueducation.view_section.ui.home.Activity.PDFViewActivity;
import com.Padhantueducation.view_section.ui.home.Models.MaterialModel;
import java.util.List;

import static com.Padhantueducation.remote.APIUrl.CLASS_VIDEO;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.HeroViewHolder> {

    Context mCtx;
    List<MaterialModel> heroList;


    public MaterialAdapter(Context mCtx, List<MaterialModel> heroList) {
        this.mCtx = mCtx;
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_material_layout, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, final int position) {
        MaterialModel hero = heroList.get(position);

        //  Glide.with(mCtx).load(CLASS_IMAGE+hero.getChapter_img()).placeholder(R.drawable.book).into(holder.imageView);

        holder.pdf_name.setText(hero.getTitle());

      //  holder.duration.setText(hero.getTime_slot());
       // holder.no_of_que.setText(hero.getNo_of_question());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCtx.startActivity(new Intent(mCtx, PDFViewActivity.class)
                .putExtra("url",CLASS_VIDEO+heroList.get(position).getChapter_pdf())
                 .putExtra("chapter_name",heroList.get(position).getTitle()));


            }
        });








    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title,no_of_que,duration,start_now,pdf_name;
        CardView cardView;
        public HeroViewHolder(View itemView) {
            super(itemView);


            pdf_name =itemView.findViewById(R.id.textView);
            cardView =itemView.findViewById(R.id.cardview);




        }
    }
}


