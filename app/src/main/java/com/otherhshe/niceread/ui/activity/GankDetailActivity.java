package com.otherhshe.niceread.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.otherhshe.niceread.R;
import com.otherhshe.niceread.model.GankItemData;
import com.otherhshe.niceread.utils.CopyUtil;
import com.otherhshe.niceread.utils.ShareUtil;
import com.otherhshe.niceread.utils.SnackBarUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Method;

import butterknife.BindView;

/**
 * Author: Othershe
 * Time: 2016/8/15 11:14
 */
public class GankDetailActivity extends BaseActivity {
    private GankItemData mGankItemData;

    @BindView(R.id.gank_detail_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.gank_detail_webview)
    WebView mWebView;

    @BindView(R.id.gank_detail_loading)
    AVLoadingIndicatorView mLoading;

    @BindView(R.id.gank_detail_progress)
    ProgressBar mProgressBar;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_gank_detail;
    }

    @Override
    protected void initView() {
        initToolbar();
        initWebView();
    }

    @Override
    protected void initData() {
        mGankItemData = getIntent().getParcelableExtra("gank_item_data");
    }

    private void initWebView() {
        final WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setBlockNetworkImage(false);
        settings.setDomStorageEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                }
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
        String desc = mGankItemData.getDesc();
        desc = desc.length() > 10 ? desc.substring(0, 10) + "..." : desc;
        mToolbar.setTitle(desc);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gank_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_share:
                ShareUtil.share(mContext, mGankItemData.getDesc() + "\n" + mGankItemData.getUrl());
                break;
            case R.id.menu_copy:
                CopyUtil.copy(mContext, mGankItemData.getUrl());
                SnackBarUtil.show(mWebView, R.string.copy_success);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * menu隐藏后显示icon
     *
     * @param view
     * @param menu
     * @return
     */
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {

                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    protected void onDestroy() {
        mWebView.destroy();
        super.onDestroy();
    }
}
