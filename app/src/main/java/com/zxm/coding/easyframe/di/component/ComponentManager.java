package com.zxm.coding.easyframe.di.component;


import com.zxm.coding.libnet.BaseApplication;

/**
 * Created by ZhangXinmin on 2020/4/22.
 * Copyright (c) 2018 . All rights reserved.
 */
public class ComponentManager {
    private static volatile HttpComponent httpComponent;

    public static HttpComponent getHttpComponent() {
        if (httpComponent == null) {
            synchronized (ComponentManager.class) {
                if (httpComponent == null) {
                    httpComponent = DaggerHttpComponent.builder()
                            .appComponent(BaseApplication.getAppComponent())
                            .build();
                }
            }
        }
        return httpComponent;
    }
}

