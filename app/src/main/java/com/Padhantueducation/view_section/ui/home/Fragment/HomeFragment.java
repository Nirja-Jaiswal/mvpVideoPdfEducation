package com.Padhantueducation.view_section.ui.home.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.ui.home.Activity.AllVideoListActivity;
import com.Padhantueducation.view_section.ui.home.Models.BannerModel;
import com.Padhantueducation.view_section.ui.home.Models.ClassResult;
import com.Padhantueducation.view_section.ui.home.Models.Result;
import com.Padhantueducation.view_section.ui.home.Models.VideoResult;
import com.Padhantueducation.view_section.ui.home.adapter.BannerAdapter;
import com.Padhantueducation.view_section.ui.home.adapter.ClassAdapter;
import com.Padhantueducation.view_section.ui.home.adapter.ClassAdapter2;
import com.Padhantueducation.view_section.ui.home.adapter.VideoAdapter;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.Padhantueducation.CommonClasses.Utils.call_fun;

public class HomeFragment extends Fragment {


    CardView card_view;
    LinearLayout lin_letest_video;
    RecyclerView banner_recycleview, class_recycleview, recycler_video,yourcource_recycleview;
    Session session;
    BannerAdapter bannerAdapter;
    ClassAdapter classAdapter;
    VideoAdapter videoAdapter;
    ClassAdapter2 classAdapter2;

    List<BannerModel> bannerModelList;
    TextView letest_video, your_subject,user_subject;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_study, container, false);
        initview();

        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          call_fun(getActivity(), "1800-18002233");
            }
        });
        lin_letest_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getActivity(), AllVideoListActivity.class));
            }
        });
        get_Video(10);
        get_user_subject();
        get_Class();
        get_Banner();
        Log.e(session.getUserId(),Utils.Get_Device_ID(getActivity()));
        return root;

    }

    private void get_user_subject() {
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<ClassResult> userCall = service.MY_COURSE(session.getUserId(), Utils.Get_Device_ID(getActivity()));
            userCall.enqueue(new Callback<ClassResult>() {
                @Override
                public void onResponse(Call<ClassResult> call, Response<ClassResult> response) {
                    try {
                        if (response != null) {
                            Log.e("res_i_report", "" + response.body().getData());
                            if (response.body().getResult().equals("true")) {
                                user_subject.setVisibility(View.VISIBLE);
                                classAdapter2 = new ClassAdapter2(getActivity(), response.body().getData());
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                                yourcource_recycleview.setLayoutManager(mLayoutManger);
                                yourcource_recycleview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                                yourcource_recycleview.setAdapter(classAdapter2);
                                classAdapter2.notifyDataSetChanged();
                            } else {
                                if (response.body().getMsg().equals("invalid_token")) {
                                    Utils.Logout_Api(session.getUserId(),getActivity());
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("error_i_report", e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ClassResult> call, Throwable t) {
                    // progressDialog.dismiss();
                    Log.e("error_i_report1", t.getMessage());
                    //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });



    }

    private void initview() {
        user_subject=root.findViewById(R.id.user_subject);
        letest_video = root.findViewById(R.id.letest_video);
        your_subject = root.findViewById(R.id.your_subject);
        card_view = root.findViewById(R.id.card_view);
        banner_recycleview = root.findViewById(R.id.recycleview);
        class_recycleview = root.findViewById(R.id.class_recycleview);
        recycler_video = root.findViewById(R.id.recycler_video);
        session = new Session(getActivity());
        lin_letest_video=root.findViewById(R.id.lin_letest_video);
        yourcource_recycleview=root.findViewById(R.id.yourcource_recycleview);
    }

    private void get_Video(final int limit) {
        //  Utils.showProgressDialog(getActivity());
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<VideoResult> userCall = service.Get_Video(session.getUserId(), Utils.Get_Device_ID(getActivity()));
        userCall.enqueue(new Callback<VideoResult>() {
            @Override
            public void onResponse(Call<VideoResult> call, Response<VideoResult> response) {

                try {
                    if (response != null) {
                        //    dismissProgressDialog();
                        Log.e("home video data ", "" + response.body().getData());

                        if (response.body().getResult().equals("true")) {
                            // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();


                            videoAdapter = new VideoAdapter(response.body().getData(), getActivity(),limit);
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recycler_video.setLayoutManager(mLayoutManger);
                            recycler_video.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                            recycler_video.setAdapter(videoAdapter);
                            videoAdapter.notifyDataSetChanged();

                            letest_video.setVisibility(View.VISIBLE);
                            your_subject.setVisibility(View.VISIBLE);
                            card_view.setVisibility(View.VISIBLE);


                        } else {


                            if (response.body().getMsg().equals("invalid_token")) {

                                Utils.Logout_Api(session.getUserId(),getActivity());
                            }

                            Toast.makeText(getActivity(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        }

                    }
                } catch (Exception e) {
                    //     dismissProgressDialog();
                    Log.e("error_i_report", e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<VideoResult> call, Throwable t) {
                //    dismissProgressDialog();
                Log.e("error_i_report1", t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void get_Class() {
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ClassResult> userCall = service.Get_ALL_CLASSES(session.getUserId(), Utils.Get_Device_ID(getActivity()));

        userCall.enqueue(new Callback<ClassResult>() {
            @Override
            public void onResponse(Call<ClassResult> call, Response<ClassResult> response) {

                try {
                    if (response != null) {
                        Log.e("res_i_report", "" + response.body().getData());

                        if (response.body().getResult().equals("true")) {
                            // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();

                            classAdapter = new ClassAdapter(getActivity(), response.body().getData());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            class_recycleview.setLayoutManager(mLayoutManger);
                            class_recycleview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                            class_recycleview.setAdapter(classAdapter);
                            classAdapter.notifyDataSetChanged();


                        } else {


                            if (response.body().getMsg().equals("invalid_token")) {

                                Utils.Logout_Api(session.getUserId(),getActivity());
                            }



                            Toast.makeText(getActivity(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        }

                    }
                } catch (Exception e) {
                    Log.e("error_i_report", e.getMessage());
                }
                //  progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ClassResult> call, Throwable t) {
                // progressDialog.dismiss();
                Log.e("error_i_report1", t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void get_Banner() {

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<Result> userCall = service.Get_Banner(session.getUserId(), Utils.Get_Device_ID(getActivity()));

        userCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                try {
                    if (response != null) {
                        Log.e("res_i_report", "" + response.body().getData());

                        if (response.body().getResult().equals("true")) {
                            // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();

                            bannerAdapter = new BannerAdapter(getActivity(), response.body().getData());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            banner_recycleview.setLayoutManager(mLayoutManger);
                            banner_recycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                            banner_recycleview.setAdapter(bannerAdapter);
                            bannerAdapter.notifyDataSetChanged();

                        } else {

                            if (response.body().getMsg().equals("invalid_token")) {
                                Utils.Logout_Api(session.getUserId(),getActivity());
                            }

                            Toast.makeText(getActivity(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        }

                    }
                } catch (Exception e) {
                    Log.e("error_i_report", e.getMessage());
                }
                //  progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                // progressDialog.dismiss();
                Log.e("error_i_report1", t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


}