package com.up72.library.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * RecyclerView自动加载更多
 * Created by liyingfeng on 2015/12/21.
 */
public class AutoRecyclerView extends RecyclerView {
    private boolean isLoadingMore = false;
    private OnLoadMoreListener onLoadMoreListener;

    public AutoRecyclerView(Context context) {
        this(context, null);
    }

    public AutoRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (getLayoutManager() instanceof LinearLayoutManager) {
                    int lastVisibleItem = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                    int totalItemCount = AutoRecyclerView.this.getAdapter().getItemCount();
                    if (onLoadMoreListener != null && !isLoadingMore && lastVisibleItem >= totalItemCount -
                            2 && dy > 0) {
                        isLoadingMore = true;
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }

    public void onComplete() {
        isLoadingMore = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}