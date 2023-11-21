package com.Padhantueducation.view_section.ui.home.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.Padhantueducation.R;

public class ClassRelatedMaterial extends AppCompatActivity {

    CardView card_view_hindi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_related_material);


        card_view_hindi=findViewById(R.id.card_view_hindi);
        card_view_hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ClassRelatedMaterial.this, ClassModulesActivity.class));

            }
        });






    }
}
