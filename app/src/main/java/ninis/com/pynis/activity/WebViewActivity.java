package ninis.com.pynis.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ninis.com.pynis.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private String loadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if( getIntent().hasExtra("URL") ) {
            loadUrl = getIntent().getStringExtra("URL");
        }

        setWebView();
    }

    private void setWebView() {
        webView = (WebView) findViewById(R.id.wv_main);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClientClass());

        webView.loadUrl(loadUrl);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }
}
