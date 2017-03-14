package com.otherhshe.niceread.ui.fragemnt;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.otherhshe.niceread.R;
import com.otherhshe.niceread.utils.ImageLoader;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Author: Othershe
 * Time: 2016/8/17 17:36
 */
public class GirlDetailFragment extends BaseFragment {
    private static final String URL = "url";

    private String mUrl;

    @BindView(R.id.girl_detail_iv)
    PhotoView mImageView;

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_girl_detail;
    }

    @Override
    protected void initView() {
        ImageLoader.load(mActivity, mUrl, mImageView);
        PhotoViewAttacher attacher = new PhotoViewAttacher(mImageView);
        attacher.update();
    }

    @Override
    protected void initData() {
        if (getArguments() == null) {
            return;
        }
        mUrl = getArguments().getString(URL);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public static GirlDetailFragment newInstance(String url) {
        GirlDetailFragment fragment = new GirlDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putString(URL, url);
        fragment.setArguments(arguments);
        return fragment;
    }
}
