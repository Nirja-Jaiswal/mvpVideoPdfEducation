package com.Padhantueducation.view_section.ui.myexamhitory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.ui.home.Models.LiveExamResult;
import com.Padhantueducation.view_section.ui.home.adapter.MyExamHistoryAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyExamHistoryFragment extends Fragment {
    View view;
    ImageView iv_back;
    RecyclerView recycleview;
    Session session;
    MyExamHistoryAdapter liveExamResultAdapter2;


    public MyExamHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_my_exam_history, container, false);
    //    Log.e("save disv", Utils.Get_Device_ID(getActivity()));
        session=new Session(getActivity());
        recycleview=view.findViewById(R.id.recycleview);
        get_Live_Exam_List();
        return  view;
    }



    private void get_Live_Exam_List() {

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<LiveExamResult> userCall = service.User_score_history(session.getUserId(), Utils.Get_Device_ID(getActivity()));
        userCall.enqueue(new Callback<LiveExamResult>() {
            @Override
            public void onResponse(Call<LiveExamResult> call, Response<LiveExamResult> response) {
                try{
                    if (response!=null){
                        Log.e("res_i_report",""+response.body().getData());

                        if (response.body().getResult().equals("true")){
                            // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();
                            liveExamResultAdapter2 = new MyExamHistoryAdapter(getActivity(),response.body().getData(),"exam");
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            //recycleview_subject.setLayoutManager(new GridLayoutManager(ChapterActivity.this,2));
                            recycleview.setAdapter(liveExamResultAdapter2);
                            liveExamResultAdapter2.notifyDataSetChanged();

                        }else {

                            if (response.body().getMsg().equals("invalid_token")) {
                                Utils.Logout_Api(session.getUserId(), getActivity());
                            }
                            Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    Log.e("error_i_report", e.getMessage());
                }
                //  progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LiveExamResult> call, Throwable t) {
                Log.e("error_i_report1",t.getMessage());
            }
        });

    }






}