package com.otherhshe.niceread.ui.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.otherhshe.niceread.R;
import com.otherhshe.niceread.data.GankItemData;
import com.otherhshe.niceread.ui.adapter.baseadapter.BaseAdapter;
import com.otherhshe.niceread.ui.adapter.baseadapter.ViewHolder;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/18 16:53
 */
public class GankItemAdapter extends BaseAdapter<GankItemData> {


    public GankItemAdapter(Context context, List<GankItemData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, GankItemData gankItemData) {
        holder.setText(R.id.gank_item_desc, gankItemData.getDesc());

        String who = TextUtils.isEmpty(gankItemData.getWho()) ? "null" : gankItemData.getWho();
        holder.setText(R.id.gank_item_who, who);

        holder.setText(R.id.gank_item_publishedat, gankItemData.getPublishedAt().substring(0, 10));

        String url = gankItemData.getUrl();
        int iconId;
        if (url.contains("github")) {
            iconId = R.drawable.github;
        } else if (url.contains("jianshu")) {
            iconId = R.drawable.jianshu;
        } else if (url.contains("csdn")) {
            iconId = R.drawable.csdn;
        } else if (url.contains("miaopai")) {
            iconId = R.drawable.miaopai;
        } else if (url.contains("acfun")) {
            iconId = R.drawable.acfun;
        } else if (url.contains("bilibili")) {
            iconId = R.drawable.bilibili;
        } else if (url.contains("youku")) {
            iconId = R.drawable.youku;
        } else if (url.contains("weibo")) {
            iconId = R.drawable.weibo;
        } else if (url.contains("weixin")) {
            iconId = R.drawable.weixin;
        } else {
            iconId = R.drawable.web;
        }

        holder.setBgRes(R.id.gank_item_icon, iconId);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_gank_layout;
    }

}
