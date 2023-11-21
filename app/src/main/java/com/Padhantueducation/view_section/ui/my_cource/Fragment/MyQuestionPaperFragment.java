package com.Padhantueducation.view_section.ui.my_cource.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Padhantueducation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyQuestionPaperFragment extends Fragment {

    public MyQuestionPaperFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_question_paper, container, false);
    }
}
