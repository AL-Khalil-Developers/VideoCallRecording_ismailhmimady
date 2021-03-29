package com.Example.videocallrecorder.Main.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.Example.videocallrecorder.R;

public class WebActivity extends AppCompatActivity {
    private WebView webPrivacyPolicy;

    public void onBackPressed() {
        finish();
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.quore_web);
        this.webPrivacyPolicy = (WebView) findViewById(R.id.wvPrivacyPolicy);
        WebSettings settings = this.webPrivacyPolicy.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        this.webPrivacyPolicy.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView webView, int i, String str, String str2) {
                Toast.makeText(WebActivity.this, str, Toast.LENGTH_SHORT).show();
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (str.startsWith("http:") || str.startsWith("https:")) {
                    return false;
                }
                WebActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                return true;
            }
        });
        this.webPrivacyPolicy.loadUrl(getString(R.string.privacypolicylink));
    }
}
