package com.zxm.coding.easyframe.di.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zxm.coding.easyframe.BuildConfig;
import com.zxm.coding.easyframe.http.MetaApi;
import com.zxm.coding.libnet.SSLSocketFactoryCompat;
import com.zxm.coding.libnet.scope.AppScope;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZhangXinmin on 2020/4/22.
 * Copyright (c) 2020 . All rights reserved.
 */
@Module
public class HttpModule {
    private Retrofit retrofit;
    private MetaApi api;

    @Provides
    @AppScope
    public Retrofit provideRetrofit() {
        synchronized (HttpModule.class) {

            if (retrofit == null) {
                final HttpLoggingInterceptor.Level level =
                        BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                                : HttpLoggingInterceptor.Level.NONE;

                final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(level);

                final OkHttpClient.Builder builder = new OkHttpClient.Builder();
                //////////////
                try {
                    // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
                    final X509TrustManager trustAllCert = new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
                                throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
                                throws CertificateException {

                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    };
                    final SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);
                    builder.sslSocketFactory(sslSocketFactory, trustAllCert);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                ////////////
                builder.connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .addInterceptor(loggingInterceptor);


                OkHttpClient okHttpClient = builder.build();
                retrofit = new Retrofit.Builder()
                        .baseUrl("http://image.so.com/")
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();
            }
        }
        return retrofit;
    }

    @Provides
    @AppScope
    public MetaApi provideIApi(Retrofit retrofit) {
        synchronized (HttpModule.class) {
            if (api == null) {
                api = retrofit.create(MetaApi.class);
            }
        }
        return api;
    }
}
