package com.Padhantueducation.view_section.ui.tools_;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Padhantueducation.R;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ToolsFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);

        return root;
    }
}