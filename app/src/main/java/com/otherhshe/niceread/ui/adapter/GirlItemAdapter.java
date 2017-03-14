package com.otherhshe.niceread.ui.adapter;

import android.content.Context;

import com.otherhshe.niceread.NiceReadApplication;
import com.otherhshe.niceread.R;
import com.otherhshe.niceread.model.GirlItemData;
import com.otherhshe.niceread.ui.adapter.baseadapter.BaseAdapter;
import com.otherhshe.niceread.ui.adapter.baseadapter.ViewHolder;
import com.otherhshe.niceread.ui.weiget.ScaleImageView;
import com.otherhshe.niceread.utils.ImageLoader;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/18 21:59
 */
public class GirlItemAdapter extends BaseAdapter<GirlItemData> {

    public GirlItemAdapter(Context context, List<GirlItemData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, GirlItemData girlItemData) {
        ScaleImageView imageView = holder.getView(R.id.girl_item_iv);
        imageView.setInitSize(girlItemData.getWidth(), girlItemData.getHeight());
        ImageLoader.load(NiceReadApplication.getContext(),
                girlItemData.getUrl(), imageView);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_girl_layout;
    }
}
