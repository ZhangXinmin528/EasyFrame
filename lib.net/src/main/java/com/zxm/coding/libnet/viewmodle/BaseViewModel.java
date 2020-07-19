package com.zxm.coding.libnet.viewmodle;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

/**
 * Created by ZhangXinmin on 2018/11/29.
 * Copyright (c) 2018 . All rights reserved.
 */
public class BaseViewModel extends ViewModel implements LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
