package com.zxm.coding.easyframe.app;

import com.zxm.coding.libnet.BaseApplication;
import com.zxm.utils.core.log.MLogger;

/**
 * Created by ZhangXinmin on 2020/4/22.
 * Copyright (c) 2020 . All rights reserved.
 */
public class EasyApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initMLogger();
    }

    private void initMLogger() {
        MLogger.setLogEnable(this, true);
    }
}
