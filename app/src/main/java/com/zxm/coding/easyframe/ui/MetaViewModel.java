package com.zxm.coding.easyframe.ui;


import androidx.annotation.NonNull;

import com.zxm.coding.easyframe.http.MetaApi;
import com.zxm.coding.libnet.viewmodle.BaseViewModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * Created by ZhangXinmin on 2020/4/22.
 * Copyright (c) 2018 . All rights reserved.
 */
public class MetaViewModel extends BaseViewModel {

    private MetaApi api;

    @Inject
    public MetaViewModel(MetaApi api) {
        this.api = api;
    }

    /**
     * 设备登陆接口：
     * 客户超级详细信息
     */
    public Observable<ResponseBody> requestImage(@NonNull String q, int sn, int pn) {
        final Map<String, Object> params = new HashMap<>();
        params.put("q", q);
        params.put("sn", sn);
        params.put("pn", pn);
        return api.requestImage(params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


}
