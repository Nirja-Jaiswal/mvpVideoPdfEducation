package com.Padhantueducation.view_section.ui.home.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.Padhantueducation.R;
import com.Padhantueducation.view_section.ui.home.Fragment.StudyZoneFragment;
import com.Padhantueducation.view_section.ui.home.Fragment.ExamZoneFragment;
import com.Padhantueducation.view_section.ui.home.Fragment.NotesFragment;
import com.Padhantueducation.view_section.ui.home.Fragment.MaterialFragment;
import com.Padhantueducation.view_section.ui.home.adapter.TabAdapter2;

public class ClassModulesActivity extends AppCompatActivity {

    private TabAdapter2 adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Context context;
    String chapter_name, id;
    TextView tv_header_comment;
    ImageView iv_back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_modules);

        context = ClassModulesActivity.this;
        tv_header_comment = findViewById(R.id.tv_header_comment);
        iv_back = findViewById(R.id.iv_back);
        if (getIntent() != null) {
            chapter_name = getIntent().getStringExtra("chapter_name");
            id = getIntent().getStringExtra("id");
            System.out.println("id check" + id);
            tv_header_comment.setText(chapter_name);
        }


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();


            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter2(context, getSupportFragmentManager());
        adapter.addFragment(new StudyZoneFragment(id), "study zone");
        adapter.addFragment(new NotesFragment(id), "Notes");
        adapter.addFragment(new MaterialFragment(id), "Materials");
        adapter.addFragment(new ExamZoneFragment(id), "Exam Zone");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
