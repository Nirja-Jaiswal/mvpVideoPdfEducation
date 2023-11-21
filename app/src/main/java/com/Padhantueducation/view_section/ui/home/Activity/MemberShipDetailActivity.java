package com.Padhantueducation.view_section.ui.home.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.Padhantueducation.R;

public class MemberShipDetailActivity extends AppCompatActivity {
String mclass_name,mprice,mmrp,mdescription,mid,mcource_id;
TextView tv_class_name,tv_description,tv_mrp,tv_price,tvPayNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_ship_detail);
        initView();

        if (getIntent()!=null){
            mclass_name=getIntent().getStringExtra("class_name");
            mprice=getIntent().getStringExtra("price");
            mmrp=getIntent().getStringExtra("mrp");
            mdescription=getIntent().getStringExtra("description");
            mid=getIntent().getStringExtra("id");
            mcource_id=getIntent().getStringExtra("cource_id");

            tv_class_name.setText(mclass_name);
            tv_description.setText(mdescription);

            tv_mrp.setText(mmrp+" Rs");
            tv_mrp.setPaintFlags(tv_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tv_price.setText(mprice+" Rs");

        }
        tvPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemberShipDetailActivity.this,PaymentGatewayActivity.class)
                        .putExtra("total_price",mprice)
                        .putExtra("cource_id",mcource_id)
                        .putExtra("id",mid));
            }
        });




    }

    private void initView() {
        tv_class_name=findViewById(R.id.tv_class_name);
        tv_description=findViewById(R.id.tv_description);
        tv_mrp=findViewById(R.id.tv_mrp);
        tv_price=findViewById(R.id.tv_price);
        tvPayNow=findViewById(R.id.tvPayNow);
    }


}