package com.zxm.coding.libcore.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zxm.utils.core.device.DeviceUtil;
import com.zxm.utils.core.permission.PermissionChecker;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by ZhangXinmin on 2017/9/17.
 * Copyright (c) 2017 . All rights reserved.
 * The base class of activity.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();

    protected String mSerialNumber;

    protected Context mContext;
    protected Resources mResources;
    private CompositeDisposable mDisposables;

    /**
     * set layout for activity
     *
     * @return
     */
    protected abstract Object setLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        mResources = getResources();
        mDisposables = new CompositeDisposable();

        initParamsAndValues();

        if (setLayout() instanceof Integer) {
            setContentView((Integer) setLayout());
        } else if (setLayout() instanceof View) {
            setContentView((View) setLayout());
        } else {
            throw new RuntimeException("You must use the method of 'setLayout()' " +
                    "to bind view for activity! ");
        }

        initViews();

    }

    /**
     * init params and values for activity
     */
    protected abstract void initParamsAndValues();

    /**
     * init views for activity
     */
    protected abstract void initViews();

    /**
     * add disposable
     *
     * @param disposable
     */
    protected void addDisposable(@NonNull Disposable disposable) {
        if (disposable != null) {
            mDisposables.add(disposable);
        }
    }

    protected void getSerialNumber() {
        if (!PermissionChecker.checkPersmission(mContext, Manifest.permission.READ_PHONE_STATE)) {
            PermissionChecker.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, 1001);
        } else {
            mSerialNumber = DeviceUtil.getSerialNumber();
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    protected void hideBottomNavigationBar() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 页面跳转
     *
     * @param clz
     */
    protected void jumpActivity(@NonNull Class<? extends BaseActivity> clz) {
        jumpActivity(clz, null);
    }

    /**
     * 页面跳转
     *
     * @param clz
     * @param args
     */
    protected void jumpActivity(@NonNull Class<? extends BaseActivity> clz, @NonNull Bundle args) {
        if (clz != null) {
            final Intent intent = new Intent();
            intent.setClass(mContext, clz);
            if (args != null) {
                intent.putExtras(args);
            }
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        if (mDisposables!=null){
            mDisposables.clear();
        }
        super.onDestroy();
    }
}
