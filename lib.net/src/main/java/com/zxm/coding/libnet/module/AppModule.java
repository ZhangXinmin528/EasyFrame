package com.zxm.coding.libnet.module;

import android.content.Context;

import com.zxm.coding.libnet.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ZhangXinmin on 2018/11/29.
 * Copyright (c) 2018 . All rights reserved.
 */
@Module
public class AppModule {
    private BaseApplication context;

    public AppModule(BaseApplication context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context providesContext() {
        return context;
    }
}
