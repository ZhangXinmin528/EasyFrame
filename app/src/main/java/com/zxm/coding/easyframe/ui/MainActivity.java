package com.zxm.coding.easyframe.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zxm.coding.easyframe.R;
import com.zxm.coding.easyframe.di.component.ComponentManager;
import com.zxm.coding.easyframe.model.ImageEntity;
import com.zxm.coding.libcore.ui.BaseActivity;
import com.zxm.utils.core.log.MLogger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ZhangXinmin on 2020/4/22.
 * Copyright (c) 2020 . All rights reserved.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Inject
    MetaViewModel viewModel;

    private EditText mSearchEt;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private int mPageIndex;
    private List<ImageEntity> mDataList;

    @Override
    protected Object setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParamsAndValues() {
        ComponentManager.getHttpComponent().inject(this);
        mPageIndex = 0;
        mDataList = new ArrayList<>();
        mAdapter = new ImageAdapter(mDataList);
    }

    @Override
    protected void initViews() {
        mSearchEt = findViewById(R.id.et_search);
        mSearchEt.setText("北京");

        mRefreshLayout = findViewById(R.id.srl_list);
        mRefreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        mRefreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        mRefreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        mRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        mRefreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        mRefreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        mRefreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
        mRefreshLayout.setEnableFooterTranslationContent(true);//是否上拉Footer的时候向上平移列表或者内容
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        mRefreshLayout.autoRefresh(1000);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                requestData(true);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                requestData(false);
            }
        });

        mRecyclerView = findViewById(R.id.recyclerview_list);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //解决跳动问题
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                requestData(false);
                break;
        }
    }

    /**
     * 请求图片接口
     */
    private void requestData(boolean isRefresh) {
        MLogger.d(TAG, "requestData().. isRefresh : " + isRefresh);
        if (isRefresh) {
            mPageIndex = 0;
            if (!mDataList.isEmpty()) {
                mDataList.clear();
            }
        }
        final String input = mSearchEt.getEditableText().toString().trim();
        if (!TextUtils.isEmpty(input)) {
            addDisposable(viewModel.requestImage(input, mPageIndex, 50)
                    .subscribe(responseBody -> {
                        final String result = responseBody.string();
                        final JSONObject jsonObject = JSON.parseObject(result);
                        final List<ImageEntity> temp = JSONObject.parseArray(jsonObject.getString("list"), ImageEntity.class);
                        if (temp != null && !temp.isEmpty()) {
                            mDataList.addAll(temp);
                            mAdapter.notifyDataSetChanged();
                        }
                        mPageIndex++;
                        finishRequest(isRefresh);
                    }, throwable -> {
                        Toast.makeText(mContext, "请求图片接口异常..[message:" + throwable.getMessage() + "]",
                                Toast.LENGTH_SHORT).show();
                        MLogger.e(TAG, "请求图片接口异常..[message:" + throwable.getMessage() + "]");
                        finishRequest(isRefresh);
                    }));
        }
    }

    private void finishRequest(boolean isRefgresh) {
        if (isRefgresh) {
            mRefreshLayout.finishRefresh();
        } else {
            mRefreshLayout.finishLoadMore();
        }
    }
}
