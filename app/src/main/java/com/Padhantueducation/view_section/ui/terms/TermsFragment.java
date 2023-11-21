package com.Padhantueducation.view_section.ui.terms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.Padhantueducation.R;

import static com.Padhantueducation.remote.APIUrl.TERMS_CONDITION;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsFragment extends Fragment {
    View view;

    public TermsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_terms, container, false);
        WebView webView = (WebView) view.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(TERMS_CONDITION);



        return view;
    }
}
