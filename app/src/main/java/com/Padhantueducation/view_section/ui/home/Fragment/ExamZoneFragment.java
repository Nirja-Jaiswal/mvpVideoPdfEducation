package com.Padhantueducation.view_section.ui.home.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.ui.home.Models.ExamResult;
import com.Padhantueducation.view_section.ui.home.adapter.ExamListAdapter;
import com.Padhantueducation.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamZoneFragment extends Fragment {
    LinearLayout not_found_img;
    String chapter_id;
    Session session;
    String cource_id;
    RecyclerView recycleview;
    ImageView iv_back;
    ExamListAdapter examListAdapter;


    public ExamZoneFragment() {
    }


    public ExamZoneFragment(String id) {
        this.chapter_id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_exam_zone, container, false);

        not_found_img=root.findViewById(R.id.not_found_img);
        recycleview = root.findViewById(R.id.recycleview);
        session = new Session(getActivity());
        iv_back = root.findViewById(R.id.iv_back);

        getExamList(chapter_id);



        return root;
    }

    private void getExamList(String chapter_id) {
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<ExamResult> userCall = service.getExamList(session.getUserId(), Utils.Get_Device_ID(getActivity()), chapter_id);
        userCall.enqueue(new Callback<ExamResult>() {
            @Override
            public void onResponse(Call<ExamResult> call, Response<ExamResult> response) {
                try {
                    if (response != null) {
                        Log.e("exam zone practise list", "" + response.body().getData());
                        if (response.body().getResult().equals("true")) {
                            // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();
                            examListAdapter = new ExamListAdapter(getActivity(), response.body().getData());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            //     recycleview_subject.setLayoutManager(new GridLayoutManager(ChapterActivity.this,2));
                            recycleview.setAdapter(examListAdapter);
                            examListAdapter.notifyDataSetChanged();

                        } else {
                            not_found_img.setVisibility(View.VISIBLE);


                            if (response.body().getMsg().equals("invalid_token")) {
                                Utils.Logout_Api(session.getUserId(), getActivity());
                            }


                          //  Toast.makeText(getActivity(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        }

                    }
                } catch (Exception e) {
                    Log.e("error_i_report", e.getMessage());
                }
                //  progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ExamResult> call, Throwable t) {
                // progressDialog.dismiss();
                Log.e("error_i_report1", t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


}





