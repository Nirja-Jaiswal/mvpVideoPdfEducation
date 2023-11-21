package com.Padhantueducation.view_section.ui.doubts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.Padhantueducation.R;
import com.Padhantueducation.view_section.MainActi.MainActivity;
import com.Padhantueducation.view_section.ui.helpLine.TabAdapter;

public class DoubtsActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    TabAdapter adapter;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubts);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(DoubtsActivity.this, getSupportFragmentManager());
        adapter.addFragment(new DiscussionForumFragment(), "Doubts Forum");
        adapter.addFragment(new MyQuestionFragment(), "Discussion");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        iv_back=findViewById(R.id.iv_back);



        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DoubtsActivity.this, MainActivity.class));

            }
        });





    }
}