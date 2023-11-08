package com.example.rss_reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class DatailActivity extends AppCompatActivity {

    private WebView webView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datail);
        webView = (WebView) findViewById(R.id.detail);
        textView = (TextView) findViewById(R.id.title_item) ;
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        String title = intent.getStringExtra("title");
        textView.setText(title);
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());
    }
}