package com.zxm.coding.libnet.component;

import android.content.Context;

import com.zxm.coding.libnet.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ZhangXinmin on 2018/11/29.
 * Copyright (c) 2018 . All rights reserved.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    Context context();
}
