package com.Padhantueducation.view_section.ui.home.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.Padhantueducation.R;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class PDFViewActivity extends AppCompatActivity implements DownloadFile.Listener{
    LinearLayout root;
    RemotePDFViewPager remotePDFViewPager;
    EditText etPdfUrl;
    Button btnDownload;
    PDFPagerAdapter adapter;
    String url,chapter_name;
    TextView tv_header_comment;
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);

        root = findViewById(R.id.remote_pdf_root);
        if (getIntent() != null)
        {
            url=getIntent().getStringExtra("url");
            chapter_name=getIntent().getStringExtra("chapter_name");

        }

        tv_header_comment=findViewById(R.id.tv_header_comment);
        tv_header_comment.setText(chapter_name);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });




        setDownloadButtonListener();






    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter.close();
        }
    }

    protected void setDownloadButtonListener() {
        final Context ctx = this;
        final DownloadFile.Listener listener = this;
        remotePDFViewPager = new RemotePDFViewPager(ctx, url, listener);
        remotePDFViewPager.setId(R.id.pdfViewPager);

    }







    protected String getUrlFromEditText() {
        return etPdfUrl.getText().toString().trim();
    }

    public static void open(Context context) {
        Intent i = new Intent(context, PDFViewActivity.class);
        context.startActivity(i);
    }

    public void showDownloadButton() {
        btnDownload.setVisibility(View.VISIBLE);
    }

    public void hideDownloadButton() {
        btnDownload.setVisibility(View.INVISIBLE);
    }

    public void updateLayout() {
        root.removeAllViewsInLayout();
       // root.addView(etPdfUrl, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
      //  root.addView(btnDownload, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        root.addView(remotePDFViewPager, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter = new PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        updateLayout();
       // showDownloadButton();
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
       // showDownloadButton();
    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}