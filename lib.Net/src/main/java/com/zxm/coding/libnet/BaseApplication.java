package com.zxm.coding.libnet;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.zxm.coding.libnet.component.AppComponent;
import com.zxm.coding.libnet.component.DaggerAppComponent;
import com.zxm.coding.libnet.module.AppModule;


/**
 * Created by ZhangXinmin on 2018/11/29.
 * Copyright (c) 2018 . All rights reserved.
 */
@SuppressLint("Registered")
public class BaseApplication extends Application {
    private static volatile AppComponent appComponent;
    private static BaseApplication instance;

    public static Context getAppContext() {
        return instance;
    }

    private static void initAppInjection() {
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule((BaseApplication) getAppContext()))
                .build();
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            synchronized (BaseApplication.class) {
                if (appComponent == null) {
                    initAppInjection();
                }
            }
        }
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initAppInjection();
    }
}
