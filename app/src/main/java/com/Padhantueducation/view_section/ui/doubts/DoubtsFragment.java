package com.Padhantueducation.view_section.ui.doubts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.tabs.TabLayout;
import com.Padhantueducation.R;
import com.Padhantueducation.view_section.ui.helpLine.TabAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class DoubtsFragment extends Fragment   {

    ViewPager viewPager;
    TabLayout tabLayout;
    TabAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_doubts, container, false);


        startActivity(new Intent(getActivity(),DoubtsActivity.class));


        return root;
    }



}