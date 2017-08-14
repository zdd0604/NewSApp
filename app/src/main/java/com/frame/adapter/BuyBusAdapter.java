package com.frame.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frame.R;
import com.frame.model.ItemModel;

import java.util.ArrayList;

/**
 * Created by wangchang on 2016/3/1.
 */
public class BuyBusAdapter extends RecyclerView.Adapter<BuyBusAdapter.BaseViewHolder> {

    private ArrayList<ItemModel> dataList = new ArrayList<>();

    public BuyBusAdapter(ArrayList<ItemModel> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public BuyBusAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemModel.ITEM_BUY_TITLE:
                return new BuyTitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_buy_title, parent, false));
            case ItemModel.ITEM_BUY_CONTENT:
                return new BuyContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_buy_content, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(BuyBusAdapter.BaseViewHolder holder, int position) {
        holder.setData(dataList.get(position).data, position);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }


    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        void setData(Object object, int position) {

        }
    }

    class BuyTitleViewHolder extends BuyBusAdapter.BaseViewHolder {



        public BuyTitleViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void setData(Object object, int position) {
            super.setData(object, position);
        }
    }

    class BuyContentViewHolder extends BuyBusAdapter.BaseViewHolder {

        public BuyContentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void setData(Object object, int position) {
            super.setData(object, position);
        }
    }
}
