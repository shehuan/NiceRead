package com.otherhshe.niceread.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.otherhshe.niceread.ui.fragemnt.BaseMvpFragment;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/12 16:02
 */
public class TypePageAdapter extends FragmentPagerAdapter {
    private List<BaseMvpFragment> fragments;
    private List<String> titles;

    public TypePageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<BaseMvpFragment> fragments, List<String> titles) {
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
