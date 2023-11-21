package com.Padhantueducation.view_section.ui.home.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.Padhantueducation.R;
import com.Padhantueducation.view_section.MainActi.MainActivity;

public class AnswerResultActivity extends AppCompatActivity {
ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_result);
        iv_back=findViewById(R.id.iv_back);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnswerResultActivity.this, MainActivity.class));
            }
        });



    }
}
