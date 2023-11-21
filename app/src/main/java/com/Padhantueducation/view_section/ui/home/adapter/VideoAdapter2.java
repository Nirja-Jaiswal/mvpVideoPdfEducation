package com.Padhantueducation.view_section.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.Padhantueducation.R;
import com.Padhantueducation.view_section.ui.home.Activity.VideoViewActivity;
import com.Padhantueducation.view_section.ui.home.Models.VideoModel;

import java.util.List;

import static com.Padhantueducation.remote.APIUrl.CLASS_VIDEO;
import static com.Padhantueducation.remote.APIUrl.VIDEO_THUMBNIL;


public class VideoAdapter2  extends RecyclerView.Adapter<com.Padhantueducation.view_section.ui.home.adapter.VideoAdapter2.ViewHolder> {
        public List<VideoModel> checkListModelList;
        VideoModel checkListModel;
        public Context context;
        View viewlike;
        String limit;



        public VideoAdapter2(List<VideoModel> checkListModelArrayList, Context activity) {
            context = activity;
            checkListModelList = checkListModelArrayList;


        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tv_name,subject_name,chapter_name,standard;
            LinearLayout mLinearLayout;
            // VideoView video_view;
            CardView card_video;
            ImageView image_view_thm,image_play;

            public ViewHolder(View view) {
                super(view);
                viewlike = view;

                tv_name = viewlike.findViewById(R.id.tv_name);
                card_video = viewlike.findViewById(R.id.card_video);
                image_view_thm = viewlike.findViewById(R.id.image_view_thm);
                image_play = viewlike.findViewById(R.id.image_play);
                subject_name=viewlike.findViewById(R.id.subject_name);
                chapter_name=viewlike.findViewById(R.id.chapter_name);
                standard=viewlike.findViewById(R.id.standard);





            }
        }



        @Override
        public com.Padhantueducation.view_section.ui.home.adapter.VideoAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video_item2, parent, false);
            return new com.Padhantueducation.view_section.ui.home.adapter.VideoAdapter2.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final com.Padhantueducation.view_section.ui.home.adapter.VideoAdapter2.ViewHolder viewHolder, final int position) {
            // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);

            if (checkListModelList.size() > 0) {
                checkListModel = checkListModelList.get(position);
                viewHolder.subject_name.setText(checkListModel.getSubject_name());
                viewHolder.chapter_name.setText(checkListModel.getChapter_name());
                viewHolder.standard.setText(checkListModel.getClass_name());
             //   final String img_url2=CLASS_VIDEO+checkListModelList.get(position).getImage();
                final String img_url2=VIDEO_THUMBNIL+checkListModelList.get(position).getImage();

                Glide.with(context)
                        .load(img_url2)
                        .into(viewHolder.image_view_thm);



                if (checkListModelList.get(position).getVideo().equalsIgnoreCase("")){
                    final String vid_url=CLASS_VIDEO+checkListModelList.get(position).getVideo();

                    //************find thumbnails image
                    final String img_url=CLASS_VIDEO+checkListModelList.get(position).getImage();
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.isMemoryCacheable();

                    Glide.with(context).setDefaultRequestOptions(requestOptions)
                            .load(img_url)
                            .into(viewHolder.image_view_thm);
                }


                viewHolder.card_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {



                            if (checkListModelList.get(position).getVideo()!=null &&
                                    !checkListModelList.get(position).getVideo().equalsIgnoreCase("")){
                                String vidAddress = CLASS_VIDEO+checkListModelList.get(position).getVideo();



                                context.startActivity(new Intent(context, VideoViewActivity.class)
                                        .putExtra("subject",checkListModelList.get(position).getSubject_name())
                                        .putExtra("chapter",checkListModelList.get(position).getChapter_name())
                                        .putExtra("class",checkListModelList.get(position).getClass_name())

                                        .putExtra("video_url",vidAddress)

                                );



                            }




                            else {
                                Toast.makeText(context, R.string.no_video, Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){


                        }



                    }
                });



            } else {
                Toast.makeText(context, R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
            }




        }

        @Override
        public int getItemCount() {

                return checkListModelList.size();



        }}

    


