package com.Padhantueducation.view_section.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.Padhantueducation.R;
import com.Padhantueducation.view_section.ui.home.Models.GetQuestionModel;

import java.util.ArrayList;
import java.util.List;

import static com.Padhantueducation.remote.APIUrl.IMAGE_BASE_URL;


public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.HeroViewHolder> {
    private Context mCtx;
    private List<GetQuestionModel> heroList;

    public static ArrayList<String> selectedAnswers = new ArrayList<>();


    public QuestionListAdapter(Context mCtx, List<GetQuestionModel> heroList) {
        this.mCtx = mCtx;
        this.heroList = heroList;
        for (int i = 0; i < heroList.size(); i++) {
            selectedAnswers.add("Not Attempted");
        }
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_exam_start_layout, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HeroViewHolder holder, final int position) {
        final GetQuestionModel hero = heroList.get(position);
        holder.setIsRecyclable(false);


        holder.question_counting.setText(String.valueOf(position+1));


        if (!hero.getQuestions().equals("")){
            holder.TvQuestion.setText(hero.getQuestions().trim());
        }else {
            holder.TvQuestion.setVisibility(View.GONE);
            holder.ImgQuestion.setVisibility(View.VISIBLE);
            Glide.with(mCtx).load(IMAGE_BASE_URL+hero.getQuestionIMG().trim()).into(holder.ImgQuestion);
        }



        if (!hero.getOptionA().equals("")){
            holder.TvOptionA.setText(hero.getOptionA().trim());
        }else {
            holder.ImgOptionA.setVisibility(View.VISIBLE);
            Glide.with(mCtx).load(IMAGE_BASE_URL+hero.getOptionA_img()).into(holder.ImgOptionA);
        }


        if (!hero.getOptionB().equals("")){
            holder.TvOptionB.setText(hero.getOptionB().trim());
        }else {
            holder.ImgOptionB.setVisibility(View.VISIBLE);
            Glide.with(mCtx).load(IMAGE_BASE_URL+hero.getOptionB_img()).into(holder.ImgOptionB);
        }



        if (!hero.getOptionC().equals("")){
            holder.TvOptionC.setText(hero.getOptionC().trim());
        }else {
            holder.ImgOptionC.setVisibility(View.VISIBLE);
            Glide.with(mCtx).load(IMAGE_BASE_URL+hero.getOptionC_img()).into(holder.ImgOptionC); }



        if (!hero.getOptionD().equals("")){
            holder.TvOptionD.setText(hero.getOptionD().trim());
        }else {
            holder.ImgOptionD.setVisibility(View.VISIBLE);
            Glide.with(mCtx).load(IMAGE_BASE_URL+hero.getOptionD_img()).into(holder.ImgOptionD);
        }




        if (hero.isSelectedA()) {
            holder.mradio_one.setChecked(true);
        }
        if (hero.isSelectedB()) {
            holder.mradio_two.setChecked(true);
        }
        if (hero.isSelectedC()) {
            holder.mradio_three.setChecked(true);
        }
        if (hero.isSelectedD()) {
            holder.mradio_four.setChecked(true);
        }


        holder.mradio_one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.mradio_one.setChecked(true);
                    holder.mradio_two.setChecked(false);
                    holder.mradio_three.setChecked(false);
                    holder.mradio_four.setChecked(false);
                    heroList.get(position).setSelectedA(true);
                    if (!hero.getOptionA().equals("")){
                        selectedAnswers.set(position,"\"" + hero.getId().trim() + "\""+":"+"\"" + "A" + "\"");
                    }else {
                        selectedAnswers.set(position,"\"" + hero.getId().trim() + "\""+":"+"\"" + "A" + "\""); }
                } else {
                    heroList.get(position).setSelectedA(false);
                }
            }
        });



        holder.mradio_two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.mradio_one.setChecked(false);
                    holder.mradio_two.setChecked(true);
                    holder.mradio_three.setChecked(false);
                    holder.mradio_four.setChecked(false);
                    heroList.get(position).setSelectedB(true);
                    if (!hero.getOptionB().equals("")){
                        selectedAnswers.set(position,"\"" + hero.getId().trim() + "\""+":"+"\"" + "B" + "\"");
                    }else {
                        selectedAnswers.set(position,"\"" + hero.getId().trim() + "\""+":"+"\"" + "B" + "\"");
                    }
                } else {
                    heroList.get(position).setSelectedB(false);
                }
            }
        });
        holder.mradio_three.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.mradio_one.setChecked(false);
                    holder.mradio_two.setChecked(false);
                    holder.mradio_three.setChecked(true);
                    holder.mradio_four.setChecked(false);
                    heroList.get(position).setSelectedC(true);
                    if (!hero.getOptionC().equals("")){
                        selectedAnswers.set(position,"\"" + hero.getId().trim() + "\""+":"+"\"" + "C" + "\"");
                    }else {
                        selectedAnswers.set(position,"\"" + hero.getId().trim() + "\""+":"+"\"" + "C" + "\"");
                    }
                } else {
                    heroList.get(position).setSelectedC(false);
                }
            }
        });


        holder.mradio_four.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.mradio_one.setChecked(false);
                    holder.mradio_two.setChecked(false);
                    holder.mradio_three.setChecked(false);
                    holder.mradio_four.setChecked(true);
                    heroList.get(position).setSelectedD(true);
                    if (!hero.getOptionD().equals("")){
                        selectedAnswers.set(position,"\"" + hero.getId().trim() + "\""+":"+"\"" + "D" + "\"");
                    }else {
                        selectedAnswers.set(position,"\"" + hero.getId().trim() + "\""+":"+"\"" + "D" + "\"");
                    }
                } else {
                    heroList.get(position).setSelectedD(false);
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, no_of_que, duration, start_now, question_counting;
        RadioButton mradio_one, mradio_two, mradio_three, mradio_four;


        TextView TvQuestion,TvOptionA,TvOptionB,TvOptionC,TvOptionD;
        ImageView ImgQuestion,ImgOptionA,ImgOptionB,ImgOptionC,ImgOptionD;
        RadioGroup rgAnswers;


        public HeroViewHolder(View itemView) {
            super(itemView);
            TvQuestion = itemView.findViewById(R.id.TvQuestion);
            ImgQuestion = itemView.findViewById(R.id.ImgQuestion);

            TvOptionA = itemView.findViewById(R.id.TvOptionA);
            ImgOptionA = itemView.findViewById(R.id.ImgOptionA);

            TvOptionB = itemView.findViewById(R.id.TvOptionB);
            ImgOptionB = itemView.findViewById(R.id.ImgOptionB);

            TvOptionC = itemView.findViewById(R.id.TvOptionC);
            ImgOptionC = itemView.findViewById(R.id.ImgOptionC);

            TvOptionD = itemView.findViewById(R.id.TvOptionD);
            ImgOptionD = itemView.findViewById(R.id.ImgOptionD);




            rgAnswers = itemView.findViewById(R.id.radio_group);
            imageView = itemView.findViewById(R.id.image);

            title = itemView.findViewById(R.id.title);
            no_of_que = itemView.findViewById(R.id.no_of_que);
            duration = itemView.findViewById(R.id.duration);
            start_now = itemView.findViewById(R.id.start_now);
            question_counting = itemView.findViewById(R.id.question_counting);

            mradio_one = itemView.findViewById(R.id.radio_one);
            mradio_two = itemView.findViewById(R.id.radio_two);
            mradio_three = itemView.findViewById(R.id.radio_three);
            mradio_four = itemView.findViewById(R.id.radio_four);
        }

    }
}


