package com.zxm.coding.easyframe.di.component;

import com.zxm.coding.easyframe.di.module.HttpModule;
import com.zxm.coding.easyframe.ui.MainActivity;
import com.zxm.coding.libnet.component.AppComponent;
import com.zxm.coding.libnet.scope.AppScope;

import dagger.Component;

/**
 * Created by ZhangXinmin on 2020/4/22.
 * Copyright (c) 2020 . All rights reserved.
 */
@AppScope
@Component(modules = HttpModule.class, dependencies = AppComponent.class)
public interface HttpComponent {

    void inject(MainActivity mainActivity);
}
