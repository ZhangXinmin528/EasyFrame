package com.zxm.coding.libcore.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        if (mDisposables != null) {
            mDisposables.clear();
        }
        super.onDestroy();
    }
}
