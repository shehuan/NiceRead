package com.otherhshe.niceread.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.otherhshe.niceread.R;
import com.otherhshe.niceread.data.GankItemData;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;

/**
 * Author: Othershe
 * Time: 2016/8/15 11:14
 */
public class GankItemDetailActivity extends BaseActivity {
    private GankItemData mGankItemData;

    @BindView(R.id.gank_item_detail_toolbar)
    Toolbar mToolBar;

    @BindView(R.id.gank_item_detail_webview)
    WebView mWebView;

    @BindView(R.id.gank_item_detail_loading)
    AVLoadingIndicatorView mLoading;

    @BindView(R.id.gank_item_detail_progress)
    ProgressBar mProgressBar;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_gank_item_detail;
    }

    @Override
    protected void initView() {

        mGankItemData = getIntent().getParcelableExtra("gank_item_data");

        initToolbar();
        initWebView();
    }

    private void initWebView() {
        final WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setBlockNetworkImage(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                String desc = mGankItemData.getDesc();
                desc = desc.length() > 10 ? desc.substring(0, 10) + "..." : desc;
                mToolBar.setTitle(desc);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoading.hide();
                settings.setBlockNetworkImage(false);
            }
        });
        mWebView.loadUrl(mGankItemData.getUrl());
    }

    private void initToolbar() {
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
