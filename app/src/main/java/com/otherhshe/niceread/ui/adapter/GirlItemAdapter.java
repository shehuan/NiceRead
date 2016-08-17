package com.otherhshe.niceread.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.otherhshe.niceread.NiceReadApplication;
import com.otherhshe.niceread.R;
import com.otherhshe.niceread.data.GirlItemData;
import com.otherhshe.niceread.utils.ImageLoader;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/17 15:35
 */
public class GirlItemAdapter extends BaseQuickAdapter<GirlItemData> {


    public GirlItemAdapter(int layoutResId, List<GirlItemData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, GirlItemData girlItemData) {
        ImageLoader.load(NiceReadApplication.getContext(),
                girlItemData.getUrl(), (ImageView) holder.getView(R.id.girl_item_iv));
    }
}
