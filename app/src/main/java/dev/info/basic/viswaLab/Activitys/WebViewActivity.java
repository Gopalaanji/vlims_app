package dev.info.basic.viswaLab.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dev.info.basic.viswaLab.R;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        webView = (WebView) findViewById(R.id.webView);
        Intent intent = getIntent();
        imgUrl = intent.getStringExtra("pdf_id");
        loadWebView();
    }

    private void loadWebView() {
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.setWebChromeClient((new WebChromeClient()));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setPluginsEnabled(true);
        webView.loadUrl("https://docs.google.com/viewer?"+imgUrl);
//        webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + imgUrl);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }

}
