package com.Padhantueducation.view_section.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.Padhantueducation.R;
import com.Padhantueducation.models.ExamResultModel;

import java.util.ArrayList;
import java.util.List;

import static com.Padhantueducation.remote.APIUrl.IMAGE_BASE_URL;

public class ResultShowAdapterCopy extends RecyclerView.Adapter<ResultShowAdapterCopy.HeroViewHolder> {
    private Context mCtx;
    private List<ExamResultModel> heroList;
    public static ArrayList<String> selectedAnswers = new ArrayList<>();

    public ResultShowAdapterCopy(Context mCtx, List<ExamResultModel> heroList) {
        this.mCtx = mCtx;
        this.heroList = heroList;
        for (int i = 0; i < heroList.size(); i++) {
            selectedAnswers.add("Not Attempted");
        }
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.activity_examscore2, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HeroViewHolder holder, final int position) {
        final ExamResultModel hero = heroList.get(position);
        holder.question_counting.setText(String.valueOf(position + 1));
        holder.explanation.setText(hero.getExplanation().trim());

        String givenAns = hero.getYourAns().trim();
        String correctAns = hero.getCorrectAns().trim();
        String optA = hero.getOptionA().trim();
        String optB = hero.getOptionB().trim();
        String optC = hero.getOptionC().trim();
        String optD = hero.getOptionD().trim();

      /*holder.question.setText(hero.getQuestions().trim());
        holder.mradio_one.setText(optA);
        holder.mradio_two.setText(optB);
        holder.mradio_three.setText(optC);
        holder.mradio_four.setText(optD);*/

        holder.mradio_one.setClickable(false);
        holder.mradio_two.setClickable(false);
        holder.mradio_three.setClickable(false);
        holder.mradio_four.setClickable(false);

        if (!hero.getQuestions().equals("")) {
            holder.TvQuestion.setText(hero.getQuestions().trim());
        } else {
            holder.TvQuestion.setVisibility(View.GONE);
            holder.ImgQuestion.setVisibility(View.VISIBLE);
            Glide.with(mCtx).load(IMAGE_BASE_URL + hero.getQuestionIMG().trim()).into(holder.ImgQuestion);
        }


        if (!hero.getOptionA().equals("")) {
            holder.TvOptionA.setText(hero.getOptionA().trim());
        } else {
            holder.ImgOptionA.setVisibility(View.VISIBLE);
            Glide.with(mCtx).load(IMAGE_BASE_URL + hero.getOptionA_img().trim()).into(holder.ImgOptionA);
        }


        if (!hero.getOptionB().equals("")) {
            holder.TvOptionB.setText(hero.getOptionB().trim());
        } else {
            holder.ImgOptionB.setVisibility(View.VISIBLE);
            Glide.with(mCtx).load(IMAGE_BASE_URL + hero.getOptionB_img().trim()).into(holder.ImgOptionB);
        }


        if (!hero.getOptionC().equals("")) {
            holder.TvOptionC.setText(hero.getOptionC().trim());
        } else {
            holder.ImgOptionC.setVisibility(View.VISIBLE);
            Glide.with(mCtx).load(IMAGE_BASE_URL + hero.getOptionC_img().trim()).into(holder.ImgOptionC);
        }


        if (!hero.getOptionD().equals("")) {
            holder.TvOptionD.setText(hero.getOptionD().trim());
        } else {
            holder.ImgOptionD.setVisibility(View.VISIBLE);
            Glide.with(mCtx).load(IMAGE_BASE_URL + hero.getOptionD_img().trim()).into(holder.ImgOptionD);
        }


        if (hero.isSelectedA()) {
            holder.mradio_one.setChecked(true);
            holder.mradio_one.setButtonDrawable(R.drawable.select_green_check);
        }
        if (hero.isSelectedB()) {
            holder.mradio_two.setChecked(true);
            holder.mradio_two.setButtonDrawable(R.drawable.select_green_check);
        }
        if (hero.isSelectedC()) {
            holder.mradio_three.setChecked(true);
            holder.mradio_three.setButtonDrawable(R.drawable.select_green_check);
        }
        if (hero.isSelectedD()) {
            holder.mradio_four.setChecked(true);
            holder.mradio_four.setButtonDrawable(R.drawable.select_green_check);
        }
        if (hero.isSelectedFalseA()) {
            holder.mradio_one.setChecked(true);
            holder.mradio_one.setButtonDrawable(R.drawable.select_red_check);
        }
        if (hero.isSelectedFalseB()) {
            holder.mradio_two.setChecked(true);
            holder.mradio_two.setButtonDrawable(R.drawable.select_red_check);
        }
        if (hero.isSelectedFalseC()) {
            holder.mradio_three.setChecked(true);
            holder.mradio_three.setButtonDrawable(R.drawable.select_red_check);
        }
        if (hero.isSelectedFalseD()) {
            holder.mradio_four.setChecked(true);
            holder.mradio_four.setButtonDrawable(R.drawable.select_red_check);
        }


     /*   if (correctAns.equalsIgnoreCase(givenAns)) {
            if (!hero.isSelectedA()) {
                if (correctAns.equals("A")) {
                    holder.mradio_one.setChecked(true);
                    holder.mradio_one.setButtonDrawable(R.drawable.select_green_check);
                    heroList.get(position).setSelectedA(true);
                }
            }

        }
        else if (correctAns.equalsIgnoreCase(givenAns)) {
            if (!hero.isSelectedB()) {
                if (correctAns.equals("B")) {
                    holder.mradio_two.setChecked(true);
                    holder.mradio_two.setButtonDrawable(R.drawable.select_green_check);
                    heroList.get(position).setSelectedB(true);
                }
            }
        }
        else if (correctAns.equalsIgnoreCase(givenAns)) {
            if (!hero.isSelectedC()) {
                if (correctAns.equals("C")) {
                    holder.mradio_three.setChecked(true);
                    holder.mradio_three.setButtonDrawable(R.drawable.select_green_check);
                    heroList.get(position).setSelectedC(true);
                }
            }
        }
        else if (correctAns.equalsIgnoreCase(givenAns)) {
            if (!hero.isSelectedD()) {
                if (correctAns.equals("D")) {
                    holder.mradio_four.setChecked(true);
                    holder.mradio_four.setButtonDrawable(R.drawable.select_green_check);
                    heroList.get(position).setSelectedD(true);
                }
            }
        }

        else if (!givenAns.equalsIgnoreCase(correctAns)) {
                if (!hero.isSelectedFalseA()  &&!hero.isSelectedA()) {
                    if (givenAns.equals("A") && correctAns.equals("C")) {
                        holder.mradio_one.setChecked(true);
                        holder.mradio_one.setButtonDrawable(R.drawable.select_red_check);
                        heroList.get(position).setSelectedFalseA(true);


                        holder.mradio_three.setChecked(true);
                        holder.mradio_three.setButtonDrawable(R.drawable.select_green_check);
                        heroList.get(position).setSelectedC(true);
                    }
            }
        }
        else if (!givenAns.equalsIgnoreCase(correctAns)) {
                if (!hero.isSelectedFalseB()) {
                    if (givenAns.equals("B")) {
                        holder.mradio_two.setChecked(true);
                        holder.mradio_two.setButtonDrawable(R.drawable.select_red_check);
                        heroList.get(position).setSelectedFalseB(true);
                    }
                }
            }
        else if (!givenAns.equalsIgnoreCase(correctAns)) {
                if (!hero.isSelectedFalseC()) {
                    if (givenAns.equals("C")) {
                        holder.mradio_three.setChecked(true);
                        holder.mradio_three.setButtonDrawable(R.drawable.select_red_check);
                        heroList.get(position).setSelectedFalseC(true);
                    }
                }
            }
        else if (!givenAns.equalsIgnoreCase(correctAns)) {
                if (!hero.isSelectedFalseD()) {
                    if (givenAns.equals("D")) {
                        holder.mradio_four.setChecked(true);
                        holder.mradio_four.setButtonDrawable(R.drawable.select_red_check);
                        heroList.get(position).setSelectedFalseD(true);
                    }
                }
            }


*/


        if (!givenAns.equalsIgnoreCase(correctAns)) {
            //////////////////if correct answer is A////////////////////////
            if (!hero.isSelectedA() &&!hero.isSelectedFalseC()) {
                if (givenAns.equals("C") && correctAns.equals("A")) {
                    holder.mradio_three.setChecked(true);
                    holder.mradio_three.setButtonDrawable(R.drawable.select_red_check);
                    heroList.get(position).setSelectedFalseA(true);

                    holder.mradio_one.setChecked(true);
                    holder.mradio_one.setButtonDrawable(R.drawable.select_green_check);
                    heroList.get(position).setSelectedC(true);
                }
            }

            if (!hero.isSelectedA() &&!hero.isSelectedFalseB()) {
            if (givenAns.equals("B") && correctAns.equals("A")) {
                holder.mradio_two.setChecked(true);
                holder.mradio_two.setButtonDrawable(R.drawable.select_red_check);
                heroList.get(position).setSelectedFalseA(true);

                holder.mradio_one.setChecked(true);
                holder.mradio_one.setButtonDrawable(R.drawable.select_green_check);
                heroList.get(position).setSelectedC(true);
            } }

            if (!hero.isSelectedA() &&!hero.isSelectedFalseD()) {
                if (givenAns.equals("D") && correctAns.equals("A")) {
                    holder.mradio_four.setChecked(true);
                    holder.mradio_four.setButtonDrawable(R.drawable.select_red_check);
                    heroList.get(position).setSelectedFalseA(true);

                    holder.mradio_one.setChecked(true);
                    holder.mradio_one.setButtonDrawable(R.drawable.select_green_check);
                    heroList.get(position).setSelectedC(true);
                }
            }

            if (!hero.isSelectedB() &&!hero.isSelectedFalseC()) {
                if (givenAns.equals("C") && correctAns.equals("B")) {
                    holder.mradio_three.setChecked(true);
                    holder.mradio_three.setButtonDrawable(R.drawable.select_red_check);
                    heroList.get(position).setSelectedFalseA(true);

                    holder.mradio_two.setChecked(true);
                    holder.mradio_two.setButtonDrawable(R.drawable.select_green_check);
                    heroList.get(position).setSelectedC(true);
                }
            }

            if (!hero.isSelectedB() &&!hero.isSelectedFalseA()) {

                if (givenAns.equals("A") && correctAns.equals("B")) {
                    holder.mradio_one.setChecked(true);
                    holder.mradio_one.setButtonDrawable(R.drawable.select_red_check);
                    heroList.get(position).setSelectedFalseA(true);

                    holder.mradio_two.setChecked(true);
                    holder.mradio_two.setButtonDrawable(R.drawable.select_green_check);
                    heroList.get(position).setSelectedC(true);
                }

            }
            if (!hero.isSelectedB() &&!hero.isSelectedFalseA()) {

                if (givenAns.equals("D") && correctAns.equals("B")) {
                    holder.mradio_four.setChecked(true);
                    holder.mradio_four.setButtonDrawable(R.drawable.select_red_check);
                    heroList.get(position).setSelectedFalseA(true);

                    holder.mradio_two.setChecked(true);
                    holder.mradio_two.setButtonDrawable(R.drawable.select_green_check);
                    heroList.get(position).setSelectedC(true);
                }


            }

             if (givenAns.equals("A") && correctAns.equals("C")) {
                holder.mradio_one.setChecked(true);
                holder.mradio_one.setButtonDrawable(R.drawable.select_red_check);
                heroList.get(position).setSelectedFalseA(true);

                holder.mradio_three.setChecked(true);
                holder.mradio_three.setButtonDrawable(R.drawable.select_green_check);
                heroList.get(position).setSelectedC(true);
            }  if (givenAns.equals("B") && correctAns.equals("C")) {
                holder.mradio_two.setChecked(true);
                holder.mradio_two.setButtonDrawable(R.drawable.select_red_check);
                heroList.get(position).setSelectedFalseA(true);

                holder.mradio_three.setChecked(true);
                holder.mradio_three.setButtonDrawable(R.drawable.select_green_check);
                heroList.get(position).setSelectedC(true);
            }  if (givenAns.equals("D") && correctAns.equals("C")) {
                holder.mradio_four.setChecked(true);
                holder.mradio_four.setButtonDrawable(R.drawable.select_red_check);
                heroList.get(position).setSelectedFalseA(true);

                holder.mradio_three.setChecked(true);
                holder.mradio_three.setButtonDrawable(R.drawable.select_green_check);
                heroList.get(position).setSelectedC(true);
            }  if (givenAns.equals("A") && correctAns.equals("D")) {
                holder.mradio_one.setChecked(true);
                holder.mradio_one.setButtonDrawable(R.drawable.select_red_check);
                heroList.get(position).setSelectedFalseA(true);

                holder.mradio_four.setChecked(true);
                holder.mradio_four.setButtonDrawable(R.drawable.select_green_check);
                heroList.get(position).setSelectedC(true);
            }  if (givenAns.equals("B") && correctAns.equals("D")) {
                holder.mradio_two.setChecked(true);
                holder.mradio_two.setButtonDrawable(R.drawable.select_red_check);
                heroList.get(position).setSelectedFalseA(true);

                holder.mradio_four.setChecked(true);
                holder.mradio_four.setButtonDrawable(R.drawable.select_green_check);
                heroList.get(position).setSelectedC(true);
            }  if (givenAns.equals("C") && correctAns.equals("D")) {
                holder.mradio_three.setChecked(true);
                holder.mradio_three.setButtonDrawable(R.drawable.select_red_check);
                heroList.get(position).setSelectedFalseA(true);

                holder.mradio_four.setChecked(true);
                holder.mradio_four.setButtonDrawable(R.drawable.select_green_check);
                heroList.get(position).setSelectedC(true);
            }
        } else {

            if (!hero.isSelectedA()) {
                if (givenAns.equals("A") && correctAns.equals("A")) {
                    holder.mradio_one.setChecked(true);
                    holder.mradio_one.setButtonDrawable(R.drawable.select_green_check);
                    heroList.get(position).setSelectedA(true);
                }
            }
            if (!hero.isSelectedB()) {
                if (givenAns.equals("B") && correctAns.equals("B")) {
                    holder.mradio_two.setChecked(true);
                    holder.mradio_two.setButtonDrawable(R.drawable.select_green_check);
                    heroList.get(position).setSelectedB(true);
                }
            }
            if (!hero.isSelectedC()) {
                if (givenAns.equals("C") && correctAns.equals("C")) {
                    holder.mradio_three.setChecked(true);
                    holder.mradio_three.setButtonDrawable(R.drawable.select_green_check);
                    heroList.get(position).setSelectedC(true);
                }
            }
            if (!hero.isSelectedD()) {
                if (givenAns.equals("D") && correctAns.equals("D")) {
                    holder.mradio_four.setChecked(true);
                    holder.mradio_four.setButtonDrawable(R.drawable.select_green_check);
                    heroList.get(position).setSelectedD(true);
                }
            }
        }

    }


    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class HeroViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, no_of_que, duration, start_now, question_counting, question, explanation;
        RadioButton mradio_one, mradio_two, mradio_three, mradio_four;
        RadioGroup rgAnswers;

        TextView TvQuestion, TvOptionA, TvOptionB, TvOptionC, TvOptionD;
        ImageView ImgQuestion, ImgOptionA, ImgOptionB, ImgOptionC, ImgOptionD;


        public HeroViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);

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
            question = itemView.findViewById(R.id.question);

            title = itemView.findViewById(R.id.title);
            no_of_que = itemView.findViewById(R.id.no_of_que);
            duration = itemView.findViewById(R.id.duration);
            start_now = itemView.findViewById(R.id.start_now);
            question_counting = itemView.findViewById(R.id.question_counting);

            mradio_one = itemView.findViewById(R.id.radio_one);
            mradio_two = itemView.findViewById(R.id.radio_two);
            mradio_three = itemView.findViewById(R.id.radio_three);
            mradio_four = itemView.findViewById(R.id.radio_four);
            explanation = itemView.findViewById(R.id.explanation);
        }

    }
}



