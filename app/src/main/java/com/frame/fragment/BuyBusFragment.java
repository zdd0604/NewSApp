package com.frame.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.frame.R;
import com.frame.TestData;
import com.frame.adapter.BuyBusAdapter;
import com.frame.model.BuyModel;
import com.frame.model.BuysModel;
import com.frame.model.ItemModel;

import java.util.ArrayList;

/**
 * Created by wangchang on 2016/2/22.
 */
public class BuyBusFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private MaterialRefreshLayout refreshLayout;
    private BuyBusAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_buybus;
    }

    @Override
    protected void initView(View view) {
        initTitle("购物车", "编辑");

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        refreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter = new BuyBusAdapter(getDatas()));


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        getDatas();
    }

    private ArrayList<ItemModel> getDatas() {

        ArrayList<ItemModel> models = new ArrayList<>();
        BuyModel model = new BuyModel();
        model.setFactory_name("京东自营");
        model.setFactory_title("金牌会员享受京东自营商品满79元免运费服务");
        model.setFactory_cheap("换购 ，活动商品满1299.00元，即可换购商品，先到先得");
        models.add(new ItemModel(ItemModel.ITEM_BUY_TITLE, model));
        BuysModel data = new BuysModel();
        data.setImageUrl(TestData.getNetImage());
        data.setGoods_title("联想ThinkPad T450笔记本超值优惠");
        data.setGoods_count(2);
        data.setGoods_price(9999.00);
        models.add(new ItemModel(ItemModel.ITEM_BUY_CONTENT, data));
        BuysModel data1 = new BuysModel();
        data1.setImageUrl(TestData.getNetImage());
        data1.setGoods_title("联想ThinkPad T450p笔记本超值优惠");
        data1.setGoods_count(1);
        data1.setGoods_price(19999.00);
        models.add(new ItemModel(ItemModel.ITEM_BUY_CONTENT, data1));
        BuysModel data2 = new BuysModel();
        data2.setImageUrl(TestData.getNetImage());
        data2.setGoods_title("联想ThinkPad Z470笔记本超值优惠");
        data2.setGoods_count(3);
        data2.setGoods_price(5399.00);
        models.add(new ItemModel(ItemModel.ITEM_BUY_CONTENT, data2));
        return models;
    }


}
