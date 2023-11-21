package com.Padhantueducation.view_section.ui.mymembership;

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
import com.Padhantueducation.models.MyMembershipResult;
import com.Padhantueducation.remote.APIService;
import com.Padhantueducation.remote.ApiClient;
import com.Padhantueducation.view_section.ui.mymembership.adapter.MyMembershipListAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMembershipListFragment extends Fragment {
    View view;
    RecyclerView recycleview;
    Session session;
    MyMembershipListAdapter myMembershipListAdapter;

    public MyMembershipListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_packagelist, container, false);
        recycleview = view.findViewById(R.id.recycleview);
        session = new Session(getActivity());


        get_my_membership_list();

        return view;
    }

    private void get_my_membership_list() {
        Utils.showProgressDialog(getActivity());
            APIService service = ApiClient.getClient().create(APIService.class);
            Call<MyMembershipResult> userCall = service.Get_user_course_plan(session.getUserId(), Utils.Get_Device_ID(getActivity()));
            userCall.enqueue(new Callback<MyMembershipResult>() {
                @Override
                public void onResponse(Call<MyMembershipResult> call, Response<MyMembershipResult> response) {
                    try{
                        if (response!=null){

                            Utils.dismissProgressDialog();
                            Log.e("res_i_report",""+response.body().getData());
                            if (response.body().getResult().equals("true")){
                                myMembershipListAdapter = new MyMembershipListAdapter(getActivity(),response.body().getData());
                                RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                                recycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                                // recycleview_subject.setLayoutManager(new GridLayoutManager(ChapterActivity.this,2));
                                recycleview.setAdapter(myMembershipListAdapter);
                                myMembershipListAdapter.notifyDataSetChanged();
                            }else {

                                if (response.body().getMsg().equals("invalid_token")) {
                                    Utils.Logout_Api(session.getUserId(), getActivity());
                                }
                                Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (Exception e){
                        Utils.dismissProgressDialog();
                        Log.e("error_i_report", e.getMessage());
                    }
                }
                @Override
                public void onFailure(Call<MyMembershipResult> call, Throwable t) {
                    Utils.dismissProgressDialog();
                    Log.e("error_i_report1",t.getMessage());
                }
            });

        }


    }

