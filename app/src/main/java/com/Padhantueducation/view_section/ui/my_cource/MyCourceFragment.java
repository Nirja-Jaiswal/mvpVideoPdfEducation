package com.Padhantueducation.view_section.ui.my_cource;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.tabs.TabLayout;
import com.Padhantueducation.R;
import com.Padhantueducation.view_section.ui.my_cource.Fragment.MyExamZoneFragment;
import com.Padhantueducation.view_section.ui.my_cource.Fragment.MyNotesFragment;
import com.Padhantueducation.view_section.ui.my_cource.Fragment.MyVideoFragment;
import com.Padhantueducation.view_section.ui.doubts.MyQuestionFragment;
import com.Padhantueducation.view_section.ui.home.adapter.TabAdapter2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MyCourceFragment extends Fragment {

    private TabAdapter2 adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mycource, container, false);





        viewPager = (ViewPager) root.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        adapter = new TabAdapter2(getActivity(), getFragmentManager());
        adapter.addFragment(new MyVideoFragment(), "Video");
        adapter.addFragment(new MyNotesFragment(), "Notes");
        adapter.addFragment(new MyQuestionFragment(), "Question paper");
        adapter.addFragment(new MyExamZoneFragment(), "Exam Zone");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        return root;
    }
}