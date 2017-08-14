package com.frame.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.frame.R;
import com.frame.TestData;
import com.frame.adapter.HomeAdapter;
import com.frame.model.ItemModel;

/**
 * Created by wangchang on 2016/2/22.
 */
public class MainFragment extends BaseFragment {

    private HomeAdapter adapters = new HomeAdapter();
    private boolean isLoadMore=true;

    @Override
    protected int getContentView() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(View view) {
        initTitle("京东商城");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapters.getItemViewType(position)) {
                    case ItemModel.ITEM_LIST:
                        return 1;
                    case ItemModel.ITEM_VIEWPAGERE:
                        return 2;
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapters = new HomeAdapter());
        MaterialRefreshLayout materialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        materialRefreshLayout.setLoadMore(isLoadMore);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                    }
                }, 3000);
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoadMore=false;
                        for (int i = 0; i < 20; i++) {
                            adapters.AddNewsData(new ItemModel(ItemModel.ITEM_LIST, TestData.contentList()));
                        }
                        materialRefreshLayout.finishRefreshLoadMore();

                    }
                }, 3000);
            }

            @Override
            public void onfinish() {
                Toast.makeText(getActivity(), "已经没有更多数据啦！", Toast.LENGTH_LONG).show();
            }
        });

        adapters.addBanner(new ItemModel(ItemModel.ITEM_VIEWPAGERE, TestData.bannerList()));
        for (int i = 0; i < 10; i++) {
            adapters.AddNewsData(new ItemModel(ItemModel.ITEM_LIST, TestData.contentList()));
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
