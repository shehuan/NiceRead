package com.otherhshe.niceread.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.otherhshe.niceread.ui.fragemnt.BaseFragment;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/17 17:54
 */
public class GirlDetailAdapter extends FragmentPagerAdapter {
    public GirlDetailAdapter(FragmentManager fm) {
        super(fm);
    }

    private List<BaseFragment> fragments;

    public void setData(List<BaseFragment> fragments) {
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
