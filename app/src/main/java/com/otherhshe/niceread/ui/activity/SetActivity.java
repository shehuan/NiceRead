package com.otherhshe.niceread.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.otherhshe.niceread.R;

import butterknife.BindView;

/**
 * Author: Othershe
 * Time: 2016/8/22 17:45
 */
public class SetActivity extends BaseActivity {
    @BindView(R.id.set_toolbar)
    Toolbar mToolbar;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initView() {
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
    protected void initData() {

    }
}
