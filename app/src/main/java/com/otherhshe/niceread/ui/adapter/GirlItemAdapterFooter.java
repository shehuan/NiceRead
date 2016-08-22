package com.otherhshe.niceread.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.otherhshe.niceread.NiceReadApplication;
import com.otherhshe.niceread.R;
import com.otherhshe.niceread.data.GirlItemData;
import com.otherhshe.niceread.ui.adapter.baseadapter.FooterRefreshAdapter;
import com.otherhshe.niceread.ui.adapter.baseadapter.ViewHolder;
import com.otherhshe.niceread.ui.weiget.ScaleImageView;
import com.otherhshe.niceread.utils.ImageLoader;

import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/18 21:59
 */
public class GirlItemAdapterFooter extends FooterRefreshAdapter<GirlItemData> {
    public GirlItemAdapterFooter(Context context, List<GirlItemData> datas) {
        super(context, datas);
    }

    @Override
    protected void convert(RecyclerView.ViewHolder viewHolder, GirlItemData girlItemData) {
        ViewHolder holder = (ViewHolder) viewHolder;
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
