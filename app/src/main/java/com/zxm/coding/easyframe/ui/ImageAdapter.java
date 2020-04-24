package com.zxm.coding.easyframe.ui;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxm.coding.easyframe.R;
import com.zxm.coding.easyframe.model.ImageEntity;
import com.zxm.coding.libcore.model.GlideApp;
import com.zxm.utils.core.screen.ScreenUtil;

import java.util.List;

/**
 * Created by ZhangXinmin on 2020/4/22.
 * Copyright (c) 2020 . All rights reserved.
 */
public class ImageAdapter extends BaseQuickAdapter<ImageEntity, BaseViewHolder> {
    public ImageAdapter(@Nullable List<ImageEntity> data) {
        super(R.layout.layout_list_item, data);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void convert(BaseViewHolder helper, ImageEntity item) {
        final ImageView imageView = helper.getView(R.id.iv_thumb);
        if (item != null) {

            final int screenWidth = ScreenUtil.getScreenWidth(mContext);
            final int width = screenWidth / 2;
            final int height = item.getThumbHeight() * width / item.getThumbWidth();

            GlideApp.with(imageView)
                    .asBitmap()
                    .placeholder(R.drawable.icon_default)
                    .error(R.drawable.icon_default)
                    .load(item.getThumb())
                    .override(width, height)
                    .into(imageView);

            helper.setText(R.id.tv_item_title, TextUtils.isEmpty(item.getTitle()) ? "" : item.getTitle());
        }
    }
}
