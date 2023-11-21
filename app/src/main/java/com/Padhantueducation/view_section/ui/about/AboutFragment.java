package com.Padhantueducation.view_section.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.Padhantueducation.R;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static com.Padhantueducation.remote.APIUrl.About_us;

public class AboutFragment extends Fragment {
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         root = inflater.inflate(R.layout.fragment_about, container, false);

        WebView webView = (WebView) root.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(About_us);

        return root;
    }
}