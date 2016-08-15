package com.otherhshe.niceread.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.otherhshe.niceread.R;
import com.otherhshe.niceread.data.GankItemData;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/14 13:16
 */
public class GankItemAdapter extends BaseQuickAdapter<GankItemData> {
    public GankItemAdapter(int layoutResId, List<GankItemData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, GankItemData gankItemData) {
        holder.setText(R.id.gank_item_desc, gankItemData.getDesc());

        String who = TextUtils.isEmpty(gankItemData.getWho()) ? "null" : gankItemData.getWho();
        holder.setText(R.id.gank_item_who, who);

        holder.setText(R.id.gank_item_publishedat, gankItemData.getPublishedAt().substring(0, 10));
    }
}
