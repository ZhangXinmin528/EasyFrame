package com.zxm.coding.easyframe.http;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by ZhangXinmin on 2020/4/22.
 * Copyright (c) 2018 . All rights reserved.
 */
public interface MetaApi {

    /**
     * 图片接口
     *
     * @return
     */
    @GET("j")
    Observable<ResponseBody> requestImage(@QueryMap Map<String, Object> params);


}
