package com.Padhantueducation.view_section.ui.doubts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Padhantueducation.CommonClasses.Utils;
import com.Padhantueducation.R;
import com.Padhantueducation.Session.Session;
import com.Padhantueducation.models.GetMyQueryListResult;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.ui.home.adapter.QuesryListAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyQuestionFragment extends Fragment {
    View view;
    RecyclerView recycleview;
    Session session;
    QuesryListAdapter quesryListAdapter;

    public MyQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_question, container, false);
        session = new Session(getActivity());
        recycleview = view.findViewById(R.id.recycleview);
        get_My_questionList();
        return view;
    }

    private void get_My_questionList() {
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<GetMyQueryListResult> userCall = service.GetMyQuestionList(session.getUserId(), Utils.Get_Device_ID(getActivity()));
        userCall.enqueue(new Callback<GetMyQueryListResult>() {
            @Override
            public void onResponse(Call<GetMyQueryListResult> call, Response<GetMyQueryListResult> response) {
                try {
                    if (response != null) {
                        Log.e("res_i_report", "" + response.body().getData());
                        if (response.body().getResult().equals("true")) {
                            // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();

                            quesryListAdapter = new QuesryListAdapter(getActivity(), response.body().getData());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            //     recycleview_subject.setLayoutManager(new GridLayoutManager(ChapterActivity.this,2));
                            recycleview.setAdapter(quesryListAdapter);
                            quesryListAdapter.notifyDataSetChanged();
                        } else {

                            if (response.body().getMsg().equals("invalid_token")) {
                                Utils.Logout_Api(session.getUserId(), getActivity());
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
            public void onFailure(Call<GetMyQueryListResult> call, Throwable t) {
                // progressDialog.dismiss();
                Log.e("error_i_report1", t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}
