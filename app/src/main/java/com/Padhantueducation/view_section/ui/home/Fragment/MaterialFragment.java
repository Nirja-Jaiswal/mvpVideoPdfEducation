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
import com.Padhantueducation.view_section.ui.home.Models.MaterialResult;
import com.Padhantueducation.view_section.ui.home.adapter.MaterialAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialFragment extends Fragment {
    View view;
    String chapter_id;
    RecyclerView recyclerView;
    Session session;
    MaterialAdapter materialAdapter;
    public MaterialFragment(String id) {
        this.chapter_id=id;
    }





    public MaterialFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_material, container, false);
        recyclerView = view.findViewById(R.id.recycleview);
        session=new Session(getActivity());


         get_Material_list(chapter_id);





    return view;
    }

    private void get_Material_list(String chapter_id) {
        Utils.showProgressDialog(getActivity());
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<MaterialResult> userCall = service.getMaterialList(session.getUserId(), Utils.Get_Device_ID(getActivity()),chapter_id);

        userCall.enqueue(new Callback<MaterialResult>() {
            @Override
            public void onResponse(Call<MaterialResult> call, Response<MaterialResult> response) {

                try{
                    if (response!=null){
                        Log.e("res_i_report",""+response.body().getData());
                           Utils.dismissProgressDialog();
                        if (response.body().getResult().equals("true")){
                            // Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();

                            materialAdapter = new MaterialAdapter(getActivity(),response.body().getData());
                            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                            //     recycleview_subject.setLayoutManager(new GridLayoutManager(ChapterActivity.this,2));
                            recyclerView.setAdapter(materialAdapter);
                            materialAdapter.notifyDataSetChanged();




                        }else {



                            if (response.body().getMsg().equals("invalid_token")) {
                                Utils.Logout_Api(session.getUserId(), getActivity());
                            }

                         //   Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        }

                    }
                }catch (Exception e){
                    Utils.dismissProgressDialog();
                    Log.e("error_i_report", e.getMessage());
                }
                //  progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<MaterialResult> call, Throwable t) {
                Utils.dismissProgressDialog();
                Log.e("error_i_report1",t.getMessage());
                //Toast.makeText(AllCountries.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}
