package com.Padhantueducation.view_section.ui.home.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.ui.home.Models.StudyZoneResult;
import com.Padhantueducation.view_section.ui.home.adapter.StudyZoneAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudyZoneFragment extends Fragment {

    String chapter_id;
    View view;
    RecyclerView recycleview;
    Session session;
    public StudyZoneFragment(String id) {
    this.chapter_id=id;
    }
    StudyZoneAdapter studyZoneAdapter;

    public StudyZoneFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_class_wise_video, container, false);

        recycleview=view.findViewById(R.id.recycleview);
        session=new Session(getActivity());


         getStudyZone();



    return view;
    }

    private void getStudyZone() {
     //   Utils.showProgressDialog(getActivity());
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<StudyZoneResult> userCall = service.getVideoList(session.getUserId(), Utils.Get_Device_ID(getActivity()),chapter_id);

        userCall.enqueue(new Callback<StudyZoneResult>() {
            @Override
            public void onResponse(Call<StudyZoneResult> call, Response<StudyZoneResult> response) {
             //   Utils.dismissProgressDialog();
                try{
                    if (response!=null){
                        Log.e("res_i_report",""+response.body().getData());

                        if (response.body().getResult().equals("true")){
                            // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();

                            studyZoneAdapter = new StudyZoneAdapter(getActivity(),response.body().getData());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

                            //     recycleview_subject.setLayoutManager(new GridLayoutManager(ChapterActivity.this,2));
                            recycleview.setAdapter(studyZoneAdapter);
                            studyZoneAdapter.notifyDataSetChanged();




                        }else {
                           // Utils.dismissProgressDialog();
                            if (response.body().getMsg().equals("invalid_token")) {
                                Utils.Logout_Api(session.getUserId(), getActivity());
                            }
                        }
                    }
                }catch (Exception e){
                   // Utils.dismissProgressDialog();
                    Log.e("error_i_report", e.getMessage());
                }
                //  progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<StudyZoneResult> call, Throwable t) {

                Log.e("error_i_report1",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
