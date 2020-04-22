package com.zxm.coding.easyframe.ui;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxm.coding.easyframe.R;
import com.zxm.coding.easyframe.model.ImageEntity;
import com.zxm.coding.libcore.model.GlideApp;

import java.util.List;

/**
 * Created by ZhangXinmin on 2020/4/22.
 * Copyright (c) 2020 . All rights reserved.
 */
public class ImageAdapter extends BaseQuickAdapter<ImageEntity, BaseViewHolder> {
    public ImageAdapter(@Nullable List<ImageEntity> data) {

        super(R.layout.layout_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageEntity item) {
        final ImageView imageView = helper.getView(R.id.iv_thumb);
        if (item != null) {
            GlideApp.with(imageView)
                    .load(item.getThumb())
                    .into(imageView);

            helper.setText(R.id.tv_item_title, TextUtils.isEmpty(item.getTitle()) ? "" : item.getTitle());
        }
    }
}
