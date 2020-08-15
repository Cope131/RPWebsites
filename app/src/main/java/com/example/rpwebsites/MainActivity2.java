package com.example.rpwebsites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

        WebView wv;
        TextView tv;
        Button btnRefresh;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main2);

                //no action bar
                getSupportActionBar().hide();

                wv = findViewById(R.id.webView);
                tv = findViewById(R.id.textViewURL);
                btnRefresh = findViewById(R.id.buttonRefresh);

                //url
                Intent intentReceived = getIntent();
                String url = intentReceived.getStringExtra("url");
                tv.setText(url);
                wv.loadUrl(url);

                //extra
                WebSettings s = wv.getSettings();
                s.setBuiltInZoomControls(true);
                s.setJavaScriptEnabled(true);

                btnRefresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                wv.reload();
                        }
                });
        }

}