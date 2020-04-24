package com.zxm.coding.libcore.model;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by ZhangXinmin on 2019/2/22.
 * Copyright (c) 2018 . All rights reserved.
 */
@GlideModule
public class CustomGlideModule extends AppGlideModule {
    @Override
    public boolean isManifestParsingEnabled() {
        //禁用清单解析，节省时间
        return false;
    }
}
