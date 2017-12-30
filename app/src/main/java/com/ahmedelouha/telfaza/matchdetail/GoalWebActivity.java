package com.ahmedelouha.telfaza.matchdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ahmedelouha.telfaza.R;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by raaja on 30-12-2017.
 */

public class GoalWebActivity extends AppCompatActivity implements GoalWebContract.View {

    public static final String GOAL_WEB_URL="goal_web_url";

    GoalWebContract.Presenter presenter;
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    WebView webView;
    String goalUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_web);
        webView = (WebView) findViewById(R.id.web);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark);
        try {
            goalUrl = URLDecoder.decode(getIntent().getStringExtra(GOAL_WEB_URL),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new GoalWebPresenter(this);
        setListener();
    }

    void setListener(){
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoader();
            }
        });

        getSupportActionBar().setTitle(goalUrl);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.openGoalPage();
            }
        });

    }

    @Override
    public void loadGoalPage() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.loadUrl(goalUrl);
    }

    @Override
    public void setPresenter(GoalWebContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoader() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoader() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.openGoalPage();
    }
}
