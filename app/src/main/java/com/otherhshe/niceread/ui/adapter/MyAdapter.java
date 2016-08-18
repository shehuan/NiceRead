package com.otherhshe.niceread.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.otherhshe.niceread.R;
import com.otherhshe.niceread.data.GankItemData;
import com.otherhshe.niceread.ui.adapter.baseadapter.RefreshAdapter;
import com.otherhshe.niceread.ui.adapter.baseadapter.ViewHolder;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/18 16:53
 */
public class MyAdapter extends RefreshAdapter<GankItemData> {
    public MyAdapter(Context context, List<GankItemData> datas) {
        super(context, datas);
    }

    @Override
    protected void convert(RecyclerView.ViewHolder viewHolder, GankItemData gankItemData) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.setText(R.id.gank_item_desc, gankItemData.getDesc());

        String who = TextUtils.isEmpty(gankItemData.getWho()) ? "null" : gankItemData.getWho();
        holder.setText(R.id.gank_item_who, who);

        holder.setText(R.id.gank_item_publishedat, gankItemData.getPublishedAt().substring(0, 10));

        if (gankItemData.getUrl().contains("github")) {
            holder.setBgRes(R.id.gank_item_icon, R.drawable.github);
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_gank_layout;
    }

}
