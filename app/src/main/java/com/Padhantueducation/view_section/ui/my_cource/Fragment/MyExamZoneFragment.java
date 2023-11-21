package com.Padhantueducation.view_section.ui.my_cource.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.Padhantueducation.view_section.view.ExamdetailsActivity;
import com.Padhantueducation.R;


public class MyExamZoneFragment extends Fragment {
    LinearLayout examdetails;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_exam_zone, container, false);

        examdetails=view.findViewById(R.id.examdetails);

        examdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ExamdetailsActivity.class));
            }
        });

    return view;
    }
}
