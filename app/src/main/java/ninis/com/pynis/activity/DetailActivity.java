package ninis.com.pynis.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ninis.com.pynis.Defines;
import ninis.com.pynis.R;
import ninis.com.pynis.VolleyMgr;
import ninis.com.pynis.data.ClienPostDetailData;

public class DetailActivity extends AppCompatActivity {

    private String loadUrl;
    private ProgressBar progressBar;
    private LinearLayout layoutBottom;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        layoutBottom = (LinearLayout) findViewById(R.id.ll_detail_bottom_area);
        layoutBottom.setVisibility(View.GONE);

        if( getIntent().hasExtra("URL") ) {
            loadUrl = getIntent().getStringExtra("URL");
            getData();
        }
    }

    private void getData() {
        if( loadUrl == null || loadUrl.length() < 1 )
            return;

        String requestUrl = Defines.URL_CLIEN_PARK_POST_DETAIL;
        try {
            requestUrl += URLEncoder.encode(loadUrl, "utf-8");

            StringRequest stringRequest = new StringRequest(requestUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                ClienPostDetailData clienPostDetailData = new Gson().fromJson(response, ClienPostDetailData.class);
                                if( clienPostDetailData != null ) {
                                    setLayout(clienPostDetailData);
                                }
                            } catch (Exception e) {

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            );
            VolleyMgr.getInstance(getApplicationContext()).getRequestQueue().add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setLayout(ClienPostDetailData data) {
        TextView tvTitle = (TextView) findViewById(R.id.tv_detail_title);
        TextView tvInfo = (TextView) findViewById(R.id.tv_detail_info);
        TextView tvText = (TextView) findViewById(R.id.tv_detail_txt);

        tvTitle.setText(data.getData().getTitle());
        tvInfo.setText(data.getData().getViewinfo());
        tvText.setText(data.getData().getText());

        progressBar.setVisibility(View.GONE);
        layoutBottom.setVisibility(View.VISIBLE);
    }
}
